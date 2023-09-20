package Mingeso.TopEducation.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagar")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PagarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IdPago;
    private Boolean matricula;
    private Boolean arancel;
    private String descuento;
}
