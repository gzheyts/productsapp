package com.productsapp.domain.service.impl;

import com.productsapp.domain.service.dto.ProductDTO;
import com.productsapp.domain.entities.Product;
import com.productsapp.domain.repository.ProductRepository;
import com.productsapp.domain.service.ProductService;
import com.productsapp.domain.service.dto.PageDTO;
import com.productsapp.domain.service.mapper.ProductMapper;
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
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Save a product.
     *
     * @param productDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductDTO save(ProductDTO productDTO) throws EntityAlreadyExistsException {
        log.debug("Request to save Product : {}", productDTO);
        if (productDTO.getId() != null && productRepository.existsById(productDTO.getId())) {
            throw new EntityAlreadyExistsException(Product.class, productDTO.getId());
        }
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) throws EntityNotFoundException {
        log.debug("Request to save Product : {}", productDTO);
        if (productDTO.getId() == null || !productRepository.existsById(productDTO.getId())) {
            throw new EntityNotFoundException(Product.class, productDTO.getId());
        }
        Product modified = productMapper.toEntity(productDTO);
        Product original = productRepository.findById(productDTO.getId()).get();
        modified.setWhenCreated(original.getWhenCreated());
        modified = productRepository.save(modified);
        return productMapper.toDto(modified);
    }

    /**
     * Get all the products.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public PageDTO<ProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return new PageDTO<>(productRepository.findAll(pageable), productMapper::toDto);
    }

    /**
     * Get one product by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) throws EntityNotFoundException {
        log.debug("Request to get Product : {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, id));
        return productMapper.toDto(product);
    }

    /**
     * Delete the product by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) throws EntityNotFoundException {
        log.debug("Request to delete Product : {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, id));
        productRepository.delete(product);
    }
}
