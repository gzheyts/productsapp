package com.productsapp.domain.service;

import com.productsapp.domain.service.dto.PageDTO;
import com.productsapp.domain.service.dto.ProductDTO;
import com.productsapp.validation.EntityAlreadyExistsException;
import com.productsapp.validation.EntityNotFoundException;
import org.springframework.data.domain.Pageable;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
public interface ProductService {

    /**
     * Save a product.
     *
     * @param productDTO the entity to save
     * @return the persisted entity
     */
    ProductDTO save(ProductDTO productDTO) throws EntityAlreadyExistsException;

    ProductDTO update(ProductDTO productDTO) throws EntityNotFoundException;

    /**
     * Get all the products.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    PageDTO<ProductDTO> findAll(Pageable pageable);

    /**
     * Get the "id" product.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProductDTO findById(Long id) throws EntityNotFoundException;

    /**
     * Delete the "id" product.
     *
     * @param id the id of the entity
     */
    void delete(Long id) throws EntityNotFoundException;
}
