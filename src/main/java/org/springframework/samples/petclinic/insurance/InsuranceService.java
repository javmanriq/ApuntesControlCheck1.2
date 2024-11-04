package org.springframework.samples.petclinic.insurance;

import java.util.List;
import java.util.Optional;

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
        Insurance oldInsurance = getInsurance(i.getId());
        if (oldInsurance != null) {
            if(i.getPrice() > 1.7 *oldInsurance.getPrice() || i.getPrice() < 0.3 * oldInsurance.getPrice()){
                throw new UnfeaseibleInsuranceModificationException();
            }
        }
        repo.save(i);
        return i;
    }
    
    @Transactional(readOnly = true)
    public List<Insurance> getAll() {
        // TODO: Change this!
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Insurance getInsurance(Integer id) {
        // TODO: Change this!
        Optional<Insurance> i = repo.findById(id);
        return i.isEmpty() ? null : i.get();
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

    @Transactional
    public List<Insurance> getInsurancesBetween(Double min, Double max) {
        // TODO: Change this!
        return repo.findInsurancesBetween(min,max); //repo.findInsurances(min, max);
    }
}
