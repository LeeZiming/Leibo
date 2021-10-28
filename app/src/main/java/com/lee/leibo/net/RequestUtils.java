package com.lee.leibo.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public final class RequestUtils {

    private static Gson sGson;

    public static RequestBody getRequestBody(Map<String, Object> map) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), getJson(map));
    }

    private static String getJson(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            throw new JsonSyntaxException("Map is empty!");
        }
        if (sGson == null) {
            sGson = new GsonBuilder().disableHtmlEscaping().create();
        }
        return sGson.toJson(map);
    }

}
