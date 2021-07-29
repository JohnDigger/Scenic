package com.example.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * FastJsonJsonpHttpMessageConverter
 * json转换组件，Spring返回Json数据时，加上jsonpcallback
 *
 * @author Lenjor
 * @version 1.0
 * @date 2019/10/14 11:30
 */
@ControllerAdvice
public class FastJsonJsonpHttpMessageConverter extends FastJsonHttpMessageConverter implements ResponseBodyAdvice {
    protected String[] jsonpParameterNames = new String[]{"jsoncallback", "jsonpcallback", "jsonp", "callback"};
    //FastJson序列化策略
    private static SerializerFeature[] features = new SerializerFeature[]{
            SerializerFeature.WriteNullNumberAsZero,
            SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteEnumUsingToString,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteNullBooleanAsFalse,
            SerializerFeature.PrettyFormat,
            SerializerFeature.DisableCircularReferenceDetect
    };

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
        HttpServletResponse response = ((ServletServerHttpResponse) serverHttpResponse).getServletResponse();

        String callback = null;
        for (int i = 0; i < jsonpParameterNames.length; i++) {
            callback = servletRequest.getParameter(jsonpParameterNames[i]);
            if (callback != null) {
                break;
            }
        }
        if (callback != null) {
            String jsonString = JSON.toJSONString(o, features);
            jsonString = new StringBuilder(callback).append("([").append(jsonString).append("])").toString();

            //根据前端需要返回对应的编码，默认均是utf-8
            byte[] bytes = jsonString.getBytes(getCharset());
            try {
                OutputStream out = null;
                out = response.getOutputStream();
                out.write(bytes);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return o;
    }

    @Override
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

    /**
     * FastJson 配置
     */
    public FastJsonConfig fastjsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(features);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        return fastJsonConfig;
    }

    /**
     * 支持的mediaType类型
     */
    public List<MediaType> getSupportedMediaType() {
        ArrayList<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        return mediaTypes;
    }
}

