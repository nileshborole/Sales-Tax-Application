package com.sales.tax.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nilesh on 04-07-2017.
 */
public class AppliedTaxList extends ArrayList<AppliedTax> {



    public AppliedTaxList(){
        super();
    }

    public AppliedTaxList(List<AppliedTax> taxList){
        super(taxList);
    }



}
