package com.lee.leibo.utils.code;

import android.text.TextUtils;

import com.lee.leibo.utils.SysInfoUtil;

import java.io.UnsupportedEncodingException;

public class DataCodeUtils {
    /**
     * 加密密码类型数据
     *
     * @param password
     * @return code
     */
    public static String desPwdData(String password) {
        // 设备ID
        String deviceId = SysInfoUtil.getImei();
        // 拼接加密字段
        String arg0 = password + "_" + deviceId;//Netroid.get().getSmartDeviceId();
        // 拼接密钥key码
        String key;
        if (TextUtils.isEmpty(deviceId)) {
            key = "";
        } else {
            key = /*Netroid.get().getSmartDeviceId()*/deviceId.substring(0, 1) + "�=�9��";
        }
        // 加密权限码
//        String code = DataCodec.dataEncrypt(arg0, "1�=�9��".getBytes("utf-8"));
        String code = "";
        try {
            code = DataCodec.dataEncrypt(arg0, key.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return code;
    }
}
