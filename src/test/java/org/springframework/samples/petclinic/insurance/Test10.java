package org.springframework.samples.petclinic.insurance;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.samples.petclinic.pet.PetService;

public class Test10 extends ReflexiveTest {
	InsuranceRepository ir;

	InsuranceService is;
	PetService ps;

	public static Integer A_INSURANCE_ID = 1;

	@BeforeEach
	void createService() {
		ir = mock(InsuranceRepository.class);
		ps = mock(PetService.class);
		is = new InsuranceService(ir,ps);
	}

	@Test
	public void testUpdates() {
		int data[][] = {
				{ 1, 1, 0 },
				{ 2, 1, 0 },
				{ 2, 2, 0 },
				{ 2, 3, 0 },
				{ 2, 4, 1 },
				{ 2, 5, 1 },
				{ 5, 20, 1 }
		};
		boolean unfeasible=false;
		for (int[] mydata : data) {
			Insurance newInsurance = Test7.createInsurance();
			Insurance oldInsurance = Test7.createInsurance();
			Class[] paramTypes = { Integer.class };
			if (classHasMethod(oldInsurance, "setId", paramTypes)) {
				invokeMethodReflexivelyWithParamTypes(oldInsurance, "setId", paramTypes, 5);
				invokeMethodReflexivelyWithParamTypes(newInsurance, "setId", paramTypes, 5);
			}
			newInsurance.setPrice((double) mydata[1]);
			oldInsurance.setPrice((double) mydata[0]);
			unfeasible= mydata[2]==1;
			if (unfeasible)
				testUpdateUnfeasible(oldInsurance, newInsurance);
			else
				testUpdateOk(oldInsurance, newInsurance);
		}
	}

	private void testUpdateUnfeasible(Insurance oldInsurance, Insurance newInsurance) {
		reset(ir);
		when(ir.findById(any(Integer.class))).thenReturn(Optional.of(oldInsurance));
		assertThrows(UnfeaseibleInsuranceModificationException.class, () -> is.save(newInsurance));
	}

	private void testUpdateOk(Insurance oldInsurance, Insurance newInsurance) {
		reset(ir);
		when(ir.findById(any(Integer.class))).thenReturn(Optional.of(oldInsurance));
		try {
			is.save(newInsurance);
			verify(ir,times(1)).save(newInsurance);
		} catch (UnfeaseibleInsuranceModificationException e) {
			fail("No exception should be thrown! " + e.getStackTrace());
		}

	}
}