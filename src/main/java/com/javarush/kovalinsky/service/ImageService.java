package com.javarush.kovalinsky.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ImageService {

    private static final String IMAGES_FOLDER = "images";
    private static final String IMAGE_PART_NAME = "image";
    private static final String NO_IMAGE_PNG = "no-image.png";
    private static final List<String> EXTENSIONS = List.of(
            ".jpg", ".jpeg", ".png", ".bmp", ".gif", ".webp"
    );

    public final Path WEB_INF = Paths.get(URI.create(
                    Objects.requireNonNull(
                            ImageService.class.getResource("/")
                    ).toString()))
            .getParent();

    private final Path imagesFolder;

    public ImageService() throws IOException {
        Stream<Path> pathStream = Files.walk(WEB_INF);
        try (pathStream) {
            imagesFolder = pathStream
                    .filter(path -> path.endsWith("WEB_INF" + File.separator + IMAGES_FOLDER))
                    .findAny()
                    .orElse(WEB_INF.resolve(IMAGES_FOLDER));
            Files.createDirectories(imagesFolder);
        }
    }

    public Path getImagePath(String fileName) {
        return EXTENSIONS.stream()
                .map(ext -> imagesFolder.resolve(fileName + ext))
                .filter(Files::exists)
                .findAny()
                .orElse(imagesFolder.resolve(NO_IMAGE_PNG));
    }

    public void uploadImage(HttpServletRequest req, String imageId) throws IOException, ServletException {
        Part data = req.getPart(IMAGE_PART_NAME);
        if (Objects.nonNull(data) && data.getInputStream().available() > 0) {
            String fileName = data.getSubmittedFileName();
            String ext = fileName.substring(fileName.lastIndexOf("."));
            deleteOldFiles(imageId);
            fileName = imageId + ext;
            uploadImageInternal(fileName, data.getInputStream());
        }
    }

    private void deleteOldFiles(String fileName) {
        EXTENSIONS.stream()
                .map(ext -> imagesFolder.resolve(fileName + ext))
                .filter(Files::exists)
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void uploadImageInternal(String name, InputStream data) throws IOException {
        try (data) {
            if (data.available() > 0) {
                Files.copy(data, imagesFolder.resolve(name), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
