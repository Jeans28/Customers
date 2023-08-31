package com.example.mscustomers.service;

import com.example.mscustomers.model.CustomerDto;
import com.example.mscustomers.model.Indicadores;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CustomerService {

    CustomerDto createCustomer(CustomerDto customerDto) throws Exception;

    List<CustomerDto> getClients(String dni, String email);

    Indicadores getIndicadores();
}
