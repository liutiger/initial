package com.liuxl.model;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class UnifiedResultDO<T> implements Serializable {
    private static final long serialVersionUID = 7634654039342996251L;

    private Boolean success;

    private String errCode;

    private String errMsg;

    private T module;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getModule() {
        return module;
    }

    public void setModule(T module) {
        this.module = module;
    }
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
