package com.study.system.exception;

/**
 * @Auther  吕伟林
 * @Des 程序自主抛出的异常
 * @Date 2021/6/25 11:57
 */
public class MsgException extends Exception {
    public MsgException(String message) {
        super(message);
    }
}
