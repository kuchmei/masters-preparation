package com.example.poc.masterspreparation;

import com.example.poc.masterspreparation.dto.CustomerDto;
import com.example.poc.masterspreparation.exception.CustomerCreationException;
import com.example.poc.masterspreparation.model.Customer;
import com.example.poc.masterspreparation.model.Role;
import com.example.poc.masterspreparation.repository.CustomerRepository;
import com.example.poc.masterspreparation.repository.RoleRepository;
import com.example.poc.masterspreparation.service.CustomerService;
import com.example.poc.masterspreparation.service.CustomerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class CustomerServiceTest {

    private CustomerRepository clientRepository = mock(CustomerRepository.class);
    private RoleRepository roleRepository = mock(RoleRepository.class);
    private final CustomerService customerServiceTest = new CustomerServiceImpl(clientRepository, roleRepository);

    @Test
    public void saveCustomer_okTest() {
        CustomerDto customerDto = new CustomerDto("Test", "Test", "Test@gmail.com", "1234", true);

        when(clientRepository.save(refEq(getCustomer(), "password"))).thenReturn(getCustomer());
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(new Role("ROLE_ADMIN"));

        Customer actual = customerServiceTest.saveCustomer(customerDto);

        verify(clientRepository, times(1)).save(any());
        verify(roleRepository, times(1)).findByName(any());
        assertEquals("Test", actual.getFirstName());
    }

    @Test
    public void saveCustomer_errorTest() {
        CustomerDto customerDto = new CustomerDto("", "", "", "", true);

        assertThrows(CustomerCreationException.class, () -> customerServiceTest.saveCustomer(customerDto));
    }

    @Test
    public void getAllCustomer_okTest() {
        when(clientRepository.findAll()).thenReturn(new ArrayList<>());

        List<Customer> actual = customerServiceTest.getAllCustomers();

        assertTrue(actual.isEmpty());
    }

    @Test
    public void getCustomerById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(getCustomer()));

        Customer actual = customerServiceTest.getCustomerById(1L);

        assertEquals("Test", actual.getFirstName());
    }

    @Test
    public void deleteCustomer_errorTest() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(getCustomer()));

        customerServiceTest.deleteCustomerById(1L);

        verify(clientRepository, times(1)).findById(any());
        verify(clientRepository, times(0)).deleteById(any());
    }

    @Test
    public void deleteCustomer_okTest() {
        Customer customer = getCustomer();
        customer.setAdmin(false);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(customer));

        customerServiceTest.deleteCustomerById(1L);

        verify(clientRepository, times(1)).findById(any());
        verify(clientRepository, times(1)).deleteById(any());
    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        customer.setPassword(bCryptPasswordEncoder.encode("1234"));
        customer.setFirstName("Test");
        customer.setLastName("Test");
        customer.setEmail("Test@gmail.com");
        customer.setRoles(Collections.singletonList(new Role("ROLE_ADMIN")));
        customer.setAdmin(true);

        return customer;
    }


}
