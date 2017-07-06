package com.sales.tax.app.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nilesh on 04-07-2017.
 */
public class ProductCategory {

    private String id;
    private String title;
    private List<Tax> taxList;

    public ProductCategory(String id, String title){
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Tax> getTaxList() {
        return taxList;
    }

    public void setTaxList(List<Tax> taxList) {
        this.taxList = taxList;
    }

    public void addTax(Tax tax){
        if(this.taxList == null)
            this.taxList = new ArrayList<>();

        this.taxList.add(tax);
    }

}
