package model.production;

import static org.junit.Assert.*;

import java.util.LinkedList;

import model.exception.BusinessLogicException;
import model.production.elements.machine.MachineType;

import org.junit.Test;

public class ProductionSequenceTest {

	@Test (expected = BusinessLogicException.class)
	public void createProductionSequenceWithNullMachines(){
		new ProductionSequence(null, new RawMaterials());
	}
	
	@Test (expected = BusinessLogicException.class)
	public void createProductionSequenceWithNullRawMaterials(){
		new ProductionSequence(new LinkedList<MachineType>(), null);
	}	
	
	@Test (expected = BusinessLogicException.class)
	public void addNullMachinesType(){
		
		ProductionSequence sequence 
			= new ProductionSequence(new RawMaterials());
		sequence.addMachineType(null);
	}
	
	@Test
	public void equalsTestExpectionTrue(){
		
		ProductionSequence sequence1 = TestUtils
			.createProductionSequence(50,4,20,4,10);
	
		ProductionSequence sequence2 = TestUtils
			.createProductionSequence(50,4,20,4,10);

		assertEquals(sequence1, sequence2);		
	}

	@Test
	public void equalsTestExpectionFalseWithDifferentRawMaterials(){
		
		ProductionSequence sequence1 = TestUtils
			.createProductionSequence(50,4,10,4,10);
	
		ProductionSequence sequence2 = TestUtils
			.createProductionSequence(50,4,20,4,10);

		assertNotSame(sequence1, sequence2);		
	}
	
	@Test
	public void equalsTestExpectionFalseWithDifferentMachines(){
		
		ProductionSequence sequence1 = TestUtils
			.createProductionSequence(50,0,20,4,10);
	
		ProductionSequence sequence2 = TestUtils
			.createProductionSequence(50,4,20,4,10);

		assertNotSame(sequence1, sequence2);		
	}

	@Test
	public void equalsTestExpectionFalseWithDifferentRawMaterialQuantity(){
		
		ProductionSequence sequence1 = TestUtils
			.createProductionSequence(50,4,20,4,10);
	
		ProductionSequence sequence2 = TestUtils
			.createProductionSequence(50,4,20,4,90);

		assertNotSame(sequence1, sequence2);		
	}
	
	@Test 
	public void identifyProduct(){

		ValidProductionSequences validSequences 
			= new ValidProductionSequences();
		ProductType type = new ProductType("prod1");
		
		validSequences.addValidProductionSequence(TestUtils
			.createProductionSequence(3,0,2,0,40),type);
		
		ProductionSequence sequence = new ProductionSequence(
				TestUtils.createMachineTypeList(3,0), 
				TestUtils.createRawMaterials(2,0,40));

		assertEquals(type, sequence.identifyProductType(validSequences));
	}
}
