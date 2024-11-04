package org.springframework.samples.petclinic.insurance;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.samples.petclinic.pet.Pet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsuranceDTO {
    Integer id;
    String name;
    Double price;
    List<String> pets;

    public InsuranceDTO(Insurance i) {
        this.id = i.getId();
        this.name = i.getName();
        this.price = i.getPrice();
        this.pets = i.getPets().stream().map(Pet::getName).collect(Collectors.toList());
    }
    
}
