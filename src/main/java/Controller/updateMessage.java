package Controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class updateMessage {
    // updateMessage类的私有成员lastUpdate记录上次更新消息的时间
    private String lastUpdate;
    public void updateChatArea(JPanel mJpanel, String gid ) {
        try {
//            System.out.println("Here updateChatArea fun() ");
//            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("src/main/java/appData/groupMessage/msgcache"+gid+".xml");
            try {
                // 创建DocumentBuilderFactory实例
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                // 解析XML文件并创建Document对象表示整个文档
                Document cachedoc = builder.parse("src/main/java/appData/groupMessage/msgcache"+gid+".xml");

                // 获取message节点下的子节点user、time和content
                NodeList messages = cachedoc.getElementsByTagName("message");
                for (int i = 0; i < messages.getLength(); i++) {
                    Element message = (Element) messages.item(i);
                    String user = message.getElementsByTagName("user").item(0).getTextContent();
                    String timestamp = message.getElementsByTagName("time").item(0).getTextContent();
                    String content = message.getElementsByTagName("content").item(0).getTextContent();
                    // 打印结果
//                    System.out.println("User: " + user);
//                    System.out.println("Time: " + timestamp);
//                    System.out.println("Content: " + content);

                    // LocalDateTime timestamp = LocalDateTime.parse(time);
//                    System.out.println(lastUpdate + " " + timestamp);
                    // 如果时间戳不为null且与上次更新时间不同，则添加新消息到chatArea
//                    if (lastUpdate != null && !timestamp.equals(lastUpdate)) {
//
//                        chatArea.append("\n" + user +"(" + timestamp + "):" + content + "\n");
////                        chatArea.append("\n" + content);
//                        System.out.println("new message");
//                    }

                    // 如果时间戳不为null且与上次更新时间不同，则添加新消息到chatArea
                    if (lastUpdate != null && !timestamp.equals(lastUpdate)) {
                        if (content.startsWith("B64ENCODE")) {
                            // 消息内容content是图片编码，打印图片
                            String result = content.substring(9);  // 删去前9个字符后转 Base64
//                            System.out.println(result);
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
                            addTextArea.setText(user + " (" + timestamp + "):   ");

                            JPanel userAndPic = new JPanel(new BorderLayout());
                            userAndPic.setBackground(Color.white);

                            userAndPic.setPreferredSize(new Dimension(400,130));

                            userAndPic.add(addTextArea, BorderLayout.WEST);
                            userAndPic.add(label);

                            mJpanel.add(userAndPic);

                        } else {
//                            System.out.println(content);
                            // 直接输出文字
                            JTextArea addTextArea = new JTextArea();
                            addTextArea.setEditable(false);
                            addTextArea.setPreferredSize(new Dimension(800, 30));

                            addTextArea.setText(user + " (" + timestamp + "): " + content);

                            System.out.println("new message");
                            mJpanel.add(addTextArea);
                        }

                    }

                    lastUpdate = timestamp;
//                    return lastUpdate;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            return lastUpdate;
        } catch (Exception e) {
            System.out.println("Error while parsing cache file: " + e.getMessage());
//            return lastUpdate;
        }
    }
}
