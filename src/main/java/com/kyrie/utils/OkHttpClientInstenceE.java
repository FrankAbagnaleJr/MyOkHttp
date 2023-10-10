package com.kyrie.utils;

import okhttp3.OkHttpClient;
/**
 * 饿汉单例bean
 */
public class OkHttpClientInstenceE {
    private static OkHttpClient client = new OkHttpClient().newBuilder().build();
    private OkHttpClientInstenceE(){}

    public OkHttpClient getClient(){
        return client;
    }



}
