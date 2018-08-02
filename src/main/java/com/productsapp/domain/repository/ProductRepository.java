package com.productsapp.domain.repository;

import com.productsapp.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> { }
