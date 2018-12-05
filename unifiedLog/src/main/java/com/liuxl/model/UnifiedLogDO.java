package com.liuxl.model;

import com.liuxl.advisor.UnifiedLogAdvisor;
import com.liuxl.util.LocalThreadPool;
import com.liuxl.util.UnifiedLogUtil;
import com.liuxl.utils.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description: 统一日志参数模型
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class UnifiedLogDO implements Serializable {
    private static final long serialVersionUID = 3947285582608100464L;

    private String requestTime;
    private String requestId;
    private Long useTime;
    private String header;
    private String interfaceName;
    private String hostIp;
    private String clientIp;
    private String localAppName;
    private Object param;
    private Object result;
    private String exceptionCode;
    private String detail;
    private String exceptionMsg;
    private String requestType = StringUtils.EMPTY;

    /**
     * 异常信息
     */
    private Boolean success = true;
    private String errCode;
    private String errMsg;

    public UnifiedLogDO(String header, String interfaceName, Object param, String requestType) {
        this.requestTime = DateUtil.getCurrentDateForStr();
        this.requestId = LocalThreadPool.getRequestId();
        this.header = header;
        this.interfaceName = interfaceName;
        this.localAppName = UnifiedLogAdvisor.getLocalAppName();
        this.clientIp = LocalThreadPool.getClientIp();
        this.hostIp = UnifiedLogUtil.getHostIp();
        this.param = param;
        this.requestType = requestType;

    }

    public UnifiedLogDO(String header, String interfaceName, Object param) {
        this.requestTime = DateUtil.getCurrentDateForStr();
        this.requestId = LocalThreadPool.getRequestId();
        this.header = header;
        this.interfaceName = interfaceName;
        this.localAppName = UnifiedLogAdvisor.getLocalAppName();
        this.clientIp = LocalThreadPool.getClientIp();
        this.hostIp = UnifiedLogUtil.getHostIp();
        this.param = param;

    }
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    @SuppressWarnings("rawtypes")
    public void setResult(Object result) {
        //如果没有接口或服务调用正常，不返回调用结果，以免造成因为结果集导致日志比较多
        if (result instanceof UnifiedResultDO) {
            this.success = ((UnifiedResultDO) result).isSuccess();
            this.errCode = ((UnifiedResultDO) result).getErrCode();
            this.errMsg = ((UnifiedResultDO) result).getErrMsg();
        }
        this.result = result;
    }

    public void clearParamAndResult() {
        this.param = null;
        this.result = null;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public long getUseTime() {
        return useTime;
    }

    public String getHeader() {
        return header;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getHostIp() {
        return hostIp;
    }

    public String getClientIp() {
        return clientIp;
    }

    public String getLocalAppName() {
        return localAppName;
    }

    public Object getParam() {
        return param;
    }

    public Object getResult() {
        return result;
    }

    public String getDetail() {
        return detail;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}
