package de.shoppinglist.service;

import de.shoppinglist.dto.UserDTO;
import de.shoppinglist.entity.User;
import de.shoppinglist.exception.EntityNotFoundException;
import de.shoppinglist.exception.InternalServerErrorException;
import de.shoppinglist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class ProfilService {

    private UserRepository userRepository;
    private static String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads";

    @Autowired
    public ProfilService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUserProfile(UserDTO user) {
        try {
            User userDB = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));

            if (user.getAvatarBase64() != null) {
                saveBase64File(userDB, user.getAvatarBase64());
            }

            return userRepository.save(userDB);
        } catch (IOException e) {
            throw new InternalServerErrorException("Failed to store file.");
        }
    }

    private void saveBase64File(User user, String base64Data) throws IOException {
        if (!base64Data.contains(",")) {
            throw new IllegalArgumentException("Invalid Base64 data");
        }

        // Remove the prefix
        String[] parts = base64Data.split(",");
        String imageString = parts[1];

        // Decode the Base64 string
        byte[] imageBytes = Base64.getDecoder().decode(imageString);

        user.setAvatar(imageBytes);
    }
}
