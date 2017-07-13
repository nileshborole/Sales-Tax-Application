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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomUnit that = (CustomUnit) o;

        if (abbrv != null ? !abbrv.equals(that.abbrv) : that.abbrv != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }




}
