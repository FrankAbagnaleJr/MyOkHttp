package com.kyrie.utils;

import okhttp3.OkHttpClient;

/**
 * 懒汉单例bean
 */
public class OkHttpClientInstenceL {
    static OkHttpClient client;
    private OkHttpClientInstenceL(){}

    public static OkHttpClient getClient(){
        if (client == null) {
            client = new OkHttpClient().newBuilder().build();
        }
        return client;
    }


}
