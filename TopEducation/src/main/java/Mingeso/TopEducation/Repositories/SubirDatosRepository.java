package Mingeso.TopEducation.Repositories;

import Mingeso.TopEducation.Entities.SubirDatosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubirDatosRepository extends JpaRepository<SubirDatosEntity, Integer> {
}
