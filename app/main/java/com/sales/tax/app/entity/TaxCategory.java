package com.sales.tax.app.entity;

/**
 * Created by Nilesh on 05-07-2017.
 */
public class TaxCategory {

    private int code;
    private String name;

    public TaxCategory(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
