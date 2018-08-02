package com.productsapp.domain.service.mapper;

import com.productsapp.domain.service.dto.ProductDTO;
import com.productsapp.domain.entities.Product;

import org.mapstruct.*;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mappings({
            @Mapping(source = "category.id", target = "categoryId"),
            @Mapping(source = "category.name", target = "categoryName")
    })
    ProductDTO toDto(Product product);

    @Mapping(source = "categoryId", target = "category")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
