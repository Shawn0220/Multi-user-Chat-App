package Controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class getOnlineUserIDs {
//    String fromuid 是 当前界面的用户是谁 uID
    public getOnlineUserIDs(String gid,String fromuid) {
    }

    public String[] getOnlineUserIDs(String gid, String fromuid) {
        String[] a = new String[0];
        try {
            // 创建 XML 文档解析器并加载文件
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            File xmlFile = new File("src/main/java/appData/onlineList/onlineUsersG" + gid + ".xml");
            Document document = builder.parse(xmlFile);

            // 从 document 中获取所有的 userId 标签
            NodeList nodeList = document.getElementsByTagName("userId");

            // 使用 HashSet 存储不重复的 userId 内容
            HashSet<String> onlineUsersSet = new HashSet<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String userId = element.getTextContent().trim();
                if (!userId.isEmpty()) {
                    onlineUsersSet.add(userId);
                }
            }
//            System.out.println(onlineUsersSet);
            // 将 HashSet 转换成 String[] 数组
            onlineUsersSet.remove(fromuid);
//            System.out.println("After delete client user" + onlineUsersSet);
            String[] onlineUsersIDs = onlineUsersSet.toArray(new String[0]);
//            System.out.println(onlineUsersIDs);

            try {
                // 创建 XML 文档解析器并加载文件
                DocumentBuilderFactory builderFactory1 = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder1 = builderFactory1.newDocumentBuilder();
                File xmlFile1 = new File("src/main/java/appData/user.xml");
                Document document1 = builder1.parse(xmlFile1);

                // 遍历 onlineUsersIDs 数组，在 XML 中查找相应的用户信息
                ArrayList<String> allUserInfo = new ArrayList<>();
                for (String uid : onlineUsersIDs) {
                    String uInfo ="";
                    Element userElement = (Element) document1.getElementsByTagName("user").item(Integer.parseInt(uid) - 1);
                    String uname = userElement.getElementsByTagName("uname").item(0).getTextContent().trim();
                    String ulevel = userElement.getElementsByTagName("ulevel").item(0).getTextContent().trim();
                    String usex = userElement.getElementsByTagName("usex").item(0).getTextContent().trim();
                    if (false){
                        uInfo = "<html><font color='red'>" + uname + " ( " + usex + "   ID" + uid + "   等级" + ulevel + " )" + "</font></html>";
                    }else{
                        uInfo =  uname + " ( " + usex + "   ID" + uid + "   等级" + ulevel ;
                    }

                    allUserInfo.add(uInfo);
                }

                // 将 ArrayList 转换成 String[] 数组
                String[] onlineUsers = allUserInfo.toArray(new String[0]);

                // 输出所有用户信息
//                System.out.println("所有在线用户信息：");
//                for (String uInfo : onlineUsers) {
//                    System.out.println("- " + uInfo);
//                }

                return onlineUsers;

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return a;
    }

    public static void main(String[] args) {
        getOnlineUserIDs onlineUsersGetter = new getOnlineUserIDs("2","1");
        String[] onlineUsers = onlineUsersGetter.getOnlineUserIDs("2","1");
        System.out.println(onlineUsers);
    }
}
