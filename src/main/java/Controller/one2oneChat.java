package Controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class one2oneChat {
    public one2oneChat(JTextArea one2onechatArea, String uid, String now2Whom, String myName) throws TransformerException, ParserConfigurationException {
        String now2WhomName = "";
        // 查询user表，看用户ID对应的用户名称
        try {
            File inputFile = new File("src/main/java/appData/user.xml");
            // 创建 DocumentBuilder 对象并解析 XML 文件
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            // 获取所有的 <user> 标签
            NodeList onlineUsers = doc.getElementsByTagName("user");
            // 遍历所有的 <user> 标签，查找指定用户编号的用户
            for (int i = 0; i < onlineUsers.getLength(); i++) {
                Element userElement = (Element) onlineUsers.item(i);

                // 如果找到了指定用户编号的用户，则输出该用户的昵称
                if (userElement.getAttribute("uid").equals(now2Whom)) {
                    now2WhomName = userElement.getElementsByTagName("uname").item(0).getTextContent();
//                    System.out.println("Now speaking to: " + now2WhomName);
                }
            }
            // 如果未找到指定用户编号的用户，则输出错误信息
//            System.out.println("User not found.");
        } catch (Exception e) {
            e.printStackTrace();
        }
////////////////
        /// 检测文件是否存在，不存在就新建
        File xmlFile0 = new File("src/main/java/appData/one2oneMessage/one2oneMessage" + uid + ".xml");
        if (xmlFile0.exists()) {
//            System.out.println("XML文件已存在：" + "src/main/java/appData/one2oneMessage/one2oneMessage" + uid + ".xml");
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
            StreamResult result11 = new StreamResult(new File("src/main/java/appData/one2oneMessage/one2oneMessage" + uid + ".xml"));
            transformer11.transform(source11, result11);
        }
///////////////////
        // 加载一对一聊天历史
        try {
            // 创建 XML 文档解析器并加载文件
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            File xmlFile = new File("src/main/java/appData/one2oneMessage/one2oneMessage" + uid + ".xml");
            Document document = builder.parse(xmlFile);

            // 创建 XPath 解析器并编译表达式
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("//message[user='" + now2Whom + "']");

            // 在 XML 文档中执行 XPath 查询，获取匹配结果
            NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
            one2onechatArea.setText(""); // 清空文本区域
            // 输出匹配结果的 user、time、content 到控制台
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                String res = now2WhomName;
                String user = xpath.evaluate("user", node);
                String isHeSender = xpath.evaluate("receiver", node);
                if(isHeSender.equals("1")){
                    res = "You";
//                    System.out.println(("HHHHHHH"));
                }

                String time = xpath.evaluate("time", node);
                String content = xpath.evaluate("content", node);
//                System.out.printf("user: %s, time: %s, content: %s\n", user, time, content);
                one2onechatArea.append(res + " (" + time + "): \n" + content + "\n\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}