package com.sales.tax.app.util;

import com.sales.tax.cache.registry.CacheIdentifier;

/**
 * Created by Nilesh on 05-07-2017.
 */
public enum AppCacheIdentifier implements CacheIdentifier {
    TAX_CACHE("tax_cache"),
    PRODUCT_CATEGORY_CACHE("product_category_cache"),
    PRODUCT_CACHE("product_cache"),
    TAX_CATEGORY_CACHE("tax_category_cache");

    private String id;

    private AppCacheIdentifier(String id){
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }
}
