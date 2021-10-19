package com.example.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class FastJsonJsonpHttpMessageConverter extends FastJsonHttpMessageConverter implements ResponseBodyAdvice {
    protected String[] jsonpParameterNames = new String[] { "jsoncallback", "jsonpcallback", "jsonp", "callback" };

    private static SerializerFeature[] features = new SerializerFeature[] { SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteEnumUsingToString, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.PrettyFormat, SerializerFeature.DisableCircularReferenceDetect };

    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        HttpServletRequest servletRequest = ((ServletServerHttpRequest)serverHttpRequest).getServletRequest();
        HttpServletResponse response = ((ServletServerHttpResponse)serverHttpResponse).getServletResponse();
        String callback = null;
        for (int i = 0; i < this.jsonpParameterNames.length; i++) {
            callback = servletRequest.getParameter(this.jsonpParameterNames[i]);
            if (callback != null)
                break;
        }
        if (callback != null) {
            String jsonString = JSON.toJSONString(o, features);
            jsonString = callback + "([" + jsonString + "])";
            byte[] bytes = jsonString.getBytes(getCharset());
            try {
                OutputStream out = null;
                ServletOutputStream servletOutputStream = response.getOutputStream();
                servletOutputStream.write(bytes);
                servletOutputStream.flush();
                servletOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return o;
    }

    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFastJsonConfig(fastjsonConfig());
        converter.setSupportedMediaTypes(getSupportedMediaType());
        return converter;
    }

    public FastJsonConfig fastjsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(features);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        return fastJsonConfig;
    }

    public List<MediaType> getSupportedMediaType() {
        ArrayList<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        return mediaTypes;
    }
}
