package View;

import Controller.downloadFile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileDownloadFrame extends JFrame {

    public DefaultListModel<String> fileListModel;
    public JList<String> fileList;
    public JButton downloadButton;

    public FileDownloadFrame(String fromDic, String uid) {
        setTitle("文件下载");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null); // 窗口居中显示
        setLayout(new BorderLayout());

        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int index = fileList.locationToIndex(evt.getPoint());
                    String fileName = fileListModel.getElementAt(index);
                    downloadFile downloader = new downloadFile();
                    downloader.downloadFile(fileName,  fromDic, uid);
                }
            }
        });
        add(new JScrollPane(fileList), BorderLayout.CENTER);

        downloadButton = new JButton("群组文件下载");
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = fileList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String fileName = fileList.getSelectedValue();
                    downloadFile dowwnloader = new downloadFile();
                    dowwnloader.downloadFile(fileName,  fromDic, uid);
                } else {
                    JOptionPane.showMessageDialog(FileDownloadFrame.this,
                            "请选择一个文件进行下载", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        add(downloadButton, BorderLayout.SOUTH);
    }

    public void setfileListModel(DefaultListModel<String> fileListModel){
        this.fileListModel = fileListModel;
    }

    public void loadFilesFromDirectory(String directoryPath) {
        // 清空fileListModel模型
        fileListModel.clear();
        try {
            Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt")
                            || path.toString().endsWith(".docx")
                            || path.toString().endsWith(".jpg")
                            || path.toString().endsWith(".png")
                            || path.toString().endsWith(".mp3")
                            || path.toString().endsWith(".doc")
                            || path.toString().endsWith(".zip")
                            || path.toString().endsWith(".mp4"))
                    .forEach(path -> fileListModel.addElement(path.getFileName().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String fromDic = "src/main/java/appData/groupFiles/G2";
            String uid = "2";
            FileDownloadFrame frame = new FileDownloadFrame( fromDic, uid);
            frame.loadFilesFromDirectory(fromDic);
            frame.setVisible(true);
        });
    }
}
