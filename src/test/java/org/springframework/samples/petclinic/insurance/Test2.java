package org.springframework.samples.petclinic.insurance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test2 extends ReflexiveTest{
    
    @Autowired(required = false)
    EntityManager em; 

    @Autowired(required = false)
    PetRepository pr;

    @Test
    public void test2(){
        testConstraints();
        testAnnotations();        
    }

    private void testAnnotations() {
        checkThatFieldIsAnnotatedWith(Pet.class, "insurance", ManyToOne.class);
        checkThatFieldIsAnnotatedWith(Insurance.class, "pets", OneToMany.class);
    }

    private void testConstraints() {
        Insurance i=Test1.createValidInsurance(em);
        em.persist(i);
        em.flush();
        Pet p = pr.findById(1).orElseThrow();
        setValue(p, "insurance", Insurance.class, i);
        em.persist(p);
        em.flush();
        em.clear();
        i = em.find(Insurance.class,invokeMethodReflexively(i, "getId"));
        assertEquals(p.getId(), i.getPets().iterator().next().getId(), "The insurance should be linked to the pet mapping the fields");
    }
}
