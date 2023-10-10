package com.kyrie.utils;
import okhttp3.MediaType;

/**
 * 工具类
 */
public class Utils {

    public static MediaType getMediaType(String s){
        if(s == null && "".equals(s)) return null;

        if (s.equals("multipart/form-data")) {
            MediaType mediaType = MediaType.parse("multipart/form-data");
            return mediaType;
        }

        return null;
    }
}
