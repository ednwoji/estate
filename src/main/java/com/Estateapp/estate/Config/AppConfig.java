package com.Estateapp.estate.Config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

    @Configuration
    @ComponentScan(basePackages = {"com.Estateapp.estate"})
    public class AppConfig extends WebMvcConfigurationSupport {


        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
                    .addResourceHandler("/**")
                    .addResourceLocations("classpath:/static/")
                    .addResourceLocations("classpath:/uploaded-files/");
        }

    }
