package com.sales.tax.io.exceptions;

/**
 * Created by Nilesh on 12-07-2017.
 */
public class CriteriaEvaluationException extends RuntimeException {

    private final ExceptionMessage exceptionMessage;

    public CriteriaEvaluationException(){
        super();
        this.exceptionMessage = CommonExceptionMessage.UNKNOWN_EXCEPTION;
    }

    public CriteriaEvaluationException(String message){
        super(message);
        this.exceptionMessage = CommonExceptionMessage.UNKNOWN_EXCEPTION;
    }

    public CriteriaEvaluationException(Throwable e){
        super(e);
        this.exceptionMessage = CommonExceptionMessage.UNKNOWN_EXCEPTION;
    }

    public CriteriaEvaluationException(String message, Throwable e){
        super(message, e);
        this.exceptionMessage = CommonExceptionMessage.UNKNOWN_EXCEPTION;
    }

    public CriteriaEvaluationException(ExceptionMessage exceptionMessage){
        super();
        this.exceptionMessage = exceptionMessage;
    }

    public CriteriaEvaluationException(ExceptionMessage exceptionMessage, Throwable e){
        super(e);
        this.exceptionMessage = exceptionMessage;
    }

    public CriteriaEvaluationException(ExceptionMessage exceptionMessage, String message){
        super(message);
        this.exceptionMessage = exceptionMessage;
    }

    public CriteriaEvaluationException(ExceptionMessage exceptionMessage, String message, Throwable e){
        super(message, e);
        this.exceptionMessage = exceptionMessage;
    }

    public ExceptionMessage getExceptionMessage(){
        return this.exceptionMessage;
    }

}
