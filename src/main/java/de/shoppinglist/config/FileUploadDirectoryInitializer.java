package de.shoppinglist.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileUploadDirectoryInitializer {

    @PostConstruct
    public void init() {
        File uploadDir = new File(System.getProperty("user.dir") + "/uploads");
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
    }
}
