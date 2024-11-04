package org.springframework.samples.petclinic.insurance;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.samples.petclinic.pet.Pet;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsuranceDTO {
    Integer id;

    @NotNull
    @Size(min=3,max=50)
    @Column(unique = true)
    String name;

    @NotNull
    @Min(0)
    Double price;

    List<String> pets;

    public InsuranceDTO() {
    }

    public InsuranceDTO(Insurance i) {
        this.id = i.getId();
        this.name = i.getName();
        this.price = i.getPrice();
        this.pets = i.getPets().stream().map(Pet::getName).collect(Collectors.toList());
    }
    
}
