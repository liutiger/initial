package com.liuxl.sacffold.common.enums;

/**
 * Created with IntelliJ IDEA.
 * Description:错误code
 *
 * @author liuxl
 * @date 2018/8/30
 */
public enum CenterErrorCodeEnum {
    ILLGAL_ARGUMENT("ILLGAL_ARGUMENT", "非法参数"),
    IDEMPOTENT_INVOKE("IDEMPOTENT_INVOKE", "幂等调用"),
    USERID_NOT_EXIST("USERID_NOT_EXIST", "操作者信息有误,无法操作！"),
    USER_NOT_EXIST("USER_NOT_EXIST", "您的账号不存在，请联系管理员！"),

    CALL_DAO_ERROR("CALL_DAO_ERROR", "调用DAO层失败");


    private String errMsg;

    private String errCode;

    CenterErrorCodeEnum(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    CenterErrorCodeEnum(String errCode) {
        this.errCode = errCode;
    }

    public static CenterErrorCodeEnum getCenterErrorCodeEnum(String code) {
        for (CenterErrorCodeEnum x : CenterErrorCodeEnum.values()) {
            if (x.getErrCode().equals(code)) {
                return x;
            }
        }
        return null;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}
