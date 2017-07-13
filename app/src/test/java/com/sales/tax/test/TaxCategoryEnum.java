package com.sales.tax.test;

import com.sales.tax.app.entity.TaxCategory;

/**
 * Created by Nilesh on 12-07-2017.
 */
public enum TaxCategoryEnum {

    BASIC(1, "basic"),
    IMPORTED(2, "imported");

    private final TaxCategory category;

    private TaxCategoryEnum(int code, String name){
        this.category = new TaxCategory(code, name);
    }

    public TaxCategory instance(){
        return category;
    }


}
