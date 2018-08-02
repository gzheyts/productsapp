package com.productsapp.rest;

import com.productsapp.domain.service.CategoryService;
import com.productsapp.domain.service.dto.CategoryDTO;
import com.productsapp.domain.service.dto.PageDTO;
import com.productsapp.validation.EntityAlreadyExistsException;
import com.productsapp.validation.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import static com.productsapp.rest.CategoryController.ENDPOINT;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
@RestController
@RequestMapping(ENDPOINT)
public class CategoryController {
    public static final String ENDPOINT = "/api/categories";

    private final Logger log = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO)
            throws URISyntaxException, EntityAlreadyExistsException {
        log.debug("REST request to save Category : {}", categoryDTO);
        CategoryDTO result = categoryService.save(categoryDTO);
        return ResponseEntity.created(new URI(ENDPOINT + result.getId()))
                .body(result);
    }

    @PutMapping
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws URISyntaxException, EntityNotFoundException {
        log.debug("REST request to update Category : {}", categoryDTO);
        CategoryDTO result = categoryService.update(categoryDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("search")
    public ResponseEntity<PageDTO<CategoryDTO>> search(Pageable pageable, @RequestParam("name") String name) {
        log.debug("REST request to search Categories");
        return ResponseEntity.ok(categoryService.searchByName(name, pageable));
    }

    @GetMapping
    public ResponseEntity<PageDTO<CategoryDTO>> getAllCategories(Pageable pageable) {
        log.debug("REST request to get a page of Categories");
        return ResponseEntity.ok(categoryService.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) throws EntityNotFoundException {
        log.debug("REST request to get Category : {}", id);
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws EntityNotFoundException {
        log.debug("REST request to delete Category : {}", id);
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
