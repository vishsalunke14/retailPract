package com.retail.retailbillingsystem.customer;

import com.retail.retailbillingsystem.customer.dto.CreateCustomerRequest;
import com.retail.retailbillingsystem.user.User;
import com.retail.retailbillingsystem.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public Customer createCustomer(CreateCustomerRequest request) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Customer customer = Customer.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .address(request.getAddress())
                .user(user)   // 🔥 IMPORTANT
                .build();

        return customerRepository.save(customer);
    }
}