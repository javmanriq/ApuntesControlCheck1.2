package org.springframework.samples.petclinic.insurance;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test1 extends ReflexiveTest{

    @Autowired(required = false)
    InsuranceRepository repo;
    
    @Autowired(required = false)
    EntityManager em;

    @Test
    public void test1(){
        repositoryExists();
        repositoryContainsMethod();
        testConstraints();
    }

    public void repositoryExists(){
        assertNotNull(repo,"The repository was not injected into the tests, its autowired value was null");
    }

    public void repositoryContainsMethod(){
        if(repo!=null){
            Optional<Insurance> v=repo.findById(12);
            assertFalse(null!=v && v.isPresent(), "No result (null) should be returned for an insurance that does not exist");
        }else
            fail("The repository was not injected into the tests, its autowired value was null");
    }

    void testConstraints(){
        Map<String,List<Object>> invalidValues=Map.of(
                                            "name",     List.of(
                                                                "","no",
                                                                "This is an example of a name too long for an Insurance"),
                                            "price",    List.of(-1.0)                                          
                                            );


        Insurance i= createValidInsurance(em);
        em.persist(i);
        em.flush();
        
        checkThatFieldsAreMandatory(i, em, "name","price");        
        
        checkThatValuesAreNotValid(i, invalidValues,em);
        
        Insurance i2= createValidInsurance(em);
        assertThrows("There should be a unique constraint on the name field",Exception.class, ()->{em.persist(i2); em.flush();});

    }

    public static Insurance createValidInsurance(EntityManager em){
        Insurance i=new Insurance();
        i.setName("Test Insurance"); 
        i.setPrice(1000.0);   
        return i;
    }
}
