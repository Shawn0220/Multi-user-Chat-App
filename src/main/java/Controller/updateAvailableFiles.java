package Controller;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class updateAvailableFiles {
    public DefaultListModel<String> loadFilesFromDirectory(String directoryPath) {
        // 清空fileListModel模型
        DefaultListModel<String> fileListModel = null;
//        fileListModel.clear();
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
            return fileListModel;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileListModel;
    }
}
