package org.springframework.samples.petclinic.insurance;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test5 extends ReflexiveTest{
    @SpyBean
    PetService ps;

    @Autowired(required = false)
    InsuranceService is;    

    private static final String A_PET_NAME = "Rosy";
    
    @Test 
    public void test5(){
        insuranceServiceIsInjected();
        insuranceServiceCanGetInsurancesByPet();        
        checkTransactionalityOfInsuranceService();
        
    }

    public void checkTransactionalityOfInsuranceService(){
        checkTransactional(InsuranceService.class,"getInsuranceOfPet", String.class);
        checkTransactional(PetService.class,"getPetByName", String.class);        
    }

    public void insuranceServiceIsInjected() {
        assertNotNull(is,"InsuranceService was not injected by Spring");       
    }
    
    public void insuranceServiceCanGetInsurancesByPet(){
        assertNotNull(is,"InsuranceService was not injected by Spring");
        Insurance insurance=is.getInsuranceOfPet(A_PET_NAME);
        assertNotNull(insurance,"The insurance for Rosy found by the InsuranceService was null");
        // The test fails if the service does not invoke the getPetByName of the Pet service:
        verify(ps).getPetByName(A_PET_NAME);            
    }

   

}
