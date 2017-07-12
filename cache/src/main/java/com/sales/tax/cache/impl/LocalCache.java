package com.sales.tax.cache.impl;

import com.sales.tax.cache.registry.Cache;
import com.sales.tax.cache.registry.CacheIdentifier;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nilesh on 03-07-2017.
 */
class LocalCache implements Cache<Object, Object> {

    private Map<String, Map<Object, Object>> cache;

    LocalCache(){
        this.cache = new HashMap<String, Map<Object, Object>>();
    }

    @Override
    public Object put(CacheIdentifier identifier, Object key, Object value) {

        if(cache.get(identifier.getId()) == null)
            cache.put(identifier.getId(), new HashMap<Object, Object>());

        return cache.get(identifier.getId()).put(key, value);
    }

    @Override
    public void putAll(CacheIdentifier identifier, Map<Object, Object> values) {

        if(cache.get(identifier.getId()) == null)
            cache.put(identifier.getId(), new HashMap<Object, Object>());

        cache.get(identifier.getId()).putAll(values);
    }

    @Override
    public Object get(CacheIdentifier identifier, Object key) {
        return cache.get(identifier.getId()) != null ? cache.get(identifier.getId()).get(key) : null;
    }

    @Override
    public Map<Object, Object> getAll(CacheIdentifier identifier) {
        return cache.get(identifier.getId());
    }

    @Override
    public Object remove(CacheIdentifier identifier, Object key) {
        return cache.get(identifier.getId()) != null ? cache.get(identifier.getId()).remove(key) : null;
    }

    @Override
    public Map<Object, Object> removeAll(CacheIdentifier identifier) {
        return cache.remove(identifier.getId());
    }

    @Override
    public boolean clearCache() {
        cache.clear();
        return true;
    }
}
