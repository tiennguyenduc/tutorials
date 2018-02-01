package com.tnd;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.tnd.model.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(
        use = Id.NAME,
        include = As.PROPERTY,
        defaultImpl = HiEnd.class)
@JsonSubTypes(@JsonSubTypes.Type(Clothes.class))
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
