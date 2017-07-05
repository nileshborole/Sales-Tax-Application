package com.sales.tax.entity;

import com.sales.tax.registry.Quantity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nilesh on 04-07-2017.
 */
public class PurchasedProduct extends Product {

    private AppliedTaxList appliedTaxes;
    private Quantity price;
    private Set<Integer> taxCategorySet;


    public PurchasedProduct(String id, String name, String categoryId) {
        super(id, name, categoryId);
        this.taxCategorySet = new HashSet<Integer>();
    }

    public void addTaxCategoryCode(TaxCategory category){
        if(category == null)
            return;
        this.taxCategorySet.add(category.getCode());
    }

    public AppliedTaxList getAppliedTaxes() {
        return appliedTaxes;
    }

    public Quantity getPrice() {
        return price;
    }

    public Quantity getTaxApplied(){
        return appliedTaxes != null ? appliedTaxes.getTotalTax() : null;
    }

    public Quantity getEndPrice(){
        Quantity endPrice = new Quantity(price.doubleValue(), price.getUnit());
        endPrice.add(getTaxApplied());
        return endPrice;
    }

    public void applyTax(Tax tax){
        if(this.taxCategorySet != null && this.taxCategorySet.contains(tax.getTaxCategory())) {
            this.appliedTaxes.add(new AppliedTax(tax, this.price));
        }
    }
}
