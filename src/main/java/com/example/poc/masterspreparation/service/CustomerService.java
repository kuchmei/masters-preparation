package com.example.poc.masterspreparation.service;

import com.example.poc.masterspreparation.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer saveCustomer(Customer customer);

    Customer getCustomerById(Long id);

    Customer updateCustomer(Customer student);

    void deleteCustomerById(Long id);
}

