package com.javarush.kovalinsky.controller;

import com.javarush.kovalinsky.config.Winter;
import com.javarush.kovalinsky.service.ImageService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet(value = "/images/*", name = "ImageServlet")
public class ImageServlet extends HttpServlet {

    private final ImageService imageService = Winter.find(ImageService.class);


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String requestURI = req.getRequestURI();
        String target = req.getContextPath() + "/images/";
        String imageName = requestURI.replace(target, "");
        Path path = imageService.getImagePath(imageName);
        Files.copy(path, resp.getOutputStream());
    }
}
