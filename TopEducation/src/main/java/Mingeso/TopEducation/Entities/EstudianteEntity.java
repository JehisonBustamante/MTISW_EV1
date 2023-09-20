package Mingeso.TopEducation.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estudiante")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EstudianteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer RUN;
    private String apellidos;
    private String nombres;
    private String fecha_nacimiento;
    private String tipo_colegio;
    private String nombre_colegio;
    private Integer anio_egreso;
}
