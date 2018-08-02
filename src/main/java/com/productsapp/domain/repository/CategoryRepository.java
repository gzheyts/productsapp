package com.productsapp.domain.repository;

import com.productsapp.domain.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findAllByNameContainsIgnoreCase(final String name, Pageable pageable);

}
