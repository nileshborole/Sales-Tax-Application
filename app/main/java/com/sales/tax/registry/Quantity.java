package com.sales.tax.registry;

import java.math.BigDecimal;

/**
 * Created by Nilesh on 04-07-2017.
 */
public class Quantity {

    private BigDecimal number;
    private Unit unit;

    public Quantity(BigDecimal number, Unit unit){
        this.number = number;
        this.unit = unit;
    }

    public Quantity(long number, Unit unit){
        this(new BigDecimal(number), unit);
    }

    public Quantity(double number, Unit unit){
        this(new BigDecimal(number), unit);
    }

    public Quantity(long number){
        this(number, null);
    }

    public Quantity(double number){
        this(number, null);
    }

    public int intValue(){
        return this.number.intValue();
    }

    public long longValue(){
        return this.number.longValue();
    }

    public double doubleValue(){
        return this.number.doubleValue();
    }

    public float floatValue(){
        return this.number.floatValue();
    }

    public String getUnitAbbr(){
        return this.unit != null ? this.unit.getAbbrv() : "";
    }

    public String getUnitName(){
        return this.unit != null? this.unit.getName() : "";
    }

    public Unit getUnit(){
        return this.unit;
    }

}
