package com.example.demo.repository;

import com.example.demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer, Integer> {
    boolean existsByPhoneNumber(String PhoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
}
