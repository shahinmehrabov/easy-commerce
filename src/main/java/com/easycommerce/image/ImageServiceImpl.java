package com.easycommerce.image;

import com.easycommerce.exception.APIException;
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
        if (image == null || image.isEmpty())
            throw new APIException("Image is required");

        String originalImageName = image.getOriginalFilename();
        String imageName = UUID.randomUUID().toString();

        if (originalImageName != null && originalImageName.contains("."))
            imageName += originalImageName.substring(originalImageName.lastIndexOf('.'));

        String imagePath = path + File.pathSeparator + imageName;

        File folder = new File(path);
        if (!folder.exists())
            folder.mkdir();

        Files.copy(image.getInputStream(), Paths.get(imagePath));

        return imageName;
    }
}
