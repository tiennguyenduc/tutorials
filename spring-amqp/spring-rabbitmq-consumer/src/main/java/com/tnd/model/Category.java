package com.tnd.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Category.class)
public class Category {

    private String name;

    private List<Product> products;

    @Override
    public String toString() {
        return "Category{" +
               "name='" + name + '\'' +
               ", products=" + products +
               '}';
    }

}
