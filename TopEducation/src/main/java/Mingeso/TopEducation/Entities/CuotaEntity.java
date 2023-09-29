package Mingeso.TopEducation.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CuotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id_cuota;

    private String RUN;
    private Integer montoInicial;
    private String estadoPago;
    private LocalDate fechaInicio;
    private LocalDate fechaPago;
    private Integer atrasoMeses;
    private Integer tipoError;
}
