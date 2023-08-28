package Controller;

import Model.messageModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class sendMessage {
    private messageModel oneMessage;
    public void sendMessage(boolean isSendingPICS, JTextField messageField, String uid, String uname, String gid)
    {
        String msg=messageField.getText().trim();//get typed text from field
        if(isSendingPICS){
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                // 读取图片文件为字节数组
                byte[] bytes = null;
                try {
                    bytes = Files.readAllBytes(selectedFile.toPath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                // 将字节数组编码为 Base64 字符串
                String base64String = Base64.getEncoder().encodeToString(bytes);
                msg = "B64ENCODE" + base64String;
            }
        }
        // 获取消息发送时间
        // 创建一个 DateTimeFormatter，用来指定时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        // 获取当前时间，并使用 formatter 格式化为字符串
        LocalDateTime now = LocalDateTime.now();
        String iso8601Time = now.format(formatter);
        this.oneMessage = new messageModel(uid, uname,  iso8601Time, msg, gid);
        oneMessage.setUserID(uid);
        oneMessage.setUser(uname);
        oneMessage.setTime(iso8601Time);
        oneMessage.setContent(msg);
        if(!msg.isEmpty()){//send only non empty strings
//            msg = uname + "(" + iso8601Time + "）:"+ msg;

//            displayMessage(msg);//display message on textarea
            messageField.setText("");

            // 覆盖最新消息cache
            try {
                // 创建DocumentBuilderFactory实例
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                // 解析XML文件并创建Document对象表示整个文档
                Document doc = builder.parse(new File("src/main/java/appData/groupMessage/msgcache"+gid+".xml"));

                // 获取message节点下的子节点user、time和content
                NodeList messages = doc.getElementsByTagName("message");
                Element message = (Element) messages.item(0);
                Element user = (Element) message.getElementsByTagName("user").item(0);
                Element time = (Element) message.getElementsByTagName("time").item(0);
                Element content = (Element) message.getElementsByTagName("content").item(0);

                // 更新元素节点的值
                user.setTextContent(oneMessage.getUser());
                time.setTextContent(oneMessage.getTime());
                content.setTextContent(oneMessage.getContent());

                // 将更新后的Document对象写入XML文件中
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("src/main/java/appData/groupMessage/msgcache"+gid+".xml"));
                transformer.transform(source, result);

                System.out.println("cacheXML file updated successfully!");
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }

            // 增加聊天历史记录
            try {
                // 创建DocumentBuilderFactory实例
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                // 解析XML文件并创建Document对象表示整个文档
                Document doc = builder.parse(new File("src/main/java/appData/groupMessage/message"+gid+".xml"));

                // 创建新的message节点和其子节点user、time和content
                Element message = doc.createElement("message");
                Element user = doc.createElement("user");
                Element time = doc.createElement("time");
                Element content = doc.createElement("content");

                // 设置user、time和content节点的文本内容

                user.appendChild(doc.createTextNode(uname));
                time.appendChild(doc.createTextNode(iso8601Time));
                content.appendChild(doc.createTextNode(msg));

                // 将user、time和content节点添加到message节点中
                message.appendChild(user);
                message.appendChild(time);
                message.appendChild(content);

                // 将message节点添加到根节点chat中
                Node chat = doc.getElementsByTagName("chat").item(0);
                chat.appendChild(message);

                // 将更新后的Document对象写入XML文件中
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("src/main/java/appData/groupMessage/message"+gid+".xml"));
                transformer.transform(source, result);

                System.out.println("historyXML file written successfully!");
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }

}
