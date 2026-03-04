package com.retail.retailbillingsystem.customer;

import com.retail.retailbillingsystem.customer.dto.CreateCustomerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@RequestBody CreateCustomerRequest request) {
        return customerService.createCustomer(request);
    }
}