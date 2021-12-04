package com.example.poc.masterspreparation.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.example.poc.masterspreparation.dto.CustomerDto;
import com.example.poc.masterspreparation.exception.CustomerCreationException;
import com.example.poc.masterspreparation.model.Customer;
import com.example.poc.masterspreparation.model.Role;
import com.example.poc.masterspreparation.repository.CustomerRepository;
import com.example.poc.masterspreparation.repository.RoleRepository;
import com.example.poc.masterspreparation.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService, UserDetailsService {

    private final CustomerRepository clientRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return clientRepository.findAll();
    }

    @Override
    public Customer saveCustomer(CustomerDto customer) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (customer.getFirstName().equals("") || customer.getLastName().equals("")
                || customer.getEmail().equals("") || customer.getPassword().equals("")) {
            throw new CustomerCreationException("Всі поля клієнта повинні бути заповнені");
        }

        Customer customerModel = new Customer();
        customerModel.setFirstName(customer.getFirstName());
        customerModel.setLastName(customer.getLastName());
        customerModel.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        customerModel.setEmail(customer.getEmail());


        if (customer.getIsAdmin()) {
            customerModel.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_ADMIN")));
            customerModel.setAdmin(true);
        }
         else {
            customerModel.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        }
        return clientRepository.save(customerModel);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return clientRepository.findById(id).get();
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return clientRepository.save(customer);
    }

    @Override
    public void deleteCustomerById(Long id) {
        Customer customer = getCustomerById(id);
        if (!customer.isAdmin()) {
            clientRepository.deleteById(id);
        }
    }

    public Customer findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = findByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", email));
        }
        return new User(customer.getEmail(), customer.getPassword(), mapRolesToAuthorities(customer.getRoles()));


    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
