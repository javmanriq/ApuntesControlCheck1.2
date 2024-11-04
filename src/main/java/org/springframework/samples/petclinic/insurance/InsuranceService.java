package org.springframework.samples.petclinic.insurance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InsuranceService {
    
    @Autowired
    private InsuranceRepository repo;
    private PetService petService;

    public InsuranceService(InsuranceRepository ir, PetService petService) {
        this.repo = ir;
        this.petService = petService;
    }

    @Transactional
    public Insurance save(Insurance i) throws UnfeaseibleInsuranceModificationException {
        // TODO: Change this!
        repo.save(i);
        return i;
    }
    
    @Transactional(readOnly = true)
    public List<Insurance> getAll() {
        // TODO: Change this!
        return repo.findAll();
    }

    public Insurance getInsurance(Integer id) {
        // TODO: Change this!
        return null;
    }

    @Transactional
    public Insurance getInsuranceOfPet(String petName) {
        // TODO: Change this!
        Pet pet = petService.getPetByName(petName);
        if (pet != null) {
            return pet.getInsurance();
        }
        return null;
    }

    public List<Insurance> getInsurancesBetween(Double min, Double max) {
        // TODO: Change this!
        return null; //repo.findInsurances(min, max);
    }
}
