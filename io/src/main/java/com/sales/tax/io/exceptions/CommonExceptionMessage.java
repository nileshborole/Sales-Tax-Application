package com.sales.tax.io.exceptions;

/**
 * Created by Nilesh on 12-07-2017.
 */
public enum CommonExceptionMessage implements ExceptionMessage {
    REGEX_NOT_MATCHED("invalid_input"),
    UNKNOWN_EXCEPTION("unknown_exception"),
    INPUT_PARSE_ERROR("input_parsing_error");



    private String id;
    private CommonExceptionMessage(String id){
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }
}
