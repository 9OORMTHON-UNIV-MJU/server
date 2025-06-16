package goormthonUniv.MJU.server.Article.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileStore {

    private final String uploadDir = System.getProperty("user.dir") + "/upload/images";

    public String store(MultipartFile file) {
        try {
            String uuid = UUID.randomUUID().toString();
            String originalName = file.getOriginalFilename();
            String extension = originalName.substring(originalName.lastIndexOf("."));
            String storeFileName = uuid + extension;

            makeFolderIfNotExist();

            String fullPath = uploadDir + "/" + storeFileName;
            file.transferTo(new File(fullPath));

            return fullPath;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패: " + file.getOriginalFilename(), e);
        }
    }

    private void makeFolderIfNotExist() {
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
