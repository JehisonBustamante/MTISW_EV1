package Mingeso.TopEducation.Repositories;

import Mingeso.TopEducation.Entities.CuotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CuotaRepository extends JpaRepository <CuotaEntity, Integer> {
    CuotaEntity findByRUN(String RUN);
    ArrayList<CuotaEntity> findAllByRUN(String RUN);
    ArrayList<CuotaEntity> findAll();

    void deleteAllByRUN(String RUN);
}
