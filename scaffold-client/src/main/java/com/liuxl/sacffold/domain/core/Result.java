package com.liuxl.sacffold.domain.core;


import com.liuxl.sacffold.common.enums.CenterErrorCodeEnum;
import com.liuxl.sacffold.common.util.StringUtil;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/8/30
 */
public class Result<T> implements IResult {


    private static final long serialVersionUID = 84536450600166657L;
    private boolean success = true;

    private String errCode;

    private String errMsg;

    private String errDetail;

    private T module;

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    @Override
    public String getErrMsg() {
        if (success || errCode == null) {
            return null;
        }

        if (StringUtil.isNotBlank(errMsg)) {
            return errMsg;
        }
        CenterErrorCodeEnum codeEnum = CenterErrorCodeEnum.getCenterErrorCodeEnum(errCode);
        return codeEnum != null ? codeEnum.getErrMsg() : null;
    }

    @Override
    public String getErrCode() {
        return errCode;
    }

    public T getModule() {
        return module;
    }

    public void setModule(T module) {
        this.module = module;
    }

    @Override
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrDetail() {
        return errDetail;
    }

    public void setErrDetail(String errDetail) {
        this.errDetail = errDetail;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static Result of() {
        return new Result();
    }

}
