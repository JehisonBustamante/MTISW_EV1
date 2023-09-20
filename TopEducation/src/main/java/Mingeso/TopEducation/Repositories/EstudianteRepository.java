package Mingeso.TopEducation.Repositories;

import Mingeso.TopEducation.Entities.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository <EstudianteEntity, Integer>{
}
