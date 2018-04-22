package com.xxx.homework.expection;

/**
 * @author sh
 */
public class DBException extends RuntimeException {

    private final int code;

    public DBException(int code, String message) {
        super(message);
        this.code = code;
    }

    public DBException(int code) {
        this(code, null);
    }
}
