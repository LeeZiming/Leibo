package com.lee.leibo.net.interceptor;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 项目描述：云度
 * 创建人：Sivan
 * 类描述：响应拦截器
 * 日期：2020/12/9
 * 版权所有：云度智能互联
 */
public class ResponseInterceptor implements Interceptor {

    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse = chain.proceed(request);

        if (request.url() != null && !TextUtils.isEmpty(request.url().toString()) && request.url().toString().contains("loginManager/autoLogin")) {
            return originalResponse;
        }

        if (originalResponse.body() != null && originalResponse.body().contentType() != null) {
            MediaType mediaType = originalResponse.body().contentType();
            String content = originalResponse.body().string();
            try {
                JSONObject object = new JSONObject(content);
                int code = object.optInt("code");
                int errCode = object.optInt("error_code");

//                if (code == KeyConstant.ERR_LOGIN_OTHER_DEVICE
//                        || code == KeyConstant.ERR_TOKEN_INVALID
////                        || code == KeyConstant.ERR_TOKEN_EMPTY
//                        || errCode == KeyConstant.ERR_LOGIN_OTHER_DEVICE
//                        || errCode == KeyConstant.ERR_TOKEN_INVALID
//                        /*|| errCode == KeyConstant.ERR_TOKEN_EMPTY*/) {
//                    //重走注册登录流程
//                    KLog.e("login invalid!");
//                    TokenManager.setToken("");
//                    TokenManager.setLoginToken("");
//                    if (!ActivityUtils.getTopActivity().getClass().equals(MainActivity.class)) {
//                        RxHelper.delay(150, data -> {
////                            EventBus.getDefault().post(new GoLoginEvent());
//                            LoginStartManager.loginWithFinishToCurrent();
//                        });
//                    } else {
//                        EventBus.getDefault().post(new LoginStatusEvent());
//                    }
//                }
            } catch (JSONException e) {
                e.printStackTrace();
                ResponseBody responseBody = ResponseBody.create(mediaType, content);
                return originalResponse.newBuilder().body(responseBody).build();
            }

            ResponseBody responseBody = ResponseBody.create(mediaType, content);
            return originalResponse.newBuilder().body(responseBody).build();
        }

        return originalResponse;
    }
}
