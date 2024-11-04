package org.springframework.samples.petclinic.insurance;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = InsuranceController.class,		
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class Test7 {
    @MockBean
    InsuranceService ts;
    
    @MockBean
    PetService ps;
    

    @Autowired
    private MockMvc mockMvc;

    public static String A_Insurance_NAME ="New Insurance name";
    public static Double A_Insurance_COST =12.0;


    @Test
    @WithMockUser(value = "spring", authorities = {"admin"})    
    public void test7() throws Exception{  
        testInsuranceCreationControllerOK();
        testInsuranceCreationControllerInvalid();                            
    }


    private void testInsuranceCreationControllerInvalid() throws Exception {                
        Insurance t=createInsurance();
        reset(ts);        
        ObjectMapper objectMapper = new ObjectMapper();        
        // Test with invalid cost
        t.setPrice(-1.0);
        String json = objectMapper.writeValueAsString(t);
        t.setPrice(A_Insurance_COST);
        mockMvc.perform(post("/api/v1/insurances")                            
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)                            
                            .content(json))
                .andExpect(status().isBadRequest());                
        verify(ts,never()).save(any(Insurance.class));
        // Test with invalid name
        t.setName("");
        json = objectMapper.writeValueAsString(t);
        t.setName(A_Insurance_NAME);
        mockMvc.perform(post("/api/v1/insurances")                            
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)                            
                            .content(json))
                .andExpect(status().isBadRequest());                
        verify(ts,never()).save(any(Insurance.class));
    }


    private void testInsuranceCreationControllerOK() throws Exception {
        Insurance t=createInsurance();        
        reset(ts);        
        
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(t);
        when(ts.save(any(Insurance.class))).thenReturn(t);
        mockMvc.perform(post("/api/v1/insurances")     
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)                            
                            .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(t.getName())))
				.andExpect(jsonPath("$.price", is(t.getPrice())));
        verify(ts).save(any(Insurance.class));                
    }
    
    public static Insurance createInsurance(){
        Insurance i=new Insurance();                
        i.setName(A_Insurance_NAME);
        i.setPrice(A_Insurance_COST);
        i.setPets(new ArrayList<>());
        return i;
    }
}
