package com.lee.leibo.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.lee.leibo.LeiboApplication;
import com.lee.leibo.constants.KeyConstant;
import com.lee.leibo.net.TokenManager;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * @see #getImei()              获取设备IMEI
 * @see #isPad()                判断是否是PAD
 * @see #getDeviceProduct()     获取设备名称
 * @see #getDeviceModel()       获取设备型号
 */
public class SysInfoUtil {

    @SuppressLint("HardwareIds")
    public static String getImei() {
        if (!TextUtils.isEmpty(TokenManager.getImei())) return TokenManager.getImei();
        Context context = LeiboApplication.getInstance();
        String deviceId;
        if (Build.VERSION.SDK_INT >= 30) {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    KLog.d("deviceId", deviceId);
                    TokenManager.saveImei(deviceId);
                    return deviceId;
                }
            }
            assert mTelephony != null;
            if (mTelephony.getDeviceId() != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    deviceId = mTelephony.getImei();
                } else {
                    deviceId = mTelephony.getDeviceId();
                }
            } else {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }
        KLog.d("deviceId", deviceId);
        TokenManager.saveImei(deviceId);
        return deviceId;
    }


    public static boolean isPad() {
        return (LeiboApplication.getInstance().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static int getDeviceType() {
        return SysInfoUtil.isPad() ? KeyConstant.DEVICE_TYPE_PAD : KeyConstant.DEVICE_TYPE_PHONE;
    }

    /**
     * @return 获取设备名称
     */
    public static String getDeviceProduct() {
        return Build.BRAND;
    }

    /**
     * @return 获取设备型号
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * @return 获取app渠道
     */
    public static String getAppChannel() {
        ApplicationInfo applicationInfo = null;
        String channel = "";
        try {
            applicationInfo = LeiboApplication.getInstance().getPackageManager().getApplicationInfo(LeiboApplication.getInstance().getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null) {
                channel = applicationInfo.metaData.getString("JPUSH_CHANNEL");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channel;
    }


}
