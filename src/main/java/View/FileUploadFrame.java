package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadFrame extends JFrame {

    private JButton chooseFileButton;
    private JTextArea infoTextArea;
    private JProgressBar progressBar;

    public FileUploadFrame(String gid) {
        super("文件上传");

        // 创建组件
        chooseFileButton = new JButton("选择文件");
        infoTextArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(infoTextArea);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setVisible(true);
        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        progressPanel.add(progressBar, BorderLayout.CENTER);

        // 添加事件监听器
        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(FileUploadFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        // 将文件上传到指定目录
                        uploadFile(selectedFile, "src/main/java/appData/groupFiles/G" + gid);
                        infoTextArea.append("文件上传成功！\n");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        infoTextArea.append("文件上传失败：" + ex.getMessage() + "\n");
                    }
                }
            }
        });

        // 布局组件
        JPanel jPanel = new JPanel();
        jPanel.add(chooseFileButton);
        jPanel.add(scrollPane);

        add(jPanel, BorderLayout.CENTER);
        add(progressPanel, BorderLayout.PAGE_END);

        // 设置窗口属性
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void uploadFile(File file, String destDirPath) throws IOException {
        Path destDir = Paths.get(destDirPath);

        if (!Files.exists(destDir)) {
            Files.createDirectories(destDir);
        }

        Path destFile = Paths.get(destDirPath, file.getName());

        FileInputStream fis = new FileInputStream(file);
        long fileSize = file.length();

        progressBar.setValue(0);
        progressBar.setVisible(true);

        FileOutputStream fos = new FileOutputStream(destFile.toFile());
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        byte[] buffer = new byte[1024];
        int bytesRead;
        long totalBytesRead = 0;

        while ((bytesRead = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
            totalBytesRead += bytesRead;
            int percentCompleted = (int) ((totalBytesRead * 100) / fileSize);
            progressBar.setValue(percentCompleted);
        }

        bos.close();
        fos.close();
        fis.close();

        progressBar.setVisible(true);
    }

    public static void main(String[] args) {
        new FileUploadFrame("2");
    }
}
