package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.CustomerDto;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getCustomer(){
        List<Customer> customerList = customerRepository.findAll();
        return  customerList;
    }

    public Customer getCustomerById(Integer id){
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent())
            return customerOptional.get();
        return null;

    }

    public ApiResponse addCustomer(CustomerDto customerDto){
        boolean existsByPhoneNumber = customerRepository.existsByPhoneNumber(customerDto.getPhoneNumber());
        if (existsByPhoneNumber){
            return new ApiResponse("Bunday mijoz mavjud ", false);
        }
        Customer customer = new Customer();
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setFullName(customerDto.getFullName());
        customer.setAddress(customerDto.getAddress());
        customerRepository.save(customer);
        return new ApiResponse("Mijoz saqlandi",true);
    }

    public ApiResponse editCustomer(Integer id, CustomerDto customerDto){
        boolean existsByPhoneNumber = customerRepository.existsByPhoneNumberAndIdNot(customerDto.getPhoneNumber(),id);
        if (existsByPhoneNumber){
            return new ApiResponse("Bunday telefon raqamli  mijoz mavjud ", false);
        }
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent()){
            return new ApiResponse("Bunday mijoz aniqlanmadi",false);
        }
        Customer customer = optionalCustomer.get();
        customer.setAddress(customerDto.getAddress());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setFullName(customerDto.getFullName());
        customerRepository.save(customer);
        return new ApiResponse("Mijoz tahrirlandi",true);

    }

    public ApiResponse deleteCustomer(Integer id){
        customerRepository.deleteById(id);
        return new ApiResponse("Mijoz o'chirildi",true);
    }


}
