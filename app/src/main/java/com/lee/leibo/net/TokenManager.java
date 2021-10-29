package com.lee.leibo.net;

import android.text.TextUtils;

import me.goldze.mvvmhabit.utils.SPUtils;

public class TokenManager {

    private static SPUtils spUtils = SPUtils.getInstance("refresh_token");

    public static final String UID = "uid";
    public static final String USER_NAME = "username";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String SP_KEY_IMEI = "sp_key_imei"; //IMEI

    public static void setAccessToken(String token) {
        if (TextUtils.isEmpty(token)) {
            spUtils.remove(ACCESS_TOKEN);
        } else {
            spUtils.put(ACCESS_TOKEN, token);
        }
    }

    public static void setRefreshToken(String token) {
        if (TextUtils.isEmpty(token)) {
            spUtils.remove(REFRESH_TOKEN);
        } else {
            spUtils.put(REFRESH_TOKEN, token);
        }
    }

    public static void setLoginName(String name) {
        if (TextUtils.isEmpty(name)) {
            spUtils.remove(USER_NAME);
        } else {
            spUtils.put(USER_NAME, name);
        }
    }

    public static void setUid(String uid) {
        if (TextUtils.isEmpty(uid)) {
            spUtils.remove(UID);
        } else {
            spUtils.put(UID, uid);
        }
    }

    public static String getAccessToken() {
        String result = spUtils.getString(ACCESS_TOKEN);
        return result == null ? "" : result;
    }

    public static String getRefreshToken() {
        String result = spUtils.getString(REFRESH_TOKEN);
        return result == null ? "" : result;
    }


    public static String getLoginName() {
        return spUtils.getString(USER_NAME);
    }

    public static String getUid() {
        return spUtils.getString(UID);
    }

    public static void saveImei(String imei) {
        spUtils.put(SP_KEY_IMEI, imei);
    }

    public static String getImei() {
        return spUtils.getString(SP_KEY_IMEI, "");
    }

    /**
     * 清除token
     */
    public static void clearToken() {
        setAccessToken("");
        setRefreshToken("");
    }

    /**
     * 我已经登录
     */
    public static boolean iamLoggedIn() {
        return !TextUtils.isEmpty(TokenManager.getAccessToken());
    }


}
