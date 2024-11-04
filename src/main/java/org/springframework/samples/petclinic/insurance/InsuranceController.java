package org.springframework.samples.petclinic.insurance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/insurances")
public class InsuranceController {
    @Autowired
    InsuranceService is;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Insurance creatInsurance(@RequestBody @Valid Insurance i) throws UnfeaseibleInsuranceModificationException {
        //TODO: process POST request
        is.save(i);
        return i;
    }

    @GetMapping("/{id}")
    public InsuranceDTO getInsurance(@PathVariable("id") Integer id) {
    Insurance i = is.getInsurance(id);
    if(i == null){
        throw new ResourceNotFoundException("No se ha podido encontrar el seguro con id: " + id);
    }
    return new InsuranceDTO(i);
    }
}
