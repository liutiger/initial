package com.liuxl.sacffold.domain.dto;


/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/8/30
 */
public abstract class BaseReqDTO extends BaseDTO {

    private static final long serialVersionUID = 4024284488552429906L;

    /**
     * Validation.
     */
    public abstract void validation();
}
