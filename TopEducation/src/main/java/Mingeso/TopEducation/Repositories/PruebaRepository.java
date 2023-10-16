package Mingeso.TopEducation.Repositories;

import Mingeso.TopEducation.Entities.PruebaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface PruebaRepository extends JpaRepository<PruebaEntity, Integer> {
    ArrayList<PruebaEntity> findAllByRUN(String RUN);

    PruebaEntity findByRUNAndFechaPrueba(String run, LocalDate fechaPrueba);
}
