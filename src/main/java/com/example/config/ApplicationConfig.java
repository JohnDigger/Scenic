package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ApplicationConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/picture/**").addResourceLocations("file:C:/scenic/picture/");
        registry.addResourceHandler("/audio/**").addResourceLocations("file:C:/scenic/audio/");
//        registry.addResourceHandler("/text/**").addResourceLocations("file:C:/scenic/text/");
//        registry.addResourceHandler("/list/**").addResourceLocations("file:C:/scenic/list/");
    }
}