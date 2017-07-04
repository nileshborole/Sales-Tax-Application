package com.sales.tax.cache.impl;

import com.sales.tax.cache.registry.Cache;

/**
 * Created by Nilesh on 03-07-2017.
 */
public enum CacheType {

    LOCAL_CACHE(){
        @Override
        void initialize() {
            this.cache = new LocalCache();
            super.initialize();
        }
    }, REMOTE_CACHE;

    protected Cache cache;

    private CacheType(){
        initialize();
    }

    void initialize(){

    }

    public Cache getInstance(){

        return this.cache;
    }

}
