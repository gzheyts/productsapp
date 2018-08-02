package com.productsapp;

import com.productsapp.domain.entities.Category;
import com.productsapp.domain.entities.Product;
import com.productsapp.domain.repository.CategoryRepository;
import com.productsapp.domain.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.IntStream;
/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
@SpringBootApplication
public class ProductsApp {

    public static void main(String[] args) {
        SpringApplication.run(ProductsApp.class, args);
    }

    @Bean
    public CommandLineRunner loader(ProductRepository productRepository, CategoryRepository categoryRepository) {
        return (String... args) -> {
            IntStream.range(0, 100).forEach(productNum -> {
                Product product = new Product();
                product.setName(String.format("Product name#%d", productNum));
                product.setDescription(String.format("Product description#%d", productNum));
                product.setPrice(500L + productNum);

                productRepository.save(product);
            });

            IntStream.range(0,5).forEach(categoryNum -> {
                Category category = new Category();
                category.setName(String.format("Category #%d", categoryNum));
                category.setDescription(String.format("Description #%d", categoryNum));
                categoryRepository.save(category);
            });
        };
    }
}
