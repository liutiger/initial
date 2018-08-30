package com.liuxl.sacffold.domain.query;


import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/8/30
 */
public abstract class BaseQuery implements Serializable {

    private static final long serialVersionUID = -7651964195999137918L;

    public BaseQuery() {

    }

    public abstract void validation();
}
