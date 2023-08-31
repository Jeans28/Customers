package com.example.mscustomers.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.internal.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class CustomerDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(name = "Identificador", example = "154")
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "nombre")
    @Schema(name = "Nombres del cliente", example = "Juan", required = true)
    private String nombre;

    @NotNull
    @NotBlank
    @Column(name = "apellido")
    @Schema(name = "Apellidos del cliente", example = "Calzado Garcia", required = true)
    private String apellido;

    @Column(name = "email")
    @Schema(name = "Correo electronico", example = "jeans@example.com", required = true)
    private String email;

    @NotNull
    @NotBlank(message = "El DNI no debe ser nulo o vacío")
    @Size(min = 8, max = 8, message = "El DNI debe tener una longitud de 8 caracteres")
    @Column(name = "dni")
    @Schema(name = "DNI del cliente", example = "78548965", required = true, maxLength = 8, minLength = 8)
    private String dni;

    @Column(name = "fecha_creacion")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
    @Schema(name = "Fecha de creacion", example = "12/12/2023")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_nacimiento")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Lima")
    @Schema(name = "Fecha de nacimiento", example = "10/01/2000", required = true)
    private Date fechaNacimiento;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CustomerDto customerDto = (CustomerDto) o;

        return Objects.equals(id, customerDto.id);
    }

    @Override
    public int hashCode() {
        return 339958611;
    }
}
