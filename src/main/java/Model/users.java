package Model;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class users {
    private String uid;
    private String uname;
    private String ulevel;
    private String usex;

    public users(String uid, String uname, String ulevel, String usex) {
        this.uid = uid;
        this.uname = uname;
        this.ulevel = ulevel;
        this.usex = usex;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUlevel() {
        return ulevel;
    }

    public void setUlevel(String ulevel) {
        this.ulevel = ulevel;
    }

    public String getUsex() {
        return usex;
    }

    public void setUsex(String usex) {
        this.usex = usex;
    }

    // 根据uid查询对应用户的全部信息 创建user对象
    public static users readUserXML(String uid) {
        try {
            File inputFile = new File("src/main/java/appData/user.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Element nNode = (Element) nList.item(temp);
                String userId = nNode.getAttribute("uid");

                if (userId.endsWith(uid)) {
                    String uname = nNode.getElementsByTagName("uname").item(0).getTextContent();
                    String ulevel = nNode.getElementsByTagName("ulevel").item(0).getTextContent();
                    String usex = nNode.getElementsByTagName("usex").item(0).getTextContent();

                    return new users(uid, uname, ulevel, usex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
