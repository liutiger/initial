package com.liuxl.enumType;

import java.net.NetworkInterface;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/5
 */
public enum ErrorCodeEnum {

    ILLGAL_ARGUMENT("ILLGAL_ARGUMENT", "非法参数"),

    IDEMPOTENT_INVOKE("IDEMPOTENT_INVOKE", "幂等调用"),

    CALL_DAO_ERROR("CALL_DAO_ERROR", "调用DAI层失败"),

    PARSE_DATE_ERROR("RSE_DATE_ERROR", "转换日期对象出错"),

    NETWORK_INTERFACE_ERROR("NETWORK_INTERFACE_ERROR", "获取局域网ip失败");
    private String errMsg;

    private String errCode;

    ErrorCodeEnum(String errMsg, String errCode) {
        this.errMsg = errMsg;
        this.errCode = errCode;
    }

    ErrorCodeEnum(String errCode) {
        this.errCode = errCode;
    }

    public static ErrorCodeEnum getTbcpErrorCodeEnum(String code) {
        for (ErrorCodeEnum x : ErrorCodeEnum.values()) {
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
