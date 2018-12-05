package com.liuxl.utils;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class CheckUtil {

    public static final String devLog = "dev";
    public static final String mainTable = "1";
    public static final String subTable = "2";
    public static final String Android = "Android";
    public static final String iPhone = "iPhone";
    public static final String iPad = "iPad";

    public static boolean isAdmin(String loginName) {
        return "admin".equalsIgnoreCase(loginName);
    }

    public static boolean isIPhone(String userAgent) {
        return userAgent.contains("iPhone");
    }

    public static boolean isPhoneRequest(String userAgent) {
        return isAndroid(userAgent) || isIPhone(userAgent) || isIpad(userAgent);
    }

    public static boolean isAndroid(String userAgent) {
        return userAgent.contains("Android");
    }

    public static boolean isIpad(String userAgent) {
        return userAgent.contains("iPad");
    }

    public static boolean isMultipartFormData(String contentType) {
        return ObjectUtil.isEmpty(contentType) ? false : contentType.contains("multipart/form-data");
    }

}
