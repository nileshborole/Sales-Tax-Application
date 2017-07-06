package com.sales.tax.app.registry;

/**
 * Created by Nilesh on 04-07-2017.
 */
public class CustomUnit implements Unit {

    private String abbrv;
    private String name;

    public CustomUnit(String abbrv, String name){
        this.abbrv = abbrv;
        this.name = name;
    }

    public CustomUnit(String name){
        this.name = name;
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
