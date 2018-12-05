package com.liuxl.model.http;

import org.apache.poi.ss.formula.functions.T;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class Context<P extends InputDTO, T> {
    private P param;
    private Result<T> result;

    public Context(P param, Result<T> result) {
        this.param = param;
        this.result = result;
    }

    public P getParam() {
        return this.param;
    }

    public Result<T> getResult() {
        return this.result;
    }

    public void setResult(Result<T> result) {
        this.result = result;
    }

    public void setResultSuccess(boolean success) {
        this.result.setSuccess(success);
    }

    public void setResultErrCode(String errCode) {
        this.result.setErrorCode(errCode);
    }

    public void setResultModule(T t) {
        this.result.setModule(t);
    }
}
