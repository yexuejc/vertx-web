package com.yexuejc.vertx.web.base.util;

/**
 * @PackageName: com.yexuejc.vertx.web.base.util
 * @Description:
 * @author: maxf
 * @date: 2018/4/8 14:21
 */
public class StringUtils {
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }


    /**
     * url中出现 // 替换为 /
     *
     * @param url
     * @return
     */
    public static String disposeUrl(String url) {
        return url.replaceAll("//", "/");
    }
}
