package com.liuxl.sacffold.domain.core;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/8/30
 */
public interface IResult extends Serializable {

    boolean isSuccess();

    void setSuccess(boolean success);

    String getErrCode();

    void setErrCode(String errCode);

    String getErrMsg();

}
