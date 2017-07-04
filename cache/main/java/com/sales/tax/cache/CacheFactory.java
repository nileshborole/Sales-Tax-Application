package com.sales.tax.cache;

import com.sales.tax.cache.impl.CacheType;
import com.sales.tax.cache.registry.Cache;

/**
 * Created by Nilesh on 03-07-2017.
 */
class CacheFactory {

    static Cache getInstance(CacheType type){
        return CacheType.LOCAL_CACHE.getInstance();
    }

}
