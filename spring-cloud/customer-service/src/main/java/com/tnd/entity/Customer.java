package com.tnd.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private Integer id;

    private String pesel;

    private String name;

    private CustomerType type;

    private List<Account> accounts;

}
