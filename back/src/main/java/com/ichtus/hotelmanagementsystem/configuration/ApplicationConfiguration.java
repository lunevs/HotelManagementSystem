package com.ichtus.hotelmanagementsystem.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMVC application configuration file.
 *
 * @author smlunev
 */
@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

    @Value("${custom.rest.basePath}")
    private String basePath;

    /**
     * Override Application path configuration. Defines Application API prefix.
     * @param configurer Spring default PathMatchConfigurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(basePath, el -> true);
    }
}
