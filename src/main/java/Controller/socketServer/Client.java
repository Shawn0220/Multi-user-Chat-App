package Controller.socketServer;

import View.ChatBox;
import View.LoginScreen;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends JFrame{

    private static String uid, gid;
    private JLabel uidLabel, gidLabel;
    private JTextField uidField, gidField;
    private JButton loginButton;


    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 9000);
        // input是客户端从服务端接收的数据
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // output是客户端向服务端发送的数据
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        OutputStream outputStream = socket.getOutputStream();
        while(true) {
            String logininfo = null;
            System.out.print("Client available, enter ’run app‘ to open app:");
            String userInput = consoleInput.readLine();
            if(userInput.equals("run app")) {
//                SwingUtilities.invokeLater(() -> new LoginScreen());
                // 初始化一个新的登陆界面
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
                String noresult = loginScreen.getloginfo();
                Thread.sleep(1000);
                // 获取登录信息：用户uid + 群组号gid
//                String result = loginScreen.getloginfo();
                String result = "null null";

                // 持续等待到用户提交uid gid
                while (result == null || result.equals("null null")) {
                    // 等待500毫秒 防止一直while占用CPU过高
                    Thread.sleep(500);
                    // 获取loginfo值
                    result = loginScreen.getloginfo();
                }
                logininfo = result;

                String[] parts = result.split(" "); // 按照空格分割字符串
                if (parts.length == 2) { // 判断分割后的数组长度是否为 2
                    String A = parts[0]; // 获取第一个字符串
                    String B = parts[1]; // 获取第二个字符串
                    uid = A;
                    gid = B;
//                    System.out.println("uid = " + A + ", gid = " + B);
                }
//                System.out.println("User ID and group: " + result);
//                break;
                // 将输入数据发送到服务端
                output.println(logininfo);
                SwingUtilities.invokeLater(() -> new ChatBox(socket, uid, gid));
//            System.out.println("debug1");
                String serverResponse = input.readLine();
//            System.out.println("debug2");
//                new updateOnlineUsersFile(serverResponse, gid);
                System.out.println(serverResponse);
                if (serverResponse.length()==0){
                    break;
            }

            }
//            System.out.println("debug3");
        }

        socket.close();
    }
}
