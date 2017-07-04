package com.sales.tax.io.registry;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Nilesh on 02-07-2017.
 */
public interface Criteria<T> extends Predicate<T>, Function<T, Value> {

    @Override
    Value apply(T t);

    boolean isStrict();
}
