package com.lee.leibo.utils.code;

import org.apache.commons.codec.binary.Base64;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DataCodec {

    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 加密/解密算法/工作模式/填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";


    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     */
    private static Key toKey(byte[] key) throws Exception {
        //生成密钥  
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密后的数据
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        //还原密钥  
        Key k = toKey(key);
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        //初始化，设置为加密模式  
        cipher.init(Cipher.ENCRYPT_MODE, k);
        //执行操作  
        return cipher.doFinal(data);
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密后的数据
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        //欢迎密钥  
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        //执行操作  
        return cipher.doFinal(data);
    }

    /**
     * dataEncrypt(数据加密)  <br/>
     *
     * @param str
     * @param key
     * @return<br/>
     */
    public static String dataEncrypt(String str, byte[] key) {
        //初始化密钥
        String data = "";
        try {
            //加密数据
            byte[] strArr = encrypt(str.getBytes(), key);
            data = new String(new Base64().encode(strArr));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * dataDecrypt(数据解密)  <br/>
     *
     * @param str
     * @param key
     * @return<br/>
     */
    public static String dataDecrypt(String str, byte[] key) {

        byte[] strArr = null;
        try {
            byte[] data = new Base64().decode(str.getBytes("utf-8"));
            //解密数据  
            strArr = decrypt(data, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null == strArr) {
            return "";
        }
        return new String(strArr);
    }

    public static void main(String[] args) throws Exception {
        String data = dataEncrypt("abcd_8434", "1�=�9��".getBytes("utf-8"));
        System.out.println(data);
        String sdata = dataDecrypt(data, "1�=�9��".getBytes("utf-8"));
        System.out.println(sdata);
    }
}
