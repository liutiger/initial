package com.liuxl.model.http;

import com.liuxl.utils.CheckUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class EWebServletContext {

    static ThreadLocal<EWebContext> local = new ThreadLocal();


    public static void setEWebContext(EWebContext context) {
        local.set(context);
    }

    public static HttpServletResponse getResponse() {
        return ((EWebContext) local.get()).getResponse();
    }

    public static EWebContext getEWebContext() {
        return (EWebContext) local.get();
    }

    public static HttpServletRequest getRequest() {
        return ((EWebContext) local.get()).getRequest();
    }

    public static boolean isPc() {
        return !CheckUtil.isPhoneRequest(getRequest().getHeader("user-agent"));
    }

    public static String getTerminal() {
        return "";
    }

    public static void destory() {
        local.remove();
    }
}
