package com.javarush.kovalinsky;

import com.javarush.kovalinsky.controller.FrontController;
import com.javarush.kovalinsky.controller.ImageServlet;
import com.javarush.kovalinsky.filter.AuthorizationFilter;
import com.javarush.kovalinsky.filter.ErrorCleanerFilter;
import com.javarush.kovalinsky.server.EmbeddedTomcat;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {
    public static void main(String[] args) {
        log.info("app init");
        EmbeddedTomcat embeddedTomcat = new EmbeddedTomcat(8085);
        embeddedTomcat.addAnnotatedServlets(new FrontController(), new ImageServlet());
        embeddedTomcat.addAnnotatedFilters(new ErrorCleanerFilter(), new AuthorizationFilter());
        embeddedTomcat.startServer();
        log.info("open http://localhost:8085 for test app");
    }
}


