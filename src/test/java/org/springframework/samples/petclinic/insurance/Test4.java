package org.springframework.samples.petclinic.insurance;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.pet.PetService;

@ExtendWith(MockitoExtension.class)
public class Test4 extends ReflexiveTest{
    @Mock
    InsuranceRepository ir;
    @Mock
    PetService ps;


    InsuranceService is;    
    
    @BeforeEach
    public void configuation(){
        is=new InsuranceService(ir,ps);
    }

    @Test 
    public void test4(){
        insuranceServiceIsInjected();
        insuranceServiceCanGetInsurances();        
        insuranceServiceCanSaveInsurances();
        checkTransactionalityOfInsuranceService();
        
    }

    public void checkTransactionalityOfInsuranceService(){
        checkTransactional(InsuranceService.class,"save", Insurance.class);        
        checkTransactional(InsuranceService.class,"getAll");
    }

    public void insuranceServiceIsInjected() {
        assertNotNull(is,"InsuranceService was not injected by Spring");       
    }
    
    public void insuranceServiceCanGetInsurances(){
        assertNotNull(is,"InsuranceService was not injected by Spring");
        when(ir.findAll()).thenReturn(List.of());
        List<Insurance> offers=is.getAll();
        assertNotNull(offers,"The list of Insurances found by the InsuranceService was null");
        // The test fails if the service does not invoke the findAll of the repository:
        verify(ir).findAll();            
    }

    private void insuranceServiceCanSaveInsurances() {        
        assertNotNull(is,"InsuranceService was not injected by Spring");
        when(ir.save(any(Insurance.class))).thenReturn(null);
        Insurance i=Test1.createValidInsurance(null);
        try {
            is.save(i);
        } catch (UnfeaseibleInsuranceModificationException e) {
            fail("The exception should not be thrown.");
        }
        // The test fails if the service does not invoke the save function of the repository:
        verify(ir).save(i);                
    }

}
