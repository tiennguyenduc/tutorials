package com.tnd.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, scope = Product.class)
public class Product {

    private String name;

    private Category category;

    @Override
    public String toString() {
        return "Product{" +
               "name='" + name + '\'' +
               '}';
    }
}
