package org.springframework.samples.petclinic.insurance;

import static org.hamcrest.Matchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Test9 {
    
    @MockBean
	InsuranceService is;
    @MockBean
	PetService ps;

    @Autowired
    private WebApplicationContext context;        
    private MockMvc mockMvc;
    
    private static final Integer A_INSURANCE_ID = 1;        

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }

    @WithMockUser(value = "spring", authorities = {"admin"})
    @Test
    void test9()  throws Exception {
        testInsuranceUpdateControllerOK();
        testInsuranceUpdateControllerInvalid();        
    }

    void testInsuranceUpdateControllerOK() throws Exception{
        Insurance i=Test7.createInsurance();        
        i.setPrice(1000.0);
        reset(is);
        when(is.getInsurance(A_INSURANCE_ID)).thenReturn(i);
        when(is.getAll()).thenReturn(List.of(i));
        when(ps.getPetByName(any(String.class))).thenReturn(new Pet());
        when(ps.savePet(any(Pet.class))).thenReturn(new Pet());

        ObjectMapper objectMapper = new ObjectMapper();

        String json = "{"                                   + //
                        "\"id\": 1,"                          + //
                        "\"name\": \"New Insurance name\","   + //
                        "\"price\": 1000.0,"                      + //
                        "\"pets\": [\"Rosy\"]"                    + //
                      "}";


        mockMvc.perform(put("/api/v1/insurances/"+A_INSURANCE_ID)                            
                            .contentType(MediaType.APPLICATION_JSON)                            
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
        verify(is).save(any(Insurance.class));    
    }

    void testInsuranceUpdateControllerInvalid() throws Exception{
        Insurance i=Test7.createInsurance();        
        i.setPrice(1000.0);
        reset(is);        
        // WE SIMULATE THAT INSURANCE WITH ID 1 DOES NOT EXIST:
        when(is.getInsurance(A_INSURANCE_ID)).thenReturn(null);
        when(is.getAll()).thenReturn(List.of());
        when(ps.getPetByName(any(String.class))).thenReturn(new Pet());
        when(ps.savePet(any(Pet.class))).thenReturn(new Pet());
        
        String json = "{"                                   + //
                        "\"id\": 1,"                          + //
                        "\"name\": \"New Insurance name\","   + //
                        "\"price\": 1000.0,"                      + //
                        "\"pets\": [\"Rosy\"]"                    + //
                      "}";


        mockMvc.perform(put("/api/v1/insurances/"+A_INSURANCE_ID)                                                        
                            .contentType(MediaType.APPLICATION_JSON)                            
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());        
        verify(is,times(0)).save(any(Insurance.class));
        
        when(is.getInsurance(A_INSURANCE_ID)).thenReturn(i);
        json = "{"                                   + //
                        "\"id\": 1,"                          + //
                        "\"name\": \"New Insurance name\","   + //
                        "\"price\": -10.0,"                      + //
                        "\"pets\": [\"Rosy\"]"                    + //
                      "}";
        mockMvc.perform(put("/api/v1/insurances/"+A_INSURANCE_ID)                            
                            .contentType(MediaType.APPLICATION_JSON)                            
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());        
        verify(is,times(0)).save(any(Insurance.class));

    }		
	    
}


