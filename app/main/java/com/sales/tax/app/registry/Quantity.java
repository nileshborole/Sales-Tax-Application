package com.sales.tax.app.registry;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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

    public void add(long add){
        add(new BigDecimal(add));
    }

    public void add(double add){
        add(new BigDecimal(add));
    }

    public void add(BigDecimal add){
        this.number = this.number.add(add);
    }

    public void add(Quantity quantity){
        if(quantity != null)
            add(quantity.number);
    }

    public void multiply(long multi){
        multiply(new BigDecimal(multi));
    }

    public void multiply(double multi){
        multiply(new BigDecimal(multi));
    }

    public void multiply(BigDecimal multi){
        this.number = this.number.multiply(multi);
    }

    public void multiply(Quantity quantity){
        if(quantity != null)
            multiply(quantity.number);
    }

    public void divide(long divide){
        divide(new BigDecimal(divide));
    }

    public void divide(double divide){
        divide(new BigDecimal(divide));
    }


    public void divide(BigDecimal divide){
        this.number = this.number.divide(divide);
    }

    public void divide(Quantity quantity){
        if(quantity != null)
            divide(quantity.number);
    }

    public Quantity roundUp(double roundUpTo){
        BigDecimal roundUp = BigDecimal.valueOf(roundUpTo).round(new MathContext(2));
        this.number = this.number.divide(roundUp).setScale(0, RoundingMode.UP).multiply(roundUp);
        return this;
    }

}
