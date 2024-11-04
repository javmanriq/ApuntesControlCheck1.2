package org.springframework.samples.petclinic.insurance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
public class Test6 extends ReflexiveTest {
    @Autowired(required = false)
    InsuranceRepository ir;
    @Autowired(required = false)
    PetRepository pr;
    @Autowired
    EntityManager em;

    @Autowired(required = false)
    InsuranceService is;

    @Test
    public void test6(){
        insuranceServiceIsInjected();
        insuranceServiceCanGetInsurancesBetween();        
        checkTransactionalityOfInsuranceService();
        
    }

    public void checkTransactionalityOfInsuranceService(){
        checkTransactional(InsuranceService.class,"getInsurancesBetween", Double.class, Double.class);        
    }

    public void insuranceServiceIsInjected() {
        assertNotNull(is,"InsuranceService was not injected by Spring");       
    }
    
    public void insuranceServiceCanGetInsurancesBetween(){
        assertNotNull(is,"InsuranceService was not injected by Spring");
        List<Insurance> insurances = is.getInsurancesBetween(0.0, 1500.0);
        assertEquals(2, insurances.size(), "The InsuranceService did not return the expected number of insurances");
        insurances = is.getInsurancesBetween(0.0, 800.0);
        assertEquals(1, insurances.size(), "The InsuranceService did not return the expected number of insurances");
        insurances = is.getInsurancesBetween(1500.0, 2000.0);
        assertEquals(0, insurances.size(), "The InsuranceService did not return the expected number of insurances");            
    }

}
