package com.sales.tax.entity;

import com.sales.tax.io.util.CommonUtil;
import com.sales.tax.registry.Quantity;

/**
 * Created by Nilesh on 04-07-2017.
 */
public class Tax {

    protected String id;
    protected String title;
    protected Quantity percentage;
    private int taxCategoryCode;


    public Tax(String id, String title, float percentage, int taxCategoryCode){

        this.id = id;
        this.title = title;
        this.percentage = new Quantity(percentage, AtomicUnit.PERCENTAGE);
        this.taxCategoryCode = taxCategoryCode;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Quantity getPercentage() {
        return percentage;
    }

    public int getTaxCategory(){
        return this.taxCategoryCode;
    }

}
