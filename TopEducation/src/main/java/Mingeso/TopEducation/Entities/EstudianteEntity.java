package Mingeso.TopEducation.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "estudiante")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EstudianteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer idEstudiante;

    private String RUN;
    private String apellidos;
    private String nombres;
    private LocalDate fechaNacimiento;
    private String tipoColegio;
    private String nombreColegio;
    private Integer anioEgreso;
}
