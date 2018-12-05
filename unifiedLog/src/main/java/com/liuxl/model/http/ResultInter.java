package com.liuxl.model.http;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/5
 */
public interface ResultInter<T> {

    void setErrorCode(String errorCode);

    String getErrorCode();

    void setErrorMsg(String errorMsg);

    String getErrorMsg();

    T getModule();

    void setModule(T module);

    void setSuccess(Boolean success);

    Boolean isSuccess();
}
