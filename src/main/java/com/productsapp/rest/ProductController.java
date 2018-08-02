package com.productsapp.rest;

import com.productsapp.domain.service.dto.ProductDTO;
import com.productsapp.domain.service.ProductService;
import com.productsapp.domain.service.dto.PageDTO;
import com.productsapp.validation.EntityAlreadyExistsException;
import com.productsapp.validation.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import static com.productsapp.rest.ProductController.ENDPOINT;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
@Slf4j
@RestController
@RequestMapping(ENDPOINT)
public class ProductController {
    public static final String ENDPOINT = "/api/products";

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO)
            throws URISyntaxException, EntityAlreadyExistsException {
        log.debug("REST request to save Product : {}", productDTO);
        ProductDTO result = productService.save(productDTO);
        return ResponseEntity.created(new URI(ENDPOINT + result.getId()))
            .body(result);
    }

    @PutMapping
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO)
            throws URISyntaxException, EntityNotFoundException {
        log.debug("REST request to update Product : {}", productDTO);
        ProductDTO result = productService.update(productDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<PageDTO<ProductDTO>> getAllProducts(Pageable pageable) {
        log.debug("REST request to get a page of Products");
        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) throws EntityNotFoundException {
        log.debug("REST request to get Product : {}", id);
        return ResponseEntity.ok(productService.findById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws EntityNotFoundException {
        log.debug("REST request to delete Product : {}", id);
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
