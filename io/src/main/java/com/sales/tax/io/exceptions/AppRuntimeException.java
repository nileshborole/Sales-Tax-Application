package com.sales.tax.io.exceptions;

/**
 * Created by Nilesh on 13-07-2017.
 */
public class AppRuntimeException extends RuntimeException{

    private final ExceptionMessage exceptionMessage;
    private Object[] params;

    public AppRuntimeException() {
        super();
        this.exceptionMessage = CommonExceptionMessage.UNKNOWN_EXCEPTION;
    }

    public AppRuntimeException(ExceptionMessage exceptionMessage){
        super();
        this.exceptionMessage = exceptionMessage;
    }

    public AppRuntimeException(ExceptionMessage exceptionMessage, String message){
        super(message);
        this.exceptionMessage = exceptionMessage;
    }

    public AppRuntimeException(ExceptionMessage exceptionMessage, Object[] params){
        super();
        this.exceptionMessage = exceptionMessage;
        this.params = params;
    }

    public AppRuntimeException(ExceptionMessage exceptionMessage, Throwable t){
        super(t);
        this.exceptionMessage = exceptionMessage;
    }

    public AppRuntimeException(ExceptionMessage exceptionMessage, Object[] params, Throwable t){
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
