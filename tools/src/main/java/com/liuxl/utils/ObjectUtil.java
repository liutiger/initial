package com.liuxl.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class ObjectUtil {
    public static boolean isNullObject(Object obj) {
        return obj == null;
    }

    public static boolean isEmpty(Collection collection) {
        return isNullObject(collection) || collection.size() == 0;
    }

    public static boolean isEmpty(Map map) {
        return isNullObject(map) || map.isEmpty();
    }

    public static boolean isEmpty(Object obj) {
        return isNullObject(obj) || obj.equals("");
    }

    public static boolean isEmptyOrEqualsZero(Object obj) {
        return isEmpty(obj) || obj.equals("0");
    }

    public static Integer toInt(Object obj) {
        if (obj == null) {
            return null;
        } else {
            try {
                return Integer.valueOf(toStr(obj));
            } catch (Exception var2) {
                return null;
            }
        }
    }

    public static String toStr(Object obj) {
        return obj == null ? "" : String.valueOf(obj);
    }

    public static String toJsonStr(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static Map<String, Object> requestToMap(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = new HashMap();
        Iterator i$ = parameterMap.keySet().iterator();

        while(true) {
            while(i$.hasNext()) {
                String key = (String)i$.next();
                String[] value = (String[])parameterMap.get(key);
                if (value != null && value.length == 1) {
                    map.put(key, value[0]);
                } else {
                    map.put(key, value);
                }
            }

            return map;
        }
    }



    public static Object getDbMapValue(Map map, String proName) {
        return !isEmpty(map.get(proName)) ? map.get(proName) : map.get(proName.toUpperCase());
    }

    public static Map dicToVal(Map params, String name) {
        String[] nameArray = name.split(",");
        String[] arr$ = nameArray;
        int len$ = nameArray.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String str = arr$[i$];
            if (params.containsKey(str)) {
                params.remove(str);
                params.put(str, params.get(str + "_val"));
            }
        }

        return params;
    }
}
