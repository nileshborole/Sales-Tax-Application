package com.sales.tax.app.entity;

import com.sales.tax.app.registry.Unit;

/**
 * Created by Nilesh on 04-07-2017.
 */
public enum AtomicUnit implements Unit {

    PERCENTAGE("Percentage", "%");

    private String name;
    private String abbrv;


    private AtomicUnit(String name, String abbvr){
        this.name = name;
        this.abbrv = abbvr;
    }


    @Override
    public String getAbbrv() {
        return this.abbrv;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
