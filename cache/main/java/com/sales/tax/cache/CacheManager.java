package com.sales.tax.cache;

import com.sales.tax.cache.impl.CacheType;
import com.sales.tax.cache.registry.Cache;
import com.sales.tax.cache.registry.CacheIdentifier;

import java.util.Map;

/**
 * Created by Nilesh on 03-07-2017.
 */
public class CacheManager {

    private Cache cache;

    public static CacheManager getInstance(CacheType type){
        return new CacheManager(type);
    }

    private CacheManager(CacheType type){
        this.cache = CacheFactory.getInstance(type);
    }

    public Object put(CacheIdentifier identifier, Object key, Object value){
        return this.cache.put(identifier, key, value);
    }

    public void putAll(CacheIdentifier identifier, Map<Object, Object> values){
        this.cache.putAll(identifier, values);
    }

    public Object get(CacheIdentifier identifier, Object key){
        return this.cache.get(identifier, key);
    }

    public Map<Object, Object> getAll(CacheIdentifier identifier){
        return this.cache.getAll(identifier);
    }

    public Object remove(CacheIdentifier identifier, Object key){
        return this.cache.remove(identifier, key);
    }

    public Map<Object, Object> removeAll(CacheIdentifier identifier){
        return this.cache.removeAll(identifier);
    }

    public void clearCache(){
        this.cache.clearCache();
    }

}
