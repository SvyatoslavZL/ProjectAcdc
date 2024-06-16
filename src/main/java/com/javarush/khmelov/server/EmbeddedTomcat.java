package com.javarush.khmelov.server;

import jakarta.servlet.Filter;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.Servlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.filters.SetCharacterEncodingFilter;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author <a href=https://bit.ly/akhmelov>Alexander Khmelov</a>
 */
public class EmbeddedTomcat extends Tomcat {

    private final Context context;

    public EmbeddedTomcat(int port) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        this.setPort(port);
        String path = Objects.requireNonNull(this.getClass().getResource("/")).getPath();
        String root = new File(path).getAbsolutePath();
        this.getHost().setAppBase(root);
        context = this.addWebapp("", root);
    }

    public void addAnnotatedServlets(Servlet... servlets) {
        for (Servlet servlet : servlets) {
            Class<? extends Servlet> servletClass = servlet.getClass();
            Wrapper wrapper = this.addServlet("", servletClass.getSimpleName(), servlet);
            if (servletClass.isAnnotationPresent(MultipartConfig.class)) {
                MultipartConfig multipartConfig = servletClass.getAnnotation(MultipartConfig.class);
                wrapper.setMultipartConfigElement(new MultipartConfigElement(multipartConfig));
            }
            WebServlet annotationParam = servletClass.getAnnotation(WebServlet.class);
            wrapper.setLoadOnStartup(annotationParam.loadOnStartup());
            Stream.of(annotationParam.value(), annotationParam.urlPatterns())
                    .flatMap(Arrays::stream)
                    .forEach(wrapper::addMapping);
        }
    }

    public void addAnnotatedFilters(Filter... filters) {
        SetCharacterEncodingFilter utfSupport = new SetCharacterEncodingFilter();
        utfSupport.setEncoding("UTF-8");
        add(utfSupport);
        for (Filter filter : filters) {
            add(filter);
        }
    }

    private void add(Filter filter) {
        Class<? extends Filter> filterClass = filter.getClass();
        String name = filterClass.getName();
        FilterDef filterDef = new FilterDef();
        filterDef.setFilter(filter);
        filterDef.setFilterName(name);
        context.addFilterDef(filterDef);

        FilterMap filterMap = new FilterMap();
        filterMap.setFilterName(name);
        if (filterClass.isAnnotationPresent(WebFilter.class)) {
            WebFilter annotationParam = filterClass.getAnnotation(WebFilter.class);
            Stream.of(annotationParam.value(), annotationParam.urlPatterns())
                    .flatMap(Arrays::stream)
                    .forEach(filterMap::addURLPattern);
        } else {
            filterMap.addURLPattern("/*");
        }
        context.addFilterMap(filterMap);
    }

    public void startServer() {
        try {
            this.start();
            this.getConnector().start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }
}
