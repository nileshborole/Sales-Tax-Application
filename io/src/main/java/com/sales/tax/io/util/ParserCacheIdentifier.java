package com.sales.tax.io.util;

import com.sales.tax.cache.registry.CacheIdentifier;

/**
 * Created by Nilesh on 03-07-2017.
 */
enum ParserCacheIdentifier implements CacheIdentifier {

    INPUT_PARSER_CACHE("input_parsers_instances");

    private String id;

    private ParserCacheIdentifier(String id){
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }
}
