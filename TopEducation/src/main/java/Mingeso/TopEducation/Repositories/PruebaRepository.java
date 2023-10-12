package Mingeso.TopEducation.Repositories;

import Mingeso.TopEducation.Entities.PruebaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PruebaRepository extends JpaRepository<PruebaEntity, Integer> {
}
