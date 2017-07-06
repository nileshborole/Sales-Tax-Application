package com.sales.tax.app;

import com.sales.tax.app.entity.ProductCategory;
import com.sales.tax.app.entity.PurchasedProduct;
import com.sales.tax.app.entity.Receipt;
import com.sales.tax.app.entity.Tax;
import com.sales.tax.app.util.AppCacheIdentifier;
import com.sales.tax.cache.CacheManager;
import com.sales.tax.cache.impl.CacheType;
import com.sales.tax.io.util.CommonUtil;

import java.util.List;

/**
 * Created by Nilesh on 05-07-2017.
 */
public class ReceiptGenerator {


    private CacheManager manager;

    public ReceiptGenerator(){
        this.manager = CacheManager.getInstance(CacheType.LOCAL_CACHE);
    }

    public Receipt generateReceipt(List<PurchasedProduct> products) throws Exception {

        if(CommonUtil.isNullOrEmpty(products))
            return null;

        for(PurchasedProduct product : products){
            applyTaxesOnProduct(product);
        }

        return new Receipt(products);
    }

    private PurchasedProduct applyTaxesOnProduct(PurchasedProduct product) throws Exception {

        ProductCategory category = (ProductCategory)manager.get(AppCacheIdentifier.PRODUCT_CATEGORY_CACHE, product.getCategoryId());

        if(category == null)
            throw new Exception("product category not found, product category id : "+ product.getCategoryId());

        List<Tax> taxes = category.getTaxList();

        if(!CommonUtil.isNullOrEmpty(taxes)){
            for(Tax tax : taxes){
                product.applyTax(tax);
            }
        }

        return product;
    }

}
