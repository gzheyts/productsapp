package com.productsapp.domain.service;

import com.productsapp.domain.service.dto.CategoryDTO;
import com.productsapp.domain.service.dto.PageDTO;
import com.productsapp.validation.EntityNotFoundException;
import com.productsapp.validation.EntityAlreadyExistsException;
import org.springframework.data.domain.Pageable;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
public interface CategoryService {

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save
     * @return the persisted entity
     */
    CategoryDTO save(CategoryDTO categoryDTO) throws EntityAlreadyExistsException;

    CategoryDTO update(CategoryDTO categoryDTO) throws EntityNotFoundException;

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    PageDTO<CategoryDTO> findAll(Pageable pageable);

    PageDTO<CategoryDTO> searchByName(String name, Pageable pageable);

    /**
     * Get the "id" category.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CategoryDTO findById(Long id) throws EntityNotFoundException;

    /**
     * Delete the "id" category.
     *
     * @param id the id of the entity
     */
    void delete(Long id) throws EntityNotFoundException;
}
