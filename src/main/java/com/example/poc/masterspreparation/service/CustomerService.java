package com.example.poc.masterspreparation.service;

import com.example.poc.masterspreparation.dto.CustomerDto;
import com.example.poc.masterspreparation.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer saveCustomer(CustomerDto customer);

    Customer getCustomerById(Long id);

    Customer updateCustomer(Customer student);

    void deleteCustomerById(Long id);

    Customer findByEmail(String email);
}

