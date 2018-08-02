package com.productsapp.domain.service.mapper;

import com.productsapp.domain.entities.Category;
import com.productsapp.domain.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
