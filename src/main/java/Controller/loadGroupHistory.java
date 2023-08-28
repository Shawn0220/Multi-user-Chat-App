package Controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class loadGroupHistory {
    public void loadGroupHistory (String gid, JPanel mJpanel){
        try {
            File inputFile = new File("src/main/java/appData/groupMessage/message"+gid+".xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList messagesList = doc.getElementsByTagName("message");

            for (int i = 0; i < messagesList.getLength(); i++) {
                Node messageNode = messagesList.item(i);
                if (messageNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element messageElement = (Element) messageNode;

                    // 获取消息中的用户名、时间和内容
                    String user = messageElement.getElementsByTagName("user").item(0).getTextContent();
                    String time = messageElement.getElementsByTagName("time").item(0).getTextContent();
                    String content = messageElement.getElementsByTagName("content").item(0).getTextContent();


                    if (content.startsWith("B64ENCODE")) {
                        // 消息内容content是图片编码，打印图片
                        String result = content.substring(9);  // 删去前9个字符后转 Base64
//                        System.out.println(result);
                        byte[] b64bytes = Base64.getDecoder().decode(result);

                        // 读入字节数组为 BufferedImage 对象
                        BufferedImage image = null;
                        try {
                            image = ImageIO.read(new ByteArrayInputStream(b64bytes));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        // 缩小图片尺寸
                        Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

                        JLabel label = new JLabel();
                        label.setPreferredSize(new Dimension(100, 100));
                        label.setIcon(new ImageIcon(scaledImage));


                        JTextArea addTextArea = new JTextArea();
                        addTextArea.setEditable(false);
                        addTextArea.setPreferredSize(new Dimension(200, 100));
                        addTextArea.setText(user + " (" + time + "):   ");

                        JPanel userAndPic = new JPanel(new BorderLayout());
                        userAndPic.setBackground(Color.white);

                        userAndPic.setPreferredSize(new Dimension(400,130));
//                        scrollPane.setBounds(new Rectangle(10,10,800,650));
//
//                        addTextArea.setBounds(0, 0, 100, 100);
//                        label.setBounds(100,0,100,100);

                        userAndPic.add(addTextArea, BorderLayout.WEST);
                        userAndPic.add(label);

                        mJpanel.add(userAndPic);
//                        mJpanel.repaint();
//                        mJpanel.revalidate();

                    } else {
//                        System.out.println(content);
                        // 直接输出文字
                        JTextArea addTextArea = new JTextArea();
                        addTextArea.setEditable(false);
                        addTextArea.setPreferredSize(new Dimension(800, 30));

                        addTextArea.setText(user + " (" + time + "): " + content);

                        mJpanel.add(addTextArea);
                    }
//                    mJpanel.repaint();
//                    mJpanel.revalidate();

                    // 将消息添加到 JTextArea 中
//                    chatArea.append(user + " (" + time + "): " + content + "\n\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
