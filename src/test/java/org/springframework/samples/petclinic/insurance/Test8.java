package org.springframework.samples.petclinic.insurance;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.arrayContaining;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.security.access.method.P;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;

@WebMvcTest(value = InsuranceController.class,		
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class Test8 {
    @MockBean
	InsuranceService is;	    	
	@MockBean
	PetService ps;	    	


    @Autowired
	private MockMvc mockMvc;

    @WithMockUser(value = "spring")
    @Test
	void test8() throws Exception {			
        testGetValidInsurance();
		testGetInsuranceNotFound();
	}


    private void testGetInsuranceNotFound() throws Exception {
		reset(is);
		Integer insuranceId=1;
		when(is.getInsurance(any(Integer.class))).thenReturn(null);
		mockMvc.perform(get("/api/v1/insurances/"+insuranceId))
			.andExpect(status().isNotFound());		
		verify(is).getInsurance(insuranceId);
	}

	private void testGetValidInsurance() throws Exception {
		reset(is);
		Integer insuranceId=1;
		Insurance t=createValidInsurance();		
		when(is.getInsurance(insuranceId)).thenReturn(t);

		mockMvc.perform(get("/api/v1/insurances/"+insuranceId))
				.andExpect(status().isOk())				
				.andExpect(jsonPath("$.name", is(t.getName())))
				.andExpect(jsonPath("$.price", is(t.getPrice())))
				.andExpect(jsonPath("$.pets[0]", is(t.getPets().get(0).getName())))		
				.andExpect(jsonPath("$.pets[1]", is(t.getPets().get(1).getName())));		
		verify(is).getInsurance(insuranceId);
	}

    private Insurance createValidInsurance() {
        Insurance t=new Insurance();
        t.setName("A valid Insurance Name");
        t.setPrice(50.0);		
		Pet p = new Pet();
		p.setName("A valid pet name");
		Pet p2 = new Pet();
		p2.setName("Another valid pet name");		
		List<Pet> pets = List.of(p,p2);
		t.setPets(pets);
       	return t;
    }
}