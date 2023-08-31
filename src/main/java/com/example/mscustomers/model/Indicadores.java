package com.example.mscustomers.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Indicadores {
    @Schema(name = "clientesNacidosPorMes", description = "Clientes nacidos por mes", example = "{\"enero\": 15, \"febrero\": 1}")
    private Map<String, Long> clientesNacidosPorMes;
    @Schema(name = "mesConMayorClientesNacidos", description = "Mes con mayor cantidad de clientes nacidos", example = "abril")
    private String mesConMayorClientesNacidos;
    @Schema(name = "mesConMenorClientesNacidos", description = "Mes con menor cantidad de clientes nacidos", example = "julio")
    private String mesConMenorClientesNacidos;
    @Schema(name = "tasaNatalidadPorMes", description = "Tasa de natalidad por mes", example = "{\"enero\": 5.5, \"febrero\": 10.7}")
    private Map<String, Double> tasaNatalidadPorMes;
}
