package Controller;

import Controller.getOnlineUserIDs;

import javax.swing.*;

public class updateOnlineUsers {
    public String[] onlineUsers, preOnlineUsers;
    public JComboBox<String> userComboBox;
    public void updateOnlineUsers(String gid, String uid ) {
        // 获取最新的在线用户列表
        getOnlineUserIDs onlineUsersGetter = new getOnlineUserIDs(gid, uid);
        onlineUsers = onlineUsersGetter.getOnlineUserIDs(gid, uid);
//        System.out.println(onlineUsers);
        // 更新下拉框中的选项
//        if(!Arrays.equals(preOnlineUsers,onlineUsers)){
//            preOnlineUsers = onlineUsers;
////            userComboBox.setModel(new DefaultComboBoxModel<>(onlineUsers));
//            System.out.println("更新在线用户选择框");
//        }
    }}
