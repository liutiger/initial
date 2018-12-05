package com.liuxl.exception;

import com.liuxl.enumType.ErrorCodeEnum;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class CommonException extends RuntimeException {
    private static final long serialVersionUID = -2307251285717611430L;

    private String errorCode;

    private String errorMessage;

    private String detailErrorMessage;


    public CommonException(Throwable e) {
        super(e.getMessage());
        this.errorCode = e.getMessage();
    }


    public CommonException(Throwable e, ErrorCodeEnum errorCodeEnum) {
        super(e.getMessage());
        this.errorMessage=errorCodeEnum.getErrMsg();
        this.errorCode = errorCodeEnum.getErrCode();
    }



    public CommonException(String errorCode, String errorMessage, String detailErrorMessage, Throwable e) {
        super(errorCode,e);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.detailErrorMessage =detailErrorMessage;
    }

    public CommonException(String errorCode, String errorMessage, String detailErrorMessage) {
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.detailErrorMessage =detailErrorMessage;
    }

    public CommonException(String errorCode, String errorMessage, Throwable e) {
        super(errorCode,e);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CommonException(String errorCode, String errorMessage) {
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CommonException(String errorCode, Throwable e) {
        super(errorCode,e);
        this.errorCode = errorCode;
    }

    public CommonException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getDetailErrorMessage() {
        return detailErrorMessage;
    }
}