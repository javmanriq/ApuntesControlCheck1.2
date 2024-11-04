package org.springframework.samples.petclinic.insurance;

import java.util.List;

import org.springframework.samples.petclinic.pet.PetService;


public class InsuranceService {
    
    private InsuranceRepository repo;
    private PetService petService;

    public InsuranceService(InsuranceRepository ir, PetService petService) {
        this.repo = ir;
        this.petService = petService;
    }

    public Insurance save(Insurance i) throws UnfeaseibleInsuranceModificationException {
        // TODO: Change this!
        return null;
    }
    
    public List<Insurance> getAll() {
        // TODO: Change this!
        return null;
    }

    public Insurance getInsurance(Integer id) {
        // TODO: Change this!
        return null;
    }

    public Insurance getInsuranceOfPet(String petName) {
        // TODO: Change this!
        return null;
    }

    public List<Insurance> getInsurancesBetween(Double min, Double max) {
        // TODO: Change this!
        return null; //repo.findInsurances(min, max);
    }
}
