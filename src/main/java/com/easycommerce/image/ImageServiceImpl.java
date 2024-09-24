package com.easycommerce.image;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {
        String originalImageName = image.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String imageName = randomId.concat(originalImageName.substring(originalImageName.lastIndexOf('.')));
        String imagePath = path + File.pathSeparator + imageName;

        File folder = new File(path);
        if (!folder.exists())
            folder.mkdir();

        Files.copy(image.getInputStream(), Paths.get(imagePath));

        return imageName;
    }
}
