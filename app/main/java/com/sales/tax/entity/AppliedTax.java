package com.sales.tax.entity;

import com.sales.tax.registry.Quantity;

/**
 * Created by Nilesh on 04-07-2017.
 */
public class AppliedTax extends Tax {

    private static final float ROUND_UP_TO = 0.05f;
    private Quantity taxAmount;

    public AppliedTax(Tax tax) {
        super(tax.getId(), tax.getTitle(), tax.getPercentage().floatValue());
    }

    public Quantity getTaxAmount() {
        return taxAmount;
    }

    public Quantity getTaxAmount(final Quantity price) {
        calculateTaxAmount(price);
        return taxAmount;
    }

    public void calculateTaxAmount(final Quantity price){
        double tax = (price.doubleValue()*this.percentage.floatValue())/100;
        tax = round(tax);
        this.taxAmount = new Quantity(tax, price.getUnit());
    }

    private double round(double num){
        return Math.ceil(num/ROUND_UP_TO)*ROUND_UP_TO;
    }
}
