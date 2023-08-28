package View;

import javax.swing.*;
import java.awt.*;


public class LoginScreen extends JFrame {

    private JLabel uidLabel, gidLabel;
    private JTextField uidField, gidField;
    private JButton loginButton;
    private String uid, gid;

    public LoginScreen() {
        setTitle("Login Screen");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // 窗口居中显示

        // Create the UI components for user inputs and login button
        uidLabel = new JLabel("             User ID:");
        gidLabel = new JLabel("             Group ID:");
        uidField = new JTextField();
        gidField = new JTextField();
        loginButton= new JButton("Login");

        // Add an action listener to the login button
        loginButton.addActionListener(e -> loginUser());

        // Create a panel to hold all UI components
        JPanel panel=new JPanel(new GridLayout(3,2));
        panel.add(uidLabel);
        panel.add(uidField);
        panel.add(gidLabel);
        panel.add(gidField);
        panel.add(loginButton, BorderLayout.SOUTH);// add all components to frame

        add(panel);// add all components to frame

        setVisible(true);//set visibility true

    }

    /**
     * Method called when user clicks on Login Button.
     */
    public void loginUser()
    {
        uid=uidField.getText().trim();//get typed text from field
        gid=gidField.getText().trim();

        // Check if UID and GID are valid (you can customize this validation as per your requirements)
        if(!uid.isEmpty() && !gid.isEmpty()){
            dispose(); // Close the current window once logged in successfully

            // Pass UID and GID values as parameters while creating ChatBox window
//            System.out.println("debug open chatbox");
//            SwingUtilities.invokeLater(() -> new ChatBox(uid, gid));
        }
        else{
            JOptionPane.showMessageDialog(this,"Please enter valid User ID and Group ID","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    public String  getloginfo(){
        return uid + " " + gid;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen());
    }
}