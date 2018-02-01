package com.tnd;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("clothes")
public class Clothes extends Product {

    private String type = "clothes";

    private String size;

}
