package Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class one2oneMessageSend {
    public static void addOne2OneMessage(JTextField messageField, String uid, String now2whom) {
        String mymessage = messageField.getText();
        LocalDateTime now = LocalDateTime.now(); // 获取当前系统时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); // 定义时间格式
        String formattedDateTime = now.format(formatter); // 将时间转换成字符串格式

        try {
            // 更新发送者的记录
            // 创建DocumentBuilderFactory实例
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            File xmlFile = new File("src/main/java/appData/one2oneMessage/one2oneMessage" + uid + ".xml");
            if (xmlFile.exists()) {
                System.out.println("send XML文件存在：" + "src/main/java/appData/one2oneMessage/one2oneMessage" + uid + ".xml");


                // 解析XML文件并创建Document对象表示整个文档
                Document doc = builder.parse(new File("src/main/java/appData/one2oneMessage/one2oneMessage" + uid + ".xml"));

                // 创建新的message节点和其子节点user、time和content
                Element message = doc.createElement("message");
                Element user = doc.createElement("user");
                Element receiver = doc.createElement("receiver");
                Element time = doc.createElement("time");
                Element content = doc.createElement("content");

                user.appendChild(doc.createTextNode(now2whom));
                receiver.appendChild(doc.createTextNode("1"));
                time.appendChild(doc.createTextNode(formattedDateTime));
                content.appendChild(doc.createTextNode(mymessage));

                // 将user、time和content节点添加到message节点中
                message.appendChild(user);
                message.appendChild(receiver);
                message.appendChild(time);
                message.appendChild(content);

                // 将message节点添加到根节点chat中
                Node chat = doc.getElementsByTagName("chat").item(0);
                chat.appendChild(message);

                // 将更新后的Document对象写入XML文件中
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("src/main/java/appData/one2oneMessage/one2oneMessage" + uid + ".xml"));
                transformer.transform(source, result);

                System.out.println("nowUserXML file written successfully!");


            } else {

                // 创建一个空的 Document 对象
                DocumentBuilderFactory docFactory11 = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder11 = docFactory11.newDocumentBuilder();
                Document doc11 = docBuilder11.newDocument();

                // 创建根节点 <chat></chat>
                Element rootElement1 = doc11.createElement("chat");
                doc11.appendChild(rootElement1);

                // 将 Document 对象保存到文件
                TransformerFactory transformerFactory11 = TransformerFactory.newInstance();
                Transformer transformer11 = transformerFactory11.newTransformer();
                DOMSource source11 = new DOMSource(doc11);
                StreamResult result11 = new StreamResult(new File("ASDF.xml"));
                transformer11.transform(source11, result11);


                // 解析XML文件并创建Document对象表示整个文档
                Document doc = builder.parse(new File("src/main/java/appData/one2oneMessage/one2oneMessage" + uid + ".xml"));

                // 创建新的message节点和其子节点user、time和content
                Element message = doc.createElement("message");
                Element user = doc.createElement("user");
                Element receiver = doc.createElement("receiver");
                Element time = doc.createElement("time");
                Element content = doc.createElement("content");

                user.appendChild(doc.createTextNode(now2whom));
                receiver.appendChild(doc.createTextNode("1"));
                time.appendChild(doc.createTextNode(formattedDateTime));
                content.appendChild(doc.createTextNode(mymessage));

                // 将user、time和content节点添加到message节点中
                message.appendChild(user);
                message.appendChild(receiver);
                message.appendChild(time);
                message.appendChild(content);

                // 将message节点添加到根节点chat中
                Node chat = doc.getElementsByTagName("chat").item(0);
                chat.appendChild(message);

                // 将更新后的Document对象写入XML文件中
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("src/main/java/appData/one2oneMessage/one2oneMessage" + uid + ".xml"));
                transformer.transform(source, result);

                System.out.println("nowUserXML file written successfully!");
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////////////////////////////////
            // 更新接收者的记录
            // 创建DocumentBuilderFactory实例
            DocumentBuilderFactory factory12 = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder12 = factory12.newDocumentBuilder();
            File xmlFile12 = new File("src/main/java/appData/one2oneMessage/one2oneMessage" + now2whom + ".xml");
            if (xmlFile12.exists()) {
                System.out.println("XML文件存在：" + "src/main/java/appData/one2oneMessage/one2oneMessage" + now2whom + ".xml");


                // 解析XML文件并创建Document对象表示整个文档
                Document doc = builder12.parse(new File("src/main/java/appData/one2oneMessage/one2oneMessage" + now2whom + ".xml"));

                // 创建新的message节点和其子节点user、time和content
                Element message = doc.createElement("message");
                Element user = doc.createElement("user");
                Element receiver = doc.createElement("receiver");
                Element time = doc.createElement("time");
                Element content = doc.createElement("content");

                user.appendChild(doc.createTextNode(uid));
                receiver.appendChild(doc.createTextNode("0"));
                time.appendChild(doc.createTextNode(formattedDateTime));
                content.appendChild(doc.createTextNode(mymessage));

                // 将user、time和content节点添加到message节点中
                message.appendChild(user);
                message.appendChild(receiver);
                message.appendChild(time);
                message.appendChild(content);

                // 将message节点添加到根节点chat中
                Node chat = doc.getElementsByTagName("chat").item(0);
                chat.appendChild(message);

                // 将更新后的Document对象写入XML文件中
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("src/main/java/appData/one2oneMessage/one2oneMessage" + now2whom + ".xml"));
                transformer.transform(source, result);

                System.out.println("nowUserXML file written successfully!");


            } else {
                // 创建一个空的 Document 对象
                DocumentBuilderFactory docFactory11 = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder11 = docFactory11.newDocumentBuilder();
                Document doc11 = docBuilder11.newDocument();

                // 创建根节点 <chat></chat>
                Element rootElement1 = doc11.createElement("chat");
                doc11.appendChild(rootElement1);

                // 将 Document 对象保存到文件
                TransformerFactory transformerFactory11 = TransformerFactory.newInstance();
                Transformer transformer11 = transformerFactory11.newTransformer();
                DOMSource source11 = new DOMSource(doc11);
                StreamResult result11 = new StreamResult(new File("src/main/java/appData/one2oneMessage/one2oneMessage" + now2whom + ".xml"));
                transformer11.transform(source11, result11);


                // 解析XML文件并创建Document对象表示整个文档
                Document doc = builder12.parse(new File("src/main/java/appData/one2oneMessage/one2oneMessage" + now2whom + ".xml"));

                // 创建新的message节点和其子节点user、time和content
                Element message = doc.createElement("message");
                Element user = doc.createElement("user");
                Element receiver = doc.createElement("receiver");
                Element time = doc.createElement("time");
                Element content = doc.createElement("content");

                user.appendChild(doc.createTextNode(uid));
                receiver.appendChild(doc.createTextNode("0"));
                time.appendChild(doc.createTextNode(formattedDateTime));
                content.appendChild(doc.createTextNode(mymessage));

                // 将user、time和content节点添加到message节点中
                message.appendChild(user);
                message.appendChild(receiver);
                message.appendChild(time);
                message.appendChild(content);

                // 将message节点添加到根节点chat中
                Node chat = doc.getElementsByTagName("chat").item(0);
                chat.appendChild(message);

                // 将更新后的Document对象写入XML文件中
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("src/main/java/appData/one2oneMessage/one2oneMessage" + now2whom + ".xml"));
                transformer.transform(source, result);

                System.out.println("nowReveiverXML file written successfully!");
            }

        } catch (ParserConfigurationException | TransformerException | org.xml.sax.SAXException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
