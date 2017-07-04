package com.sales.tax.entity;

/**
 * Created by Nilesh on 04-07-2017.
 */
public class Product {

    private String id;
    protected String name;
    protected String categoryId;

    public Product(String id, String name, String categoryId){
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategoryId() {
        return categoryId;
    }
}
