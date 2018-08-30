package com.liuxl.scaffold.common.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:mybaits 错误报错信息
 *
 * @author liuxl
 * @date 2018/8/30
 */
public class DatabaseException extends RuntimeException {
    
    public DatabaseException() {
        super();
    }

    public DatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }
}
