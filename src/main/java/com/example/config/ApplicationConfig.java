package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ApplicationConfig extends WebMvcConfigurerAdapter {
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[] { "/picture/**" }).addResourceLocations(new String[] { "file:C:/scenic/picture/" });
        registry.addResourceHandler(new String[] { "/audio/**" }).addResourceLocations(new String[] { "file:C:/scenic/audio/" });
        registry.addResourceHandler(new String[] { "/video/**" }).addResourceLocations(new String[] { "file:C:/scenic/video/" });
    }
}
