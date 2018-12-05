package com.liuxl.util;

import com.liuxl.enumType.RequsetTypeEnum;
import com.liuxl.utils.StringUtil;
import com.liuxl.utils.UUIDUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:线程池
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class LocalThreadPool {

    private final static String CLIENT_IP = "clientIp";
    private final static String REQUEST_ID = "requestId";
    private final static String REQUEST_TYPE = "requestType";

    final static java.lang.ThreadLocal<Map<String, Object>> local = new java.lang.ThreadLocal<>();

    public static String getClientIp() {
        if (local.get() == null) {
            setClientIp("");
        }
        return (String) local.get().get(CLIENT_IP);
    }

    public static String getRequestId() {

        if (local.get() == null || local.get().get(REQUEST_ID) == null) {
            setRequestId(UUIDUtil.getUUID(true));
        }

        return (String) local.get().get(REQUEST_ID);
    }

    public static void setClientIp(String clientIp) {
        Map<String, Object> map = local.get();
        if (map == null) {
            map = new HashMap<>(1);
        }
        map.put(CLIENT_IP, clientIp);
        local.set(map);
    }

    public static void setClientIpWhenIsNull(String clientIp) {
        Map<String, Object> map = local.get();
        if(StringUtil.isBlank(clientIp)){
            clientIp=UnifiedLogUtil.getHostIp();
        }
        if (map == null || map.get(CLIENT_IP) == null) {
            setClientIp(clientIp);
        }
    }

    public static void setRequestIdWhenIsNull(String requestId) {
        Map<String, Object> map = local.get();

        if(StringUtil.isBlank(requestId)){
            requestId=UUIDUtil.getUUID(true);
        }
        if (map == null || map.get(REQUEST_ID) == null) {
            setRequestId(requestId);
        }
    }

    public static String getRequestType() {
        if (local.get() == null || local.get().get(REQUEST_TYPE) == null) {
            setRequestType(UUIDUtil.getUUID(true));
        }

        return (String) local.get().get(REQUEST_TYPE);
    }

    public static void setRequestType(String requestType) {
        Map<String, Object> map = local.get();
        if (map == null) {
            map = new HashMap<>(1);
        }
        map.put(REQUEST_TYPE, requestType);
        local.set(map);
    }

    public static void setRequesTypeWhenIsNull(String requestType) {
        Map<String, Object> map = local.get();
        if (map == null || map.get(REQUEST_TYPE) == null) {
            setRequestType(requestType);
        }
    }

    public static void setRequestId(String requestId) {
        if (StringUtil.isBlank(requestId)) {
            return;
        }
        Map<String, Object> map = local.get();
        if (map == null) {
            map = new HashMap<>(1);
        }
        map.put(REQUEST_ID, requestId);
        local.set(map);
    }
    public static void remove() {
        if (local != null && !getRequestType().equals(RequsetTypeEnum.HTTP.getType())) {
            local.set(null);
            local.remove();
        }
    }
}
