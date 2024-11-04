package org.springframework.samples.petclinic.insurance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test3 extends ReflexiveTest {
    @Autowired(required = false)
    InsuranceRepository ir;
    @Autowired(required = false)
    PetRepository pr;
    @Autowired
    EntityManager em;


    


    @Test
    public void test3(){
        testInitialInsurances();
        testLinkedInsurances();
    }



    public void testInitialInsurances(){
        List<Insurance> insurances=ir.findAll();
        assertTrue(insurances.size()==2, "Exactly two Insurances should be present in the DB");
        
        Optional<Insurance> i1=ir.findById(1);
        assertTrue(i1.isPresent(),"There should exist a Offer with id:1");
        assertEquals("Premium Insurance" ,i1.get().getName());
        assertEquals(1000.0,i1.get().getPrice());        

        Optional<Insurance> i2=ir.findById(2);
        assertTrue(i2.isPresent(),"There should exist a Offer with id:2");
        assertEquals("Medium Insurance" ,i2.get().getName());
        assertEquals(500.0,i2.get().getPrice());        


    }

    public void testLinkedInsurances()
    {
        Pet p3 = pr.findById(3).orElseThrow();
        Pet p4 = pr.findById(4).orElseThrow();;
        Pet p5 = pr.findById(5).orElseThrow();

        assertEquals(1,(Integer)invokeMethodReflexively(p3.getInsurance(), "getId"),"The id of the Insurance of the Pet with id:3 should be 1");
        assertEquals(1,(Integer)invokeMethodReflexively(p4.getInsurance(), "getId"),"The id of the Insurance of the Pet with id:4 should be 1");
        assertEquals(2,(Integer)invokeMethodReflexively(p5.getInsurance(), "getId"),"The id of the Insurance of the Pet with id:5 should be 2");
    }
}
