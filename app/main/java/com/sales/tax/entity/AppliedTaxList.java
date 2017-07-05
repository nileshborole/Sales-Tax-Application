package com.sales.tax.entity;

import com.sales.tax.registry.Quantity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Nilesh on 04-07-2017.
 */
public class AppliedTaxList extends ArrayList<AppliedTax> {

    public AppliedTaxList(List<AppliedTax> taxList){
        super(taxList);
    }

    public Quantity getTotalTax() {
        return calculateTotalTax();
    }

    private Quantity calculateTotalTax(){

        Iterator<AppliedTax> iterator = this.iterator();
        Quantity totalTax = null;
        while(iterator.hasNext()){
            AppliedTax tax = iterator.next();
            if(totalTax == null)
                totalTax = new Quantity(0, tax.getTaxAmount().getUnit());

            totalTax.add(tax.getTaxAmount());
        }
        return totalTax;
    }

}
