package com.productsapp.domain.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
public class ProductDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    private String name;

    @NotNull
    @Min(value = 1L)
    @Max(value = 1000L)
    private Long price;

    @Size(max = 200)
    private String description;

    private LocalDateTime whenCreated;

    private Long categoryId;

    private String categoryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (productDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", price=" + getPrice() +
                ", description='" + getDescription() + "'" +
                ", whenCreated='" + getWhenCreated() + "'" +
                "}";
    }
}
