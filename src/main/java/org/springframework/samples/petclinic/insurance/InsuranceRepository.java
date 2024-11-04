package org.springframework.samples.petclinic.insurance;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


public interface InsuranceRepository extends CrudRepository<Insurance, Integer> {
    Insurance save(Insurance i);
    List<Insurance> findAll();
    Optional<Insurance> findById(Integer id);

}
