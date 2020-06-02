package com.lzc.lol.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 *  md5加密工具类
 */
public class MD5Util {

    public static void main(String[] args) {
//
//        String md2Encode = md5Jdk("EDG");
        String md2Encode = md5Jdk("doinb");
        System.out.println("加密后为： " + md2Encode);
//        System.out.println("加密后长度：" + md2Encode.length());
//        System.out.println(LoggedUserSessionContext.sessionMap);
    }


    public static String converByteToHexString(byte[] bytes) {
        String result = "";
        for(int i = 0; i < bytes.length; i++) {
            int temp = bytes[i] & 0xff;
            String tempHex = Integer.toHexString(temp);
            if(tempHex.length() < 2) {
                result += "0" + tempHex;
            }else
                result += tempHex;
        }

        return result;

    }

    public static String md5Jdk(String message) {
        String temp = "";
        try {
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            byte[] encodeMd5Digest = md5Digest.digest(message.getBytes());
            temp = converByteToHexString(encodeMd5Digest);
        }catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return temp;
    }



}