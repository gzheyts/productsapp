package com.productsapp.domain.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {
    private static final String GENERATOR_NAME = "product_generator";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = "product_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @NotNull
    @Min(value = 1L)
    @Max(value = 1000L)
    @Column(name = "price", nullable = false)
    private Long price;

    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "when_created", nullable = false)
    @CreationTimestamp
    private LocalDateTime whenCreated;

    @ManyToOne
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public Product price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(LocalDateTime whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Category getCategory() {
        return category;
    }

    public Product category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", price=" + getPrice() +
                ", description='" + getDescription() + "'" +
                ", whenCreated='" + getWhenCreated() + "'" +
                "}";
    }
}
