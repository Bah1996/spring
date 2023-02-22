package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.CustomerDto;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/api/customers")
    public ResponseEntity<List<Customer>> getCustomers(){
        List<Customer> customer = customerService.getCustomer();
        return ResponseEntity.ok(customer);
    }
    @GetMapping("/api/customers/{id}")
    public Customer getCustomer(@PathVariable Integer id){
        Customer customerById = customerService.getCustomerById(id);
        return customerById;
    }

    @PostMapping("/api/customers")
    public ApiResponse addCustomer(@RequestBody CustomerDto customerDto){
        ApiResponse apiResponse = customerService.addCustomer(customerDto);
        return apiResponse;
    }

    @PutMapping("/api/customers/{id}")
    public ApiResponse editCustomer(@PathVariable Integer id, @RequestBody CustomerDto customerDto){
        ApiResponse apiResponse = customerService.editCustomer(id, customerDto);
        return apiResponse;
    }

    @DeleteMapping("/api/customers/{id}")
    public ApiResponse deleteCustomer(@PathVariable Integer id){
        ApiResponse apiResponse = customerService.deleteCustomer(id);
        return apiResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
