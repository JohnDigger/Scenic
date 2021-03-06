package com.example.util;

/**
 * @author 贾佳
 * @date 2021/8/4 16:37
 */
//生成新的文件名
public class FileNameUtil {
    /**
     * 获取文件后缀
     *
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     *
     * @param fileOriginName 源文件名
     * @return
     */
    public static String getFileName(String fileOriginName) {
        return UUIDUtils.getUUID() + FileNameUtil.getSuffix(fileOriginName);
    }
}
