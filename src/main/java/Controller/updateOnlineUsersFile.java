package Controller;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.Arrays;

public class updateOnlineUsersFile {
    // ...

    public updateOnlineUsersFile(String userIds,String gid) {
        try {
            // 创建 XML 文档解析器并加载文件
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            File xmlFile = new File("src/main/java/appData/onlineList/onlineUsersG"+gid + ".xml");
            Document document = builder.parse(xmlFile);

            // 删除原有的 userId 标签
            NodeList userList = (NodeList) document.getElementsByTagName("userId");
            for (int i = 0; i < userList.getLength(); i++) {
                userList.item(i).getParentNode().removeChild(userList.item(i));
            }

            // 将新的 userId 标签添加到 userlist 中
            String[] ids = userIds.substring(1, userIds.length() - 1).split(",");
            Element userlist = (Element) document.getElementsByTagName("userList").item(0);
            for (String id : ids) {
                Element userId = document.createElement("userId");
                userId.setTextContent(id.trim());
                userlist.appendChild(userId);
            }

            // 将修改后的文档写回到 XML 文件中
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("src/main/java/appData/onlineList/onlineUsersG"+gid + ".xml"));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
