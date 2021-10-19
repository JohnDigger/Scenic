package com.example.exception;

/**
 * Created by user on 2016/7/5.
 */
public class BackgroundException extends RuntimeException {
    private static final long serialVersionUID = -1013552870386139910L;

    public BackgroundException() {}

    public BackgroundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BackgroundException(String message) {
        super(message);
    }

    public BackgroundException(Throwable cause) {
        super(cause);
    }
}
