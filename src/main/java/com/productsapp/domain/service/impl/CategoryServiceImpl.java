package com.productsapp.domain.service.impl;

import com.productsapp.domain.entities.Category;
import com.productsapp.domain.repository.CategoryRepository;
import com.productsapp.domain.service.CategoryService;
import com.productsapp.domain.service.dto.CategoryDTO;
import com.productsapp.domain.service.dto.PageDTO;
import com.productsapp.domain.service.mapper.CategoryMapper;
import com.productsapp.validation.EntityAlreadyExistsException;
import com.productsapp.validation.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) throws EntityAlreadyExistsException {
        log.debug("Request to save Category : {}", categoryDTO);
        if (categoryDTO.getId() != null && categoryRepository.existsById(categoryDTO.getId())) {
            throw new EntityAlreadyExistsException(Category.class, categoryDTO.getId());
        }
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) throws EntityNotFoundException {
        log.debug("Request to save Category : {}", categoryDTO);
        if (categoryDTO.getId() == null || !categoryRepository.existsById(categoryDTO.getId())) {
            throw new EntityNotFoundException(Category.class, categoryDTO.getId());
        }
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public PageDTO<CategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return new PageDTO<>(categoryRepository.findAll(pageable), categoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public PageDTO<CategoryDTO> searchByName(String name, Pageable pageable) {
        log.debug("Request to search Categories");
        return new PageDTO<>(categoryRepository.findAllByNameContainsIgnoreCase(name, pageable), categoryMapper::toDto);
    }
    /**
     * Get one category by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) throws EntityNotFoundException {
        log.debug("Request to get Category : {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Category.class, id));
        return categoryMapper.toDto(category);
    }

    /**
     * Delete the category by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) throws EntityNotFoundException {
        log.debug("Request to delete Category : {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Category.class, id));
        categoryRepository.delete(category);
    }
}
