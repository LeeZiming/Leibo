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
        String deviceId = SysInfoUtil.getImei();
        // 中国时区
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        long time = calendar.getTimeInMillis();
        String timestamp = time + "";
        // token处理
        String token = TokenManager.getToken();
        if (!TextUtils.isEmpty(token)) {
            token = DigestUtils.md5Hex(token + timestamp);
        }
        String loginToken = TokenManager.getLoginToken();
        // 拼接加密字段
        String arg0 = TokenManager.getUseCode() + "_" + deviceId;
        // 拼接密钥key码
        String key = "";
        if (!TextUtils.isEmpty(deviceId)) {
            key = deviceId.substring(0, 1) + "�=�9��";
        }
        // 加密权限码
        String code = DataCodec.dataEncrypt(arg0, key.getBytes("utf-8"));
        // 判断 loginName是否必须是手机号
        String url = original.url().toString();
        boolean isLoginNamePhone = false;
        if (url.contains(KeyConstant.URL_USER_LOGIN) || url.contains(KeyConstant.URL_USER_REGISTER) || TextUtils.isEmpty(TokenManager.getLoginName()) || url.contains(KeyConstant.URL_COMMON_SMS_CODE)) {
            isLoginNamePhone = true;
        }
        String loginName = isLoginNamePhone ? TokenManager.getLoginPhone() : TokenManager.getLoginName();
        if (url.contains(KeyConstant.URL_TRY_DRIVE_ADD) && !TokenManager.iamLoggedIn()) {
            loginName = "";
        }

        String version = "";
        try {
            PackageInfo packageInfo = LeiboApplication.getInstance().getPackageManager().getPackageInfo(LeiboApplication.getInstance().getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 拼接header参数
        Request.Builder requestBuilder = original.newBuilder()
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("User-Agent", "yundu-android-phone102")
                .header("loginName", loginName)
                .header("smartDeviceId", deviceId)
                .header("tuDeviceId", "")
                .header("token", token)
                .header("timestamp", timestamp)
                .header("version", version)
                .header("useCode", code)
                .header("tspFlag", "1")
                .header("carType", "7")
                .header("deviceToken", deviceId.replaceAll("-", "_"))
                .header("deviceType", SysInfoUtil.getDeviceModel())
                .header("appChannel", SysInfoUtil.getAppChannel())
                .header("loginToken", loginToken);
        // build header
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
