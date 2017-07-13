package com.sales.tax.test;

import com.sales.tax.app.SalesTaxApplication;
import com.sales.tax.app.entity.AppliedTax;
import com.sales.tax.app.entity.AppliedTaxList;
import com.sales.tax.app.entity.Tax;
import com.sales.tax.app.registry.CustomUnit;
import com.sales.tax.app.registry.Quantity;
import com.sales.tax.app.registry.Unit;
import com.sales.tax.io.util.CommonUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Nilesh on 12-07-2017.
 */
public class TestAppliedTax {

    private Tax basicTax;
    private Tax importedTax;
    private Unit currency;

    @Before
    public void init(){
        System.setProperty(CommonUtil.appNames, "test");
        basicTax = new Tax("TAX01", "Basic Tax", 10, TaxCategoryEnum.BASIC.instance().getCode());
        importedTax = new Tax("TAX02", "Imported Tax", 5,  TaxCategoryEnum.IMPORTED.instance().getCode());
        currency = new CustomUnit("USD", "$");
    }

    @Test
    public void testCalculateTotalTax(){
        Quantity price = new Quantity(150, currency);
        Quantity expectedTax = new Quantity(15, currency);
        AppliedTax tax = new AppliedTax(basicTax, price);
        System.out.println(tax.getTaxAmount().doubleValue());
        assert(expectedTax.doubleValue() == tax.getTaxAmount().doubleValue() && expectedTax.getUnit().equals(tax.getTaxAmount().getUnit()));
    }

    @Test
    public void testCalculateTotalTaxRoundUp(){
        Quantity price = new Quantity(150.65, currency);
        Quantity expectedTax = new Quantity(15.1, currency);
        AppliedTax tax = new AppliedTax(basicTax, price);
        System.out.println(tax.getTaxAmount().doubleValue());
        assert(expectedTax.doubleValue() == tax.getTaxAmount().doubleValue());
    }

    @Test
    public void testCalculatedTotalTaxNegative(){
        Quantity price = new Quantity(-150, currency);
        Quantity expectedTax = new Quantity(-15, currency);
        AppliedTax tax = new AppliedTax(basicTax, price);
        System.out.println(tax.getTaxAmount().doubleValue());
        assert(expectedTax.doubleValue() == tax.getTaxAmount().doubleValue());
    }

    @Test
    public void testCalculatedTotalTaxForZeroPrice(){
        Quantity price = new Quantity(0, currency);
        Quantity expectedTax = new Quantity(0, currency);
        AppliedTax tax = new AppliedTax(basicTax, price);
        System.out.println(tax.getTaxAmount().doubleValue());
        assert(expectedTax.doubleValue() == tax.getTaxAmount().doubleValue());
    }

    @Test
    public void testAppliedTaxList(){

        AppliedTaxList list = new AppliedTaxList();

        list.add(new AppliedTax(basicTax, new Quantity(14.99, currency)));
        list.add(new AppliedTax(importedTax, new Quantity(14.99, currency)));
        list.add(new AppliedTax(importedTax, new Quantity(0, currency)));
        list.add(new AppliedTax(importedTax, new Quantity(-5.68, currency)));

        Quantity expectedTotalTax = new Quantity(1.95, currency);
        Quantity actualTotalTax = list.getTotalTax();
        System.out.println(actualTotalTax.doubleValue());
        assert (expectedTotalTax.doubleValue() == actualTotalTax.doubleValue() && expectedTotalTax.getUnit().equals(actualTotalTax.getUnit()));
    }

}
