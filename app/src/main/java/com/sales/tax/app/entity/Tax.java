package com.sales.tax.app.entity;

import com.sales.tax.app.registry.Quantity;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tax tax = (Tax) o;

        if (id != null ? !id.equals(tax.id) : tax.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
