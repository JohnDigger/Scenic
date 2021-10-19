package com.example.util;


import com.example.exception.BackgroundException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2016/7/19.
 */
public class PicUploadUtil {

    private static final String dateFormat = "yyyy_MM_dd_HH_mm_SS_ssss";

    private static final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

    public static String formatDate() {

        return sdf.format(new Date());
    }

    public static String picRename(String pic_type) {

        System.out.println("添加某个商品名字-------------------------" + pic_type);
        String file_ture_name = ".";
        if (pic_type.equals("image/jpeg")) {
            file_ture_name = file_ture_name.concat("jpg");
        } else if (pic_type.equals("image/png")) {
            file_ture_name = file_ture_name.concat("png");
        } else if (pic_type.equals("image/bmp")) {
            file_ture_name = file_ture_name.concat("bmp");
        } else if (pic_type.equals("image/gif")) {
            file_ture_name = file_ture_name.concat("gif");
        } else throw new BackgroundException("上传图片类型不正确！");

        return PicUploadUtil.formatDate() + file_ture_name;
    }
}
