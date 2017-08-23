package com.sai.framework.exception;


public class ExceptionParser {
    private static final String CODE_UNKNOW = "net_unknow";
    private String code = CODE_UNKNOW;
    private String message;

    public ExceptionParser(Exception e) {
        message = e.getMessage();
    }

    public ExceptionParser(Throwable throwable) {
        message = throwable.getMessage();

    }

    public String getCode() {
        return CODE_UNKNOW;
    }

    public String getMessage() {
        return message;
    }
}
