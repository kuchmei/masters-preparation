package com.example.poc.masterspreparation.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.poc.masterspreparation.model.Customer;
import com.example.poc.masterspreparation.model.Role;
import com.example.poc.masterspreparation.repository.CustomerRepository;
import com.example.poc.masterspreparation.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService, UserDetailsService {

    private final CustomerRepository clientRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return clientRepository.findAll();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return clientRepository.save(customer);
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
        clientRepository.deleteById(id);
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
