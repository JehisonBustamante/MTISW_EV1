package Mingeso.TopEducation.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "promedio")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PromedioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer idEstudiante;

    private String RUN;
    private Double promedio;
    private LocalDate fechaPromedio;
}
