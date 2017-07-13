package com.sales.tax.test;

import com.sales.tax.app.entity.*;
import com.sales.tax.app.registry.Quantity;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Nilesh on 12-07-2017.
 */
public class TestPurchasedProduct {

    private Product food;
    private Product travelBag;
    private ProductCategory foodCategory;
    private ProductCategory commonCategory;

    private Tax basicTax;
    private Tax importedTax;

    @Before
    public void init(){
        this.basicTax = new Tax("TAX01","Basic Tax", 10, TaxCategoryEnum.BASIC.instance().getCode());
        this.importedTax = new Tax("TAX02","Imported Tax", 5, TaxCategoryEnum.IMPORTED.instance().getCode());
        foodCategory = new ProductCategory("PCAT1", "Food");
        foodCategory.addTax(basicTax);
        foodCategory.addTax(importedTax);

        commonCategory = new ProductCategory("PCAT2", "Common");
        commonCategory.addTax(basicTax);
        commonCategory.addTax(importedTax);

        food = new Product("PDT01", "Food", foodCategory.getId());
        travelBag = new Product("PDT02", "Travel Bag", commonCategory.getId());
    }

    @Test
    public void testAppliedTaxes(){
        PurchasedProduct product = new PurchasedProduct(food, new Quantity(175.68), new Quantity(1));
        product.addTaxCategoryCode(TaxCategoryEnum.IMPORTED.instance());
        product.applyTax(importedTax);

        AppliedTaxList expectedTaxes = new AppliedTaxList();
        expectedTaxes.add(new AppliedTax(importedTax, new Quantity(175.68)));

        AppliedTaxList actualTaxes = product.getAppliedTaxes();

        assert(expectedTaxes.size() == actualTaxes.size());
        assert(expectedTaxes.getTotalTax().doubleValue() == actualTaxes.getTotalTax().doubleValue());

    }

    @Test
    public void testEndPrice(){
        PurchasedProduct product = new PurchasedProduct(travelBag, new Quantity(550.49), new Quantity(1));
        product.addTaxCategoryCode(TaxCategoryEnum.IMPORTED.instance());
        product.addTaxCategoryCode(TaxCategoryEnum.BASIC.instance());
        product.applyTax(importedTax);
        product.applyTax(basicTax);
        System.out.println(product.getEndPrice().doubleValue());
        assert(633.09 == product.getEndPrice().doubleValue());
    }

}
