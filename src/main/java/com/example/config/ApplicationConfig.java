package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ApplicationConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/picture/**").addResourceLocations("file:D:\\javaweb\\Scenic\\src\\main\\resources\\static\\upload");
        registry.addResourceHandler("/audio/**").addResourceLocations("file:D:\\javaweb\\Scenic\\src\\main\\resources\\static\\upload");
//        registry.addResourceHandler("/text/**").addResourceLocations("file:C:/scenic/text/");
//        registry.addResourceHandler("/list/**").addResourceLocations("file:C:/scenic/list/");
    }
}