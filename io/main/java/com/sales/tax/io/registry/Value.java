package com.sales.tax.io.registry;

/**
 * Created by Nilesh on 02-07-2017.
 */
public class Value {

    private String key;
    private Object value;

    public Value(String key, Object value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}
