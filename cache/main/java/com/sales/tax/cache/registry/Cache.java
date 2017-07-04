package com.sales.tax.cache.registry;

import java.util.Map;

/**
 * Created by Nilesh on 03-07-2017.
 */
public interface Cache<K, V> {

    public V put(CacheIdentifier identifier,K key, V value);

    public void putAll(CacheIdentifier identifier, Map<K, V> values);

    public V get(CacheIdentifier identifier, K key);

    public Map<K, V> getAll(CacheIdentifier identifier);

    public V remove(CacheIdentifier identifier, K key);

    public Map<K, V> removeAll(CacheIdentifier identifier);

    public boolean clearCache();

}
