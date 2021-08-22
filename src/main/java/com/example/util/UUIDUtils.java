package com.example.util;

import java.util.UUID;

/**
 * @author 贾佳
 * @date 2021/8/4 16:38
 */
//使用UUID来对图片名称进行重新生成。
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}