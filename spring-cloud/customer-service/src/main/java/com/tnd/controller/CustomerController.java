package com.tnd.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tnd.entity.Account;
import com.tnd.entity.Customer;
import com.tnd.entity.CustomerType;
import com.tnd.intercomm.AccountService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CustomerController {

    @Autowired
    private AccountService accountService;

    private List<Customer> customers;

    public CustomerController() {
        customers = new ArrayList<>();
        customers.add(new Customer(1, "12345", "Adam Kowalski", CustomerType.INDIVIDUAL, Collections.EMPTY_LIST));
        customers.add(new Customer(2, "12346", "Anna Malinowska", CustomerType.INDIVIDUAL, Collections.EMPTY_LIST));
        customers.add(new Customer(3, "12347", "Paweł Michalski", CustomerType.INDIVIDUAL, Collections.EMPTY_LIST));
        customers.add(new Customer(4, "12348", "Karolina Lewandowska", CustomerType.INDIVIDUAL, Collections.EMPTY_LIST));
    }

    @RequestMapping("/customers/pesel/{pesel}")
    public Customer findByPesel(@PathVariable("pesel") String pesel) {
        log.info(String.format("Customer.findByPesel(%s)", pesel));
        return customers.stream().filter(it -> it.getPesel().equals(pesel)).findFirst().get();
    }

    @RequestMapping("/customers")
    public List<Customer> findAll() {
        log.info("Customer.findAll()");
        return customers;
    }

    @RequestMapping("/customers/{id}")
    public Customer findById(@PathVariable("id") Integer id) {
        log.info(String.format("Customer.findById(%s)", id));
        Customer customer = customers.stream().filter(it -> it.getId().intValue()==id.intValue()).findFirst().get();
        List<Account> accounts =  accountService.getAccount(id);
        customer.setAccounts(accounts);
        return customer;
    }

}
