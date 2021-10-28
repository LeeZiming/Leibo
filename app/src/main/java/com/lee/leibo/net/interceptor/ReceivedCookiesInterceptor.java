package com.lee.leibo.net.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {
    private Context mContext;

    public ReceivedCookiesInterceptor(Context mContext) {

        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
                Log.v("OkHttp", header);
            }
            SharedPreferences.Editor config = mContext.getSharedPreferences("config", mContext.MODE_PRIVATE)
                    .edit();
            config.putStringSet("cookie", cookies);
            config.commit();
        }

        return originalResponse;
    }
}