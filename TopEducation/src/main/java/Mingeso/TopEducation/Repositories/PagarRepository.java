package Mingeso.TopEducation.Repositories;

import Mingeso.TopEducation.Entities.PagarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagarRepository extends JpaRepository<PagarEntity, Integer> {
}
