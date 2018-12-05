package com.liuxl.model.http;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class Result<T> implements ResultInter<T> {

    private Boolean success = true;
    private String errorMsg;
    private String errorCode;
    private T module;

    public Result() {
    }

    @Override
    public Boolean isSuccess() {
        return this.success;
    }

    @Override
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public T getModule() {
        return this.module;
    }

    @Override
    public void setModule(T t) {
        this.module = t;
    }

    @Override
    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
