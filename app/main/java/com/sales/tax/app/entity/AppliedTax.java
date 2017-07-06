package com.sales.tax.app.entity;

import com.sales.tax.app.registry.Quantity;

/**
 * Created by Nilesh on 04-07-2017.
 */
public class AppliedTax extends Tax {

    private static final float ROUND_UP_TO = 0.05f;
    private Quantity taxAmount;

    public AppliedTax(final Tax tax, final Quantity price) {
        super(tax.getId(), tax.getTitle(), tax.getPercentage().floatValue(), tax.getTaxCategory());
        calculateTaxAmount(price);
    }

    public Quantity getTaxAmount() {
        return taxAmount;
    }

    private void calculateTaxAmount(final Quantity price){
        this.taxAmount = new Quantity(price.doubleValue(), price.getUnit());
        taxAmount.multiply(percentage);
        taxAmount.divide(100);
        this.taxAmount.roundUp(ROUND_UP_TO);
    }


}
