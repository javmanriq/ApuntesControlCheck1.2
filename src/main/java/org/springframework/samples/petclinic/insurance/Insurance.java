package org.springframework.samples.petclinic.insurance;

import java.util.List;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.pet.Pet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Insurance extends BaseEntity {
    
    @NotNull
    @Size(min=3,max=50)
    @Column(unique = true)
    String name;
    
    @NotNull
    @Min(0)
    Double price;
    

    @Transient
    List<Pet> pets;
}