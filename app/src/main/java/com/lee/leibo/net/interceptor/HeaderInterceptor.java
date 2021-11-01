package com.lee.leibo.net.interceptor;

import android.content.pm.PackageInfo;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.lee.leibo.app.LeiboApplication;
import com.lee.leibo.constants.KeyConstant;
import com.lee.leibo.net.TokenManager;
import com.lee.leibo.utils.SysInfoUtil;
import com.lee.leibo.utils.code.DataCodec;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();

        // token处理
        String token = TokenManager.getAccessToken();

        String uid = TokenManager.getUid();


        // 拼接header参数
        Request.Builder requestBuilder = original.newBuilder();
//                .header("Content-Type", "application/json;charset=UTF-8")
//                .header("User-Agent", "yundu-android-phone102")
//                .header("access_token", token);

        // build header
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
