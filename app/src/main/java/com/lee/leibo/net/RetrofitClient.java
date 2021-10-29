package com.lee.leibo.net;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.lee.leibo.BuildConfig;
import com.lee.leibo.app.LeiboApplication;
import com.lee.leibo.net.interceptor.HeaderInterceptor;
import com.lee.leibo.net.interceptor.ReceivedCookiesInterceptor;
import com.lee.leibo.net.interceptor.ResponseInterceptor;


import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.goldze.mvvmhabit.http.interceptor.BaseInterceptor;
import me.goldze.mvvmhabit.http.interceptor.logging.Level;
import me.goldze.mvvmhabit.http.interceptor.logging.LoggingInterceptor;
import me.goldze.mvvmhabit.utils.KLog;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qiaogb on 2019/5/10.
 * RetrofitClient封装单例类, 实现网络请求
 */
public class RetrofitClient {

    //超时时间
    private static final int DEFAULT_TIMEOUT = 10;//20;
    //缓存时间
    private static final int CACHE_TIMEOUT = 10 * 1024 * 1024;

    private Retrofit retrofit;

    public static final String weiboUrl1 = "https://api.weibo.com/2/";// 正式环境


    public static String weiboUrl = getDefaultYudoUrl();

    private static final String getDefaultYudoUrl() {
        String defaultUrl = SPUtils.getInstance().getString("yudoUrl");
        if (TextUtils.isEmpty(defaultUrl)) {
            return weiboUrl1; //以后测试想改接口，改此处    默认莆田测试 上线时改为正式环境
        }
        return defaultUrl;
    }

    private Cache cache = null;
    private File httpCacheDirectory;
    public ClearableCookieJar cookieJar;

    public static RetrofitClient getInstance() {
        return SingletonHolder.getInstance();
    }


    private static class SingletonHolder {
        private static RetrofitClient INSTANCE;

        public static RetrofitClient getInstance() {
            if (INSTANCE == null) {
                synchronized (SingletonHolder.class) {
                    if (INSTANCE == null) {
                        INSTANCE = new RetrofitClient(weiboUrl, null);
                    }
                }
            }
            return INSTANCE;
        }
    }

    public static String getYudoUrl() {
        return weiboUrl;
    }

    private RetrofitClient(String url, Map<String, String> headers) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put("Authorization", "");
        if (TextUtils.isEmpty(url)) {
            url = weiboUrl1;
        }

        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(LeiboApplication.getInstance().getCacheDir(), "goldze_cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, CACHE_TIMEOUT);
            }
        } catch (Exception e) {
            KLog.e("Could not create http cache", e);
        }
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(LeiboApplication.getInstance()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor(new BaseInterceptor(headers))
                .addInterceptor(new ResponseInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new ReceivedCookiesInterceptor(LeiboApplication.getInstance()))
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier)
                .addInterceptor(
                        new LoggingInterceptor
                                .Builder()//构建者模式
                                .loggable(BuildConfig.DEBUG) //是否开启日志打印
                                .setLevel(Level.BASIC) //打印的等级
                                .log(Platform.INFO) // 打印类型
                                .request("Request") // request的Tag
                                .response("Response")// Response的Tag
                                .build()
                )
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))// 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
    }

    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

}
