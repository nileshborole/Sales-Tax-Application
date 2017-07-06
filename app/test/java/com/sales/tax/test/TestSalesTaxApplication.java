package com.sales.tax.test;

import com.sales.tax.app.SalesTaxApplication;
import com.sales.tax.io.util.CommonUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nilesh on 06-07-2017.
 */
public class TestSalesTaxApplication {

    private List<String> inputStrings;
    private SalesTaxApplication app;
    @Before
    public void init() throws Exception{
        System.setProperty(CommonUtil.appNames, "test");
        app = new SalesTaxApplication("test");
    }

    @Test
    public void testPrintReceipt() throws Exception{

        inputStrings = new ArrayList<String>();

        /*inputStrings.add("1 book at 12.49");
        inputStrings.add("1 music CD at 14.99");
        inputStrings.add("1 chocolate bar at 0.85");*/
        inputStrings.add("1 box of imported chocolates at 11.25");
        inputStrings.add("1 imported bottle of perfume at 47.50");
        inputStrings.add("1 chocolate bar at 0.85");
        inputStrings.add("1 music CD at 14.99");
        StringBuilder result = app.printReceipt(inputStrings);

        StringBuilder expected = new StringBuilder("1 imported box of chocolates: 11.85\n")
                .append("1 imported bottle of perfume: 54.65\n")
                .append("1 chocolate bar: 0.85\n")
                .append("1 music cd: 16.49\n")
                .append("Sales Taxes: 9.25\n")
                .append("Total: 83.84");

        assert(expected.toString().equals(result.toString()));

    }

}
