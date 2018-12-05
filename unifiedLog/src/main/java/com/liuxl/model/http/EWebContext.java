package com.liuxl.model.http;

import com.liuxl.constant.CharacterConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class EWebContext<P extends InputDTO, T> extends Context<P, T> {

    private org.apache.velocity.context.Context context = null;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, List<File>> fileMap = new HashMap();
    private boolean requestInit = true;

    public EWebContext(P param, Result<T> result, HttpServletRequest request, HttpServletResponse response) {
        super(param, result);
        this.request = request;
        this.response = response;
    }

    public String getMainVm() {
        return this.getParam().getMainVm();
    }

    public void setContext(org.apache.velocity.context.Context ctx) {
        if (this.context == null) {
            this.context = ctx;
            if (this.getParam() != null) {
                this.context.put(CharacterConstant.CTX_PATH, this.getParam().get(CharacterConstant.CTX_PATH));
                this.context.put(CharacterConstant.BASE_PATH, this.getParam().get(CharacterConstant.BASE_PATH));
                this.context.put(CharacterConstant.SESSION_ID, this.getParam().get(CharacterConstant.SESSION_ID));
            }
        }

    }

    public void put(String key, Object val) {
        if (this.requestInit && !(val instanceof File) && !(val instanceof Map)) {
            this.getParam().put(key, val);
        }

        if (this.context != null) {
            this.context.put(key, val);
        }

    }

    public void putFileMap(Map<String, List<File>> fileMap) {
        this.fileMap = fileMap;
    }

    public void initOver() {
        this.requestInit = false;
    }

    public Object get(String key) {
        return this.getParam().get(key);
    }

    public Map<String, List<File>> getFileMap() {
        return this.fileMap;
    }

    public org.apache.velocity.context.Context getContext() {
        return this.context;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }
}
