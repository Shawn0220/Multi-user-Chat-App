package Controller;

import View.FileDownloadFrame;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class downloadFile {
    public void downloadFile(String fileName, String fromDic, String uid) {
        String fileUrl = "file:///C:/Users/52954/Desktop/tututu/untitled/" + fromDic + "/" + fileName;  // 文件的URL地址
        System.out.println(fileUrl);
        String savePath = "src/main/java/appData/userDownload/user" + uid + "/" + fileName;          // 下载后保存的路径
        try {
            URL url = new URL(fileUrl);
            InputStream inputStream = url.openStream();

            FileOutputStream fileOutputStream = new FileOutputStream(new File(savePath));
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            fileOutputStream.close();
            inputStream.close();

//            JOptionPane.showMessageDialog(FileDownloadFrame.this,
//                    "文件下载完成", "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
//            JOptionPane.showMessageDialog(FileDownloadFrame.this,
//                    "文件下载失败", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}

