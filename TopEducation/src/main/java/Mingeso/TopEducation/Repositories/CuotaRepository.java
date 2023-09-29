package Mingeso.TopEducation.Repositories;

import Mingeso.TopEducation.Entities.CuotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuotaRepository extends JpaRepository<CuotaEntity, Integer> {
    CuotaEntity findByRUN(String RUN);
}
