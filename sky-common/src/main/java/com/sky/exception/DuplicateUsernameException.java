package com.sky.exception;

/**
 * 用户名重复异常
 */
public class DuplicateUsernameException extends BaseException {
    public DuplicateUsernameException(String message) {
        super(message);
    }
}
