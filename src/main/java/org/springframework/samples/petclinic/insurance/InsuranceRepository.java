package org.springframework.samples.petclinic.insurance;

import java.util.List;
import java.util.Optional;


public interface InsuranceRepository {
    Insurance save(Insurance i);
    List<Insurance> findAll();
    Optional<Insurance> findById(Integer id);

}
