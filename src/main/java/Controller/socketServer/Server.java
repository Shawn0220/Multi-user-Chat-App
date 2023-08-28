package Controller.socketServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static ArrayList<Integer> uid = new ArrayList<Integer>();
    static ArrayList<Integer> gid = new ArrayList<Integer>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9000);
        System.out.println("Waiting for clients...");
        while(true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected!");
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            clientHandler.start();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) throws IOException {
            this.clientSocket = socket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        }

        public void run() {
            try {
                while(true) {
                    String input = in.readLine();
                    if(input == null) {
                        return;
                    }

                    String[] parts = input.split(" "); // 按照空格分割字符串
                    if (parts.length == 2) { // 判断分割后的数组长度是否为 2
                        String A = parts[0]; // 获取第一个字符串
                        String B = parts[1]; // 获取第二个字符串
//                        System.out.println("A = " + A + ", B = " + B);
                        int aa = Integer.parseInt(A);
                        int bb = Integer.parseInt(B);
                        // aa是用户编号， bb是群聊编号
                        System.out.println(aa+" "+bb);
                        if (aa<0){
                            uid.remove(Integer.valueOf(-aa));
                            System.out.println("Now uid list:" + uid);
                            out.println(uid.toString());
                        }else{
                            uid.add(aa);
                            System.out.println("Now uid list:" + uid);
                            out.println(uid.toString());
                        }
                        // 传入uid的集合
                        String nowUID = generateXmlString(uid, bb);

//                        System.out.println("debug5");

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static String generateXmlString(ArrayList<Integer> uids, int gid) {


        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
        xmlBuilder.append("<active>\n");
        xmlBuilder.append("<userList>\n");

        for (int each_uid : uid) {
            // 生成 XML 字符串
            xmlBuilder.append("<userId>").append(each_uid).append("</userId>\n");
        }
        System.out.println(xmlBuilder.toString());

        xmlBuilder.append("</userList>\n");
        xmlBuilder.append("<updateTime>2023-6-01T15:30:00</updateTime>\n");
        xmlBuilder.append("</active>");


        writeXmlToFile(xmlBuilder.toString(), gid);
        return(xmlBuilder.toString());
    }

    private static void writeXmlToFile(String xmlContent, int gid) {
        try {
            System.out.println("onlineUsersG" + gid + ".xml");
            FileWriter fileWriter = new FileWriter("src/main/java/appData/onlineList/onlineUsersG" + gid + ".xml");
            fileWriter.write(xmlContent);
            fileWriter.close();
            System.out.println("onlineUserXML data updated.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    }
}
