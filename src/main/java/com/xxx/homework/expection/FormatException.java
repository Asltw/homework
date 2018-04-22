package com.xxx.homework.expection;

/**
 * @author sh
 */
public class FormatException extends RuntimeException {
    private final int code;

    public FormatException(int code, String message) {
        super(message);
        this.code = code;
    }

    public FormatException(int code) {
        this(code, null);
    }
}
