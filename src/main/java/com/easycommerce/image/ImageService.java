package com.easycommerce.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    String uploadImage(String path, MultipartFile image) throws IOException;
}
