package com.liuxl.model.http;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/12/5
 */
public abstract class InputDTO implements Serializable {

    private static final long serialVersionUID = -3720934065558938753L;

    abstract void put(String key, Object value);

    abstract Object get(String key);

    abstract String getMainVm();
}
