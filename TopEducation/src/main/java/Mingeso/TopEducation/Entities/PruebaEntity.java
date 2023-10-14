package Mingeso.TopEducation.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "pruebas")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PruebaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPrueba;
    private String RUN;
    private Integer puntaje;
    private LocalDate fechaPrueba;
}
