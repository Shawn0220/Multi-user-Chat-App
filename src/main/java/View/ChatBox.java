package View;

import Controller.updateOnlineUsers;
import Model.*;
import Controller.*;
import Controller.getOnlineUserIDs;
import Controller.one2oneMessageSend;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatBox extends JFrame {
    private Socket socket;

    private JTextArea chatArea, one2onechatArea;
    private JTextField messageField, one2oneMessageField;
    private JScrollPane scrollPane, one2onescrollPane;

    private DefaultListModel<String> fileListModel;
    private JList<String> fileList;
    private JButton downloadButton;

    // Add two instance variables to store UID and GID values
    private String uid, gid;
    private String uname;
    private String lastUpdate = null;

    private Map<String, JTextArea> chatMap;
    private JComboBox<String> userBox;
    private JComboBox<String> userComboBox;
    private String[] onlineUsers = {"User1", "测试用户2", "User3"}, preonlineUsers;
    private String now2Whom = "0", isChange;
    private String now2whomID = "0";

    public ChatBox(Socket socket, String uid, String gid) {
        this.uid = uid;
        this.gid = gid;
        this.socket = socket;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if(socket!=null){
                        PrintWriter writer = new PrintWriter(socket.getOutputStream());
                        writer.println("-" + uid + " " + gid);
                        writer.flush();
                    }

                    System.out.println("Window closed");
//                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 获取当前该群组所有在线用户信息
        getOnlineUserIDs onlineUsersGetter = new getOnlineUserIDs(gid, uid);
        onlineUsers = onlineUsersGetter.getOnlineUserIDs(gid, uid);
        System.out.println(onlineUsers);

        users user = users.readUserXML(uid);
        uname = user.getUname();

        // 创建聊天界面
        setTitle("Chat Group " + gid + "                                                                 Hello," + uname);
        setSize(1100, 820);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // 窗口居中显示
        // 在线用户下拉框
        userComboBox = new JComboBox<>(onlineUsers);

        // 添加选项改变监听器，将选中值赋值给 now2Whom 变量
        // 每次重选对话用户，就加载与其的对话历史
        userComboBox.addActionListener(e -> {
            now2Whom = (String) userComboBox.getSelectedItem();

            Pattern pattern = Pattern.compile("(?<=ID)(.*?)(?=\\s)");
            Matcher matcher = pattern.matcher(now2Whom);
            if (matcher.find()) {
                System.out.println(matcher.group(0));
                now2whomID = matcher.group(0);
            }
            //打印用户ID
            System.out.println(now2Whom);
            try {
                new one2oneChat(one2onechatArea,uid,now2whomID,uname);
            } catch (TransformerException | ParserConfigurationException ex) {
                ex.printStackTrace();
            }
        });

//        if (isChange!=now2Whom){
//            isChange = now2Whom;
//            System.out.println(isChange);
//        }
//        System.out.println(now2Whom);

        // Create the text area for displaying messages
        chatArea = new JTextArea();
        chatArea.setEditable(false);

        one2onechatArea = new JTextArea();
        one2onechatArea.setEditable(false);

        // Add scrolling functionality to the text area
        scrollPane = new JScrollPane(chatArea);
        one2onescrollPane = new JScrollPane(one2onechatArea);

        //create send button and message field
        JButton sendButton=new JButton("Send");
        ImageIcon sendButtonicon = new ImageIcon("src/main/java/appData/imgs/124124.jpg");
        Image newsendButtoniconImg = sendButtonicon.getImage().getScaledInstance(110, 100, Image.SCALE_SMOOTH);
        Icon newSendButtonicon = new ImageIcon(newsendButtoniconImg);
        // 将图片设置为按钮的图标
        sendButton.setIcon(newSendButtonicon);

        JButton one2oneSendButton=new JButton("私聊他~");

        // 图片发送按钮
        JButton picButton=new JButton("File");
        ImageIcon picButtonIcon = new ImageIcon("src/main/java/appData/imgs/smile.jpg");
        // 调整图片的大小以适应按钮大小
        Image newPicButtonIconImg = picButtonIcon.getImage().getScaledInstance(110, 100, Image.SCALE_SMOOTH);
        Icon newPicButtonIcon = new ImageIcon(newPicButtonIconImg);
        // 将图片设置为按钮的图标
        picButton.setIcon(newPicButtonIcon);

        JButton fileButton=new JButton("向群组上传文件");
//        ImageIcon fileButtonicon = new ImageIcon("src/main/java/appData/imgs/fileimg.jpg");
        // 调整图片的大小以适应按钮大小
//        Image newfileButtoniconImg = fileButtonicon.getImage().getScaledInstance(110, 100, Image.SCALE_SMOOTH);
//        Icon newfileButtonicon = new ImageIcon(newfileButtoniconImg);
        // 将图片设置为按钮的图标
//        fileButton.setIcon(newfileButtonicon);

        messageField=new JTextField();
        one2oneMessageField =new JTextField();

        JPanel panel=new JPanel(new BorderLayout());
        panel.setLayout(null);

        String fromDic = "src/main/java/appData/groupFiles/G" + gid;
        // 在chatBox.java中创建FileDownloadFrame对象并加载文件列表
        FileDownloadFrame fileDownloadFrame = new FileDownloadFrame(fromDic, uid);
        fileDownloadFrame.loadFilesFromDirectory(fromDic);

        // 创建JPanel对象，并将downloadButton和fileList添加到其中
        JPanel fileDownloadPanel = new JPanel(new BorderLayout());
        fileDownloadPanel.add(fileDownloadFrame.fileList, BorderLayout.CENTER);
        fileDownloadPanel.add(fileDownloadFrame.downloadButton, BorderLayout.SOUTH);

        JScrollPane fileScrollPane = new JScrollPane(fileDownloadPanel);

        scrollPane.setBounds(new Rectangle(10,10,800,650));
        one2onescrollPane.setBounds(new Rectangle(830,60,240,220));
        messageField.setBounds(new Rectangle(10,670,800,100));
        one2oneMessageField.setBounds(new Rectangle(835,290,230,60));
        sendButton.setBounds(new Rectangle(838,670,100,100));
        one2oneSendButton.setBounds(new Rectangle(835,360,230,30));
        fileButton.setBounds(new Rectangle(835,600,230,30));
        userComboBox.setBounds(new Rectangle(830,10,240,35));
        userComboBox.setPreferredSize(new Dimension(200, 30 * onlineUsers.length));
        fileScrollPane.setBounds(new Rectangle(835,400,230,200));
        picButton.setBounds(new Rectangle(957,670,100,100));

        // 上方面板
        JPanel mJpanel = new JPanel();
        mJpanel.setBackground(Color.white);
        mJpanel.setLayout(new BoxLayout(mJpanel, BoxLayout.Y_AXIS));
        JScrollPane mScroll = new JScrollPane(mJpanel);
        mScroll.getViewport().setBackground(Color.white);
        mScroll.setBounds(new Rectangle(10,10,800,650));

        panel.add(picButton);
        panel.add(fileScrollPane);
//        panel.add(scrollPane);
        panel.add(one2onescrollPane);
        panel.add(sendButton);
        panel.add(one2oneSendButton);
        panel.add(one2oneMessageField);
        panel.add(messageField);
        panel.add(fileButton);
        panel.add(userComboBox);
        panel.add(mScroll);
        Color palePink = new Color(174, 214, 241);
        panel.setBackground(palePink);
        add(panel);// add all components to frame

        // 群聊消息发送按钮点击事件
        // add actionlistener to send button
//        sendButton.addActionListener(e -> new sendMessage(false, messageField, uid, uname, gid));
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage sender = new sendMessage();
                sender.sendMessage(false, messageField, uid, uname, gid);
            }
        });
        // 发送图片事件
        picButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage sender = new sendMessage();
                sender.sendMessage(true, messageField, uid, uname, gid);
            }
        });
        // 私聊消息发送
        one2oneSendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                one2oneMessageSend.addOne2OneMessage(one2oneMessageField, uid, now2whomID);
                JOptionPane.showMessageDialog(null, "私聊消息已发送！");
                one2oneMessageField.setText("");
                try {
                    new one2oneChat(one2onechatArea,uid,now2whomID,uname);
                } catch (TransformerException ex) {
                    ex.printStackTrace();
                } catch (ParserConfigurationException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // 文件上传按钮点击事件
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建FileUploadFrame窗口并显示出来
                new FileUploadFrame( gid);
            }
        });

        // 加载聊天记录
        System.out.println("loading chat history.");
        chatField mycf = new chatField(mJpanel, gid);
        loadGroupHistory HistoryLoader = new loadGroupHistory();
        HistoryLoader.loadGroupHistory(gid, mJpanel);

        // 历史记录加载完毕
        JTextArea addTextArea = new JTextArea();
        addTextArea.setEditable(false);
        addTextArea.setPreferredSize(new Dimension(800, 30));
        addTextArea.setText("-----------------------------------------以        上        是        本        群        组        历        史        信        息-----------------------------------------");
        mJpanel.add(addTextArea);

//        chatArea.append("-----------------------------------------以        上        是        本        群        组        历        史        信        息-----------------------------------------");
        setVisible(true);//set visibility true

        // 更新实时消息
//        updateMessage updater = new updateMessage();
//        String templastUpdate = updater.updateChatArea(mJpanel, gid, lastUpdate); // 初始化chatArea
        updateMessage updater = new updateMessage();
        // 设置一个计时器来每0.01秒钟更新一次消息
        Timer timer = new Timer(10, e -> {
//            updateMessage updater = new updateMessage();
            //当上次更新时间与cache里最新消息时间不同，则需要向用户更新最新消息
            updater.updateChatArea(mJpanel, gid);

            try {
                new one2oneChat(one2onechatArea, uid, now2whomID, uname);
            } catch (TransformerException ex) {
                ex.printStackTrace();
            } catch (ParserConfigurationException ex) {
                ex.printStackTrace();
            }
        });
        timer.start();

        updateOnlineUsers nowOnlineUserInfo = new updateOnlineUsers();
        // 定时更新在线用户
        Timer onlineUsertimer = new Timer(2000,e->{
            nowOnlineUserInfo.updateOnlineUsers(gid, uid);
            onlineUsers = nowOnlineUserInfo.onlineUsers;
//            System.out.println(uid + " " +  Arrays.toString(onlineUsers) + "" +  Arrays.toString(preonlineUsers));
//            System.out.println("userComboBox: " + nowOnlineUserInfo.userComboBox);
//            System.out.println("onlineUsers: " + Arrays.toString(nowOnlineUserInfo.onlineUsers));
            if(!Arrays.equals(preonlineUsers,onlineUsers)) {
                preonlineUsers = onlineUsers;
                System.out.println("Here");
                userComboBox.setModel(new DefaultComboBoxModel<>(onlineUsers));
            }
        });
//        Timer onlineUsertimer = new Timer(100, e -> new updateOnlineUsers());
        onlineUsertimer.start();


        // 每隔两秒重新加载文件列表
        Timer fileTimer = new Timer(2000, e -> {
            fileDownloadFrame.loadFilesFromDirectory(fromDic);
        });
        fileTimer.start();

    }

    public static void main(String[] args) {
        BufferedReader inputStream = null;
//        SwingUtilities.invokeLater(() -> new ChatBox("2", "2"));
        SwingUtilities.invokeLater(() -> new ChatBox( null,"4", "2"));
    }
}