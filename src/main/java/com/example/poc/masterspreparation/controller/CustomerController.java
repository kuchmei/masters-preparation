package com.example.poc.masterspreparation.controller;

import com.example.poc.masterspreparation.model.Customer;
import com.example.poc.masterspreparation.service.CustomerService;
import com.example.poc.masterspreparation.service.MailService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping(value = "/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // handler method to handle list students and return mode and view
    @GetMapping()
    public String listCustomers(Model model, Principal principal) {
        System.out.println(principal.getName());

        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers";
    }

    @GetMapping("/new")
    public String createCustomerForm(Model model) {
        // create student object to hold student form data
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "create_customer";

    }

    @PostMapping()
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String editCustomerForm(@PathVariable Long id, @NotNull Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "edit_customer";
    }

    @PostMapping("/{id}")
    public String updateCustomer(@PathVariable Long id,
                               @ModelAttribute("customer") Customer customer) {

        // get student from database by id
        Customer existingCustomer = customerService.getCustomerById(id);
        existingCustomer.setId(id);
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEmail(customer.getEmail());

        // save updated student object
        customerService.updateCustomer(existingCustomer);
        return "redirect:/customers";
    }

    // handler method to handle delete student request

    @GetMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return "redirect:/customers";
    }

}
