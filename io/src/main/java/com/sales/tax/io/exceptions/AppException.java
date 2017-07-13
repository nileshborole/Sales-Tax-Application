package com.sales.tax.io.exceptions;

/**
 * Created by Nilesh on 13-07-2017.
 */
public class AppException extends Exception {

    private final ExceptionMessage exceptionMessage;
    private Object[] params;

    public AppException(Throwable t){
        super(t);
        this.exceptionMessage = CommonExceptionMessage.UNKNOWN_EXCEPTION;
    }

    public AppException(ExceptionMessage exceptionMessage){
        super();
        this.exceptionMessage = exceptionMessage;
    }

    public AppException(ExceptionMessage exceptionMessage, Object[] params){
        super();
        this.exceptionMessage = exceptionMessage;
        this.params = params;
    }

    public AppException(ExceptionMessage exceptionMessage, Object[] params, Throwable t){
        super(t);
        this.exceptionMessage = exceptionMessage;
        this.params = params;
    }

    public ExceptionMessage getExceptionMessage(){
        return this.exceptionMessage;
    }

    public Object[] getParams(){
        return this.params;
    }

}
