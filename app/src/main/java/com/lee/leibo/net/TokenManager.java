package com.lee.leibo.net;

import android.text.TextUtils;

import me.goldze.mvvmhabit.utils.SPUtils;

public class TokenManager {

    private static SPUtils spUtils = SPUtils.getInstance("refresh_token");

    public static final String TOKEN = "af";
    public static final String LOGIN_TOKEN = "login_af";
    public static final String loginPhone = "a";
    public static final String LgName = "login_username";
    public static final String userRealName = "user_real_name";
    public static final String UserId = "userId";
    public static final String UserHeadImageUrl = "user_head_imageurl";
    public static final String UseCode = "useCode";
    public static final String Vin = "vin";
    public static final String CarType = "carType";
    public static final String RemoteControl = "remoteControl"; //保存手势密码
    public static final String CheckStatus = "checkStatus"; //保存绑车状态
    public static final String SaveGestureStatus = "saveGestureStatus"; //保存手势密码的状态为有/无
    public static final String SaveGestureUnix = "saveGestureUnix"; //保存远程控制时间戳（远程控制时使用）
    public static final String SP_KEY_USER_AGREE = "sp_key_user_agree"; //用户是否同意用户协议
    public static final String SP_KEY_IMEI = "sp_key_imei"; //IMEI

    public static void setToken(String token) {
        if (TextUtils.isEmpty(token)) {
            spUtils.remove(TOKEN);
        } else {
            spUtils.put(TOKEN, token);
        }

    }

    public static void setLoginToken(String token) {
        if (TextUtils.isEmpty(token)) {
            spUtils.remove(LOGIN_TOKEN);
        } else {
            spUtils.put(LOGIN_TOKEN, token);
        }
    }

    public static String getToken() {
        String result = spUtils.getString(TOKEN);
        return result == null ? "" : result;
    }

    public static String getLoginToken() {
        String result = spUtils.getString(LOGIN_TOKEN);
        return result == null ? "" : result;
    }

    public static void setLoginPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            spUtils.remove(loginPhone);
        } else {
            spUtils.put(loginPhone, phone);
        }
    }

    public static String getLoginPhone() {
        return spUtils.getString(loginPhone);
    }


    public static String getLoginName() {
        return spUtils.getString(LgName);
    }

    public static void setLoginName(String name) {
        spUtils.put(LgName, name);
    }

    public static String getUserRealName() {
        return spUtils.getString(userRealName);
    }

    public static void setUserRealName(String name) {
        spUtils.put(userRealName, name);
    }

    public static void saveUserId(String userId) {
        spUtils.put(UserId, TextUtils.isEmpty(userId) ? "" : userId);
    }

    public static String getUserId() {
        return spUtils.getString(UserId);
    }

    public static void saveUserHeadImageUrl(String userHeadImageUrl) {
        spUtils.put(UserHeadImageUrl, TextUtils.isEmpty(userHeadImageUrl) ? "" : userHeadImageUrl);
    }

    public static String getUserHeadImageUrl() {
        return spUtils.getString(UserHeadImageUrl);
    }

    /**
     * 保存执行码（远程控制时使用）
     */
    public static void saveUseCode(String code) {
        spUtils.put(UseCode, code);
        saveGestureUnix(System.currentTimeMillis());
    }

    public static String getUseCode() {
        return spUtils.getString(UseCode);
    }

    /**
     * 保存车架号
     */
    public static void saveVinCode(String vinCode) {
        spUtils.put(Vin, TextUtils.isEmpty(vinCode) ? "" : vinCode);
    }

    public static String getVinCode() {
        return spUtils.getString(Vin);
    }

    public static void setCartype(String cartype) {
        spUtils.put(CarType, cartype);
    }

    public static String getCarType() {
        return spUtils.getString(CarType);
    }

    /**
     * 保存手势密码
     */
    public static void saveRemoteControl(String code) {
        spUtils.put(RemoteControl, code);
    }

    public static String getRemoteControl() {
        return spUtils.getString(RemoteControl);
    }

    /**
     * 保存绑车状态
     */
    public static void setCheckStatus(String checkStatus) {
        if (checkStatus != null) {
            spUtils.put(CheckStatus, checkStatus);
        }
    }

    public static String getCheckStatus() {
        return spUtils.getString(CheckStatus);
    }

    /**
     * 保存手势密码的状态为有/无
     */
    public static void saveGesture(boolean status) {
        spUtils.put(SaveGestureStatus, status);
    }

    /**
     * 判断手势密码的状态
     */
    public static boolean hasGesture() {
        return spUtils.getBoolean(SaveGestureStatus, false);
    }

    /**
     * 保存远程控制时间戳（远程控制时使用）
     */
    public static void saveGestureUnix(long timeUnix) {
        spUtils.put(SaveGestureUnix, timeUnix);
    }

    /**
     * 获取远程控制时间戳（远程控制时使用）
     */
    public static long getGestureUnix() {
        return spUtils.getLong(SaveGestureUnix, 0);
    }

    /**
     * 保存用户是否同意用户协议
     */
    public static void saveUserAgree() {
        spUtils.put(SP_KEY_USER_AGREE, true);
    }

    public static boolean getUserAgree() {
        return spUtils.getBoolean(SP_KEY_USER_AGREE, false);
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
        setToken("");
        setLoginToken("");
        saveGesture(false);
        saveGestureUnix(0);
        saveVinCode("");
        saveUserId("");
//        setLoginPhone("");
//        setLoginName("");
    }

    /**
     * 我已经登录
     */
    public static boolean iamLoggedIn() {
        return !TextUtils.isEmpty(TokenManager.getToken());
    }


    /**
     * 比较时间
     */
    public static boolean compareTimesToSystemTime() {
        long res = System.currentTimeMillis() - getGestureUnix();
        return res > 30 * 60 * 1000;
    }

}
