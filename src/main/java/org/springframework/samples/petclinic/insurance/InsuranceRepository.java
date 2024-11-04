package org.springframework.samples.petclinic.insurance;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface InsuranceRepository extends CrudRepository<Insurance, Integer> {
    Insurance save(Insurance i);
    List<Insurance> findAll();
    Optional<Insurance> findById(Integer id);

    @Query("SELECT i FROM Insurance i WHERE i.price >= :min AND i.price <= :max")
    List<Insurance> findInsurancesBetween(Double min, Double max);
}
