package com.productsapp.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final String GENERATOR_NAME = "category_generator";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = "category_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(min = 6)
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Category description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        if (category.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                "}";
    }
}
