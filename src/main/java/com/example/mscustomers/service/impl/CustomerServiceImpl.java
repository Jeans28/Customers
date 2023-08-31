package com.example.mscustomers.service.impl;

import com.example.mscustomers.model.CustomerDto;
import com.example.mscustomers.model.Indicadores;
import com.example.mscustomers.repository.CustomerRepository;
import com.example.mscustomers.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository repository;

    @Override
    public CustomerDto createCustomer(CustomerDto customer) throws Exception {
        try {
            if (Objects.nonNull(getCustomerDniOrEmail(customer.getDni(), customer.getEmail()))){
                log.info("Cliente ya esta registrado por el DNI y/o Email: " + customer.getDni().concat("-").concat(customer.getEmail()));
                throw new Exception("Cliente ya esta registrado con el DNI o Email.");
            }
            log.info("Creando cliente: " + customer);
            customer.setFechaCreacion(LocalDateTime.now());
            return repository.save(customer);
        }catch (Exception e){
            log.error("Ocurrio un error al crear el cliente: " + e.getMessage());
           throw new Exception(e.getMessage());
        }
    }



    @Override
    public List<CustomerDto> getClients(String dni, String email) {
        if (Objects.isNull(dni) && Objects.isNull(email)) {
            log.info("Consultando clientes sin filtros");
            return repository.findAll();
        } else if(Objects.nonNull(dni)){
            log.info("Consultando cliente por DNI: " + dni);
            CustomerDto customer = repository.findCustomerByDni(dni);
            return Objects.nonNull(customer)? Collections.singletonList(customer) : Collections.emptyList();
        }else {
            log.info("Consultando cliente por Email: " + email);
            CustomerDto customer = repository.findCustomerByEmail(email);
            return Objects.nonNull(customer)? Collections.singletonList(customer) : Collections.emptyList();
        }
    }

    @Override
    public Indicadores getIndicadores() {
        List<CustomerDto> customers = repository.findAll();

        Map<String, Long> clientesNacidosPorMes = calcularClientesNacidosPorMes(customers);


        Map<String, Integer> todosLosMeses = obtenerTodosLosMeses();
        Map<String, Double> tasaNatalidadPorMes = calcularTasaNatalidadPorMes(clientesNacidosPorMes, customers.size());
        clientesNacidosPorMes = completarMesesSinClientes(clientesNacidosPorMes, todosLosMeses);

        Indicadores indicadores = new Indicadores();
        indicadores.setClientesNacidosPorMes(clientesNacidosPorMes);
        indicadores.setMesConMayorClientesNacidos(obtenerMesConMayorClientes(clientesNacidosPorMes));
        indicadores.setMesConMenorClientesNacidos(obtenerMesConMenorClientes(clientesNacidosPorMes));
        indicadores.setTasaNatalidadPorMes(completarTasaNatalidad(tasaNatalidadPorMes, todosLosMeses));

        return indicadores;
    }

    private Map<String, Long> calcularClientesNacidosPorMes(List<CustomerDto> customers) {
        return customers.stream()
                .map(customer -> obtenerMesNacimiento(customer.getFechaNacimiento()))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private String obtenerMesConMayorClientes(Map<String, Long> clientesNacidosPorMes) {
        return clientesNacidosPorMes.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private String obtenerMesConMenorClientes(Map<String, Long> clientesNacidosPorMes) {
        return clientesNacidosPorMes.entrySet().stream()
                .min(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private Map<String, Double> calcularTasaNatalidadPorMes(Map<String, Long> clientesNacidosPorMes, int totalClientes) {
        return clientesNacidosPorMes.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (double) entry.getValue() / totalClientes
                ));
    }

    private String obtenerMesNacimiento(Date fechaNacimiento) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaNacimiento);
        int mes = cal.get(Calendar.MONTH);
        return new DateFormatSymbols(new Locale("es")).getMonths()[mes];
    }

    private Map<String, Double> completarTasaNatalidad(Map<String, Double> tasaNatalidadPorMes, Map<String, Integer> todosLosMeses) {
        return todosLosMeses.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> tasaNatalidadPorMes.getOrDefault(entry.getKey(), 0.0)
                ));
    }

    private Map<String, Integer> obtenerTodosLosMeses() {
        DateFormatSymbols symbols = new DateFormatSymbols(new Locale("es"));
        String[] meses = symbols.getMonths();
        return Arrays.stream(meses, 0, meses.length - 1)
                .collect(Collectors.toMap(
                        String::toLowerCase,
                        mes -> 0
                ));
    }

    private Map<String, Long> completarMesesSinClientes(Map<String, Long> clientesNacidosPorMes, Map<String, Integer> todosLosMeses) {
        return todosLosMeses.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> clientesNacidosPorMes.getOrDefault(entry.getKey(), 0L)
                ));
    }

    public CustomerDto getCustomerDniOrEmail(String dni, String email){
        log.info("Buscando Customer por DNI or Email: " + dni.concat(" - ").concat(email));
        return repository.findCustomerByDniOrEmail(dni, email);
    }


}
