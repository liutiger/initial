package com.liuxl.model;

import com.liuxl.util.LocalThreadPool;
import com.liuxl.util.UnifiedLogUtil;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description: 基础模型
 *
 * @author liuxl
 * @date 2018/12/5
 */
public abstract class UnifiedBaseDO implements Serializable {
    private static final long serialVersionUID = 7958696849663421634L;
    /**
     * 唯一请求ID 全链路
     */
    private String requestUnifiedId;
    /**
     * 客户端请求的IP
     */
    private String clientIp;


    public String getRequestUnifiedId() {
        return requestUnifiedId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void enterUnifiedLog() {
        requestUnifiedId = LocalThreadPool.getRequestId();
        clientIp = UnifiedLogUtil.getHostIp();
    }
}
