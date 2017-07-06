package com.sales.tax.app.entity;

import com.sales.tax.app.registry.Quantity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nilesh on 04-07-2017.
 */
public class PurchasedProduct extends Product {

    private AppliedTaxList appliedTaxes;
    private Quantity price;
    private Quantity quantity;
    private Set<Integer> taxCategorySet;


    public PurchasedProduct(Product product, Quantity price, Quantity quantity) {
        super(product.getId(), product.getName(), product.getCategoryId());
        this.taxCategorySet = new HashSet<Integer>();
        this.appliedTaxes = new AppliedTaxList();
        this.price = price;
        this.quantity = quantity;
    }

    public void addTaxCategoryCode(TaxCategory category){
        if(category == null)
            return;
        this.taxCategorySet.add(category.getCode());
    }

    public boolean isTaxCategoryApplied(TaxCategory category){
        if(category == null)
            return false;

        return taxCategorySet.contains(category.getCode());
    }
    public AppliedTaxList getAppliedTaxes() {
        return appliedTaxes;
    }

    public Quantity getOriginalPrice() {
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

    public Quantity getQuantity(){
        return this.quantity;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PurchasedProduct{");
        sb.append("appliedTaxes=").append(appliedTaxes);
        sb.append(", price=").append(price);
        sb.append(", taxCategorySet=").append(taxCategorySet);
        sb.append('}');
        return sb.toString();
    }
}
