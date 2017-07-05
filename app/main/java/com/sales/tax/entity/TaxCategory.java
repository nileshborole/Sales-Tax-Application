package com.sales.tax.entity;

/**
 * Created by Nilesh on 05-07-2017.
 */
public class TaxCategory {

    private int code;
    private String category;

    public TaxCategory(int code, String category){
        this.code = code;
        this.category = category;
    }

    public int getCode() {
        return code;
    }

    public String getCategory() {
        return category;
    }
}
