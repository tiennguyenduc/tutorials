package com.tnd;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tnd.model.Category;

public class JsonTypeInfoTest {

    @Test
    public void JsonPolymorphismTest() throws IOException {

        Category category = new Category();
        category.setName("ss");

        HiEnd hiEnd = new HiEnd();
        hiEnd.setName("hi-end");
        hiEnd.setCategory(category);

        Clothes clothes = new Clothes();
        clothes.setName("pants");
        clothes.setSize("29");
        clothes.setCategory(category);

        ObjectMapper objectMapper = new ObjectMapper();

//        String json = objectMapper.writeValueAsString(hiEnd);
////
//        json =  objectMapper.writeValueAsString(clothes);

        String json = "{\"name\":\"lacy\"}";
        Product product = objectMapper.readerFor(Product.class).readValue(json);

        json = "{\"name\":\"lacy\", \"type\":\"clothes\", \"size\":\"28\"}";
        product = objectMapper.readerFor(Product.class).readValue(json);

    }

}
