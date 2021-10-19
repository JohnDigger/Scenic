package com.example.config;

import java.io.File;
import javax.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

public class Config {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofBytes(102400000L));
        factory.setMaxRequestSize(DataSize.ofBytes(102400000L));
        String location = System.getProperty("user.dir") + "/data/tmp";
        System.out.println(location);
        File tmpFile = new File(location);
        if (!tmpFile.exists())
            tmpFile.mkdirs();
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }
}
