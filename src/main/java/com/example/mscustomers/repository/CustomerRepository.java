package com.example.mscustomers.repository;

import com.example.mscustomers.model.CustomerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerDto, Long> {

    CustomerDto findCustomerByDni(String dni);

    CustomerDto findCustomerByEmail(String email);

    CustomerDto findCustomerByDniOrEmail(String dni, String email);

}
