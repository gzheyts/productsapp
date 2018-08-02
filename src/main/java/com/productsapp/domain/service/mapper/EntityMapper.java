package com.productsapp.domain.service.mapper;

import java.util.List;
/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
public interface EntityMapper <D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    List <E> toEntity(List<D> dtoList);

    List <D> toDto(List<E> entityList);
}
