package com.epam.jwd.core_final.criteria;

import java.lang.reflect.InvocationTargetException;

/**
 * @type T - type of builder
 * @type S - type of class that built by builder
 * **/
public interface BaseBuilder<T, S> {
    S build() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException;
}
