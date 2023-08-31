package com.example.mscustomers.controller;

import com.example.mscustomers.model.CustomerDto;
import com.example.mscustomers.model.Indicadores;
import com.example.mscustomers.service.CustomerService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/customer")
@Slf4j
@RequiredArgsConstructor
@Validated
@Api(tags = "Customers API", value= "Endpoints para gestionar clientes")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ApiOperation(value = "Registrar clientes nuevos")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cliente registrado exitosamente", response = CustomerDto.class),
            @ApiResponse(code = 400, message = "Solicitud incorrecta"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<CustomerDto> createCustomer(@ApiParam(name = "Datos del cliente a registrar") @RequestBody @Valid CustomerDto customer) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @GetMapping("")
    @ApiOperation(value = "Consulta clientes por DNI, Email o sin filtros")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta de cliente exitosa", response = CustomerDto.class),
            @ApiResponse(code = 400, message = "Solicitud incorrecta"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<CustomerDto>> getCustomer(@ApiParam(name = "DNI del cliente") @RequestParam(required = false) String dni,
                                            @ApiParam(name = "Email del cliente")  @RequestParam(required = false) String email) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(customerService.getClients(dni,email));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/indicadores")
    @ApiOperation(value = "Consulta cindicadores de natalidad, nacidos por mes entre otros. ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta exitosa", response = Indicadores.class),
            @ApiResponse(code = 400, message = "Solicitud incorrecta"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<Indicadores> getIndicadores() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(customerService.getIndicadores());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }



}
