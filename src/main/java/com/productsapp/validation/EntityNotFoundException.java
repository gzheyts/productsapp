package com.productsapp.validation;

import org.springframework.util.StringUtils;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(Class clazz, Long id) {
        super(String.format("%s was not found with id=%s", StringUtils.capitalize(clazz.getSimpleName()), id));
    }

}
