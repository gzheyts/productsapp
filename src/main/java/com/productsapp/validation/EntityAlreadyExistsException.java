package com.productsapp.validation;

import org.springframework.util.StringUtils;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
public final class EntityAlreadyExistsException extends Exception {

    public EntityAlreadyExistsException(Class clazz, Long id) {
        super(String.format("%s already exists with id=%s", StringUtils.capitalize(clazz.getSimpleName()), id));
    }
}
