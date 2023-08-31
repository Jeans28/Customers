package com.example.mscustomers.service;

import com.example.mscustomers.model.CustomerDto;
import com.example.mscustomers.model.Indicadores;
import com.example.mscustomers.repository.CustomerRepository;
import com.example.mscustomers.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateCustomerSuccess() throws Exception {
        CustomerDto customer = new CustomerDto();
        customer.setDni("12345678");
        customer.setEmail("test@example.com");

        when(repository.findCustomerByDniOrEmail(any(), any())).thenReturn(null);
        when(repository.save(any())).thenReturn(customer);

        CustomerDto result = customerService.createCustomer(customer);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(customer.getDni(), result.getDni());
        Assertions.assertEquals(customer.getEmail(), result.getEmail());
        Assertions.assertNotNull(result.getFechaCreacion());
    }

    @Test
    public void testCreateCustomerError() {
        CustomerDto customer = new CustomerDto();
        customer.setDni("12345678");
        customer.setEmail("test@example.com");

        when(repository.findCustomerByDniOrEmail(any(), any())).thenReturn(customer);

        assertThrows(Exception.class, () -> customerService.createCustomer(customer));
    }

    @Test
    public void testGetClientsByDniSuccess() {
        String dni = "12345678";

        when(repository.findCustomerByDni(dni)).thenReturn(new CustomerDto());

        List<CustomerDto> result = customerService.getClients(dni, null);

        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void testGetClientsByEmailSuccess() {
        String email = "test@example.com";

        when(repository.findCustomerByEmail(email)).thenReturn(new CustomerDto());

        List<CustomerDto> result = customerService.getClients(null, email);

        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void testGetClientsWithoutFiltersSuccess() {
        when(repository.findAll()).thenReturn(Collections.singletonList(new CustomerDto()));

        List<CustomerDto> result = customerService.getClients(null, null);

        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void testGetClientsByDniError() {
        String dni = "12345678";

        when(repository.findCustomerByDni(dni)).thenReturn(null);

        List<CustomerDto> result = customerService.getClients(dni, null);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetClientsByEmailError() {
        String email = "test@example.com";

        when(repository.findCustomerByEmail(email)).thenReturn(null);

        List<CustomerDto> result = customerService.getClients(null, email);

        Assertions.assertTrue(result.isEmpty());
    }


    @Test
    public void testGetIndicadoresWithCustomersAndNoFechaNac() throws ParseException {
        List<CustomerDto> customers =prepareCustomersFechaNcimiento();

        when(repository.findAll()).thenReturn(customers);

        Indicadores indicadores = customerService.getIndicadores();

        Assertions.assertNotNull(indicadores);
        Assertions.assertFalse(indicadores.getClientesNacidosPorMes().isEmpty());
        Assertions.assertEquals("abril", indicadores.getMesConMayorClientesNacidos());

        int totalClientes = customers.size();
        Map<String, Double> tasasEsperadas = customers.stream()
                .map(customer -> customer.getFechaNacimiento().getMonth())
                .collect(Collectors.groupingBy(month -> new DateFormatSymbols(new Locale("es")).getMonths()[month],
                        Collectors.counting()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> (double) entry.getValue() / totalClientes));

        for (Map.Entry<String, Double> entry : tasasEsperadas.entrySet()) {
            Assertions.assertEquals(entry.getValue(), indicadores.getTasaNatalidadPorMes().get(entry.getKey()), 0.001);
        }
    }



    private List<CustomerDto> prepareCustomersFechaNcimiento() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return Arrays.asList(
                CustomerDto.builder()
                        .nombre("Juan")
                        .apellido("Calzado")
                        .email("juan@example.com")
                        .dni("12345678")
                        .fechaNacimiento(dateFormat.parse("10/01/1990"))
                        .build(),
                CustomerDto.builder()
                        .nombre("Ximena")
                        .apellido("Garcia")
                        .email("garcia@example.com")
                        .dni("23456789")
                        .fechaNacimiento(dateFormat.parse("05/04/1985"))
                        .build(),
                CustomerDto.builder()
                        .nombre("Michael")
                        .apellido("Perez")
                        .email("michael@example.com")
                        .dni("34567890")
                        .fechaNacimiento(dateFormat.parse("20/10/2000"))
                        .build()
        );
    }
}
