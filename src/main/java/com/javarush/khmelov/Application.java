package com.javarush.khmelov;


import com.javarush.khmelov.controller.FrontController;
import com.javarush.khmelov.controller.ImageServlet;
import jakarta.servlet.Servlet;
import jakarta.servlet.annotation.WebServlet;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Slf4j
public class Application {
    public static void main(String[] args) {
        log.info("app init");
        EmbeddedTomcat embeddedTomcat = new EmbeddedTomcat(8085, new FrontController(), new ImageServlet());
        embeddedTomcat.run();
        log.info("open http://localhost:8085 for test app");
    }
}


class EmbeddedTomcat extends Tomcat {
    public EmbeddedTomcat(int port, Servlet... servlets) {

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        this.setPort(port);
        final File root = new File(
                Objects.requireNonNull(
                        this.getClass().getResource("/")
                ).getPath()
        );
        this.getHost().setAppBase(root.getAbsolutePath());
        this.addWebapp("", root.getAbsolutePath());
        this.getConnector();
        for (Servlet servlet : servlets) {
            Class<? extends Servlet> servletClass = servlet.getClass();
            Wrapper wrapper = this.addServlet("", servletClass.getSimpleName(), servlet);
            WebServlet annotationParam = servletClass.getAnnotation(WebServlet.class);
            wrapper.setLoadOnStartup(annotationParam.loadOnStartup());
            Stream.of(annotationParam.value(), annotationParam.urlPatterns())
                    .flatMap(Arrays::stream)
                    .forEach(wrapper::addMapping);
        }
    }

    public void run() {
        try {
            this.start();
            CompletableFuture.runAsync(() -> {
                this.getServer().await();
            });
            Thread.sleep(1000);
        } catch (LifecycleException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
