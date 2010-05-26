package model.production;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ValidProductionSequencesTest {

	ValidProductionSequences validSequences;
	
	ProductionSequence sequence1;
	ProductionSequence sequence2;
	ProductionSequence sequence3;
	
	ProductType type1;
	ProductType type2;
	ProductType type3;
	
	MachineType machine1;
	MachineType machine2;
	MachineType machine3;
	MachineType machine4;
	MachineType machine5;
	MachineType machine6;
	
	RawMaterials rawMaterials1;
	RawMaterials rawMaterials2;
	
	@Before
	public void setUp() {
		this.validSequences = ValidProductionSequences.getInstance();
		
		machine1 = new MachineType("m1");
		machine2 = new MachineType("m2");
		machine3 = new MachineType("m3");
		machine4 = new MachineType("m4");
		machine5 = new MachineType("m5");
		machine6 = new MachineType("m6");		
		
		rawMaterials1 = new RawMaterials();
		rawMaterials2 = new RawMaterials();
		rawMaterials2.store(new RawMaterialType("mat1"), 10);
		
		
		sequence1 = new ProductionSequence(rawMaterials1);
		type1 = new ProductType("prod1");
		
		sequence1.addMachineType(machine1);
		sequence1.addMachineType(machine2);
		sequence1.addMachineType(machine3);
		
		this.validSequences.addValidProductionSequence(sequence1,type1);
		
		sequence2 = new ProductionSequence(rawMaterials1);
		type2 = new ProductType("prod2");
		
		sequence2.addMachineType(machine4);
		sequence2.addMachineType(machine5);
		sequence2.addMachineType(machine6);
		
		this.validSequences.addValidProductionSequence(sequence2,type2);
		
		RawMaterials rawMaterials = new RawMaterials();
		
		sequence3 = new ProductionSequence(rawMaterials);
		type3 = new ProductType("prod3");
		
		sequence3.addMachineType(machine1);
		sequence3.addMachineType(machine2);
		sequence3.addMachineType(machine3);
				
		this.validSequences.addValidProductionSequence(sequence3,type3);
	}

	@Test
	public void 
	identifyProductTypeOfAProductionSequenceWithNotRepeatedSequenceOfMachines(){
	
		ProductType type2 = this.validSequences.identifyProductType(sequence2);
		assertTrue(type2.equals(this.type2));
	}
	
	@Test
	public void
	identifyProductTypeOfAProductionSequenceWithRepeatedSequenceOfMachines(){
	
		ProductType type1 = this.validSequences.identifyProductType(sequence1);
		assertTrue(type1.equals(this.type1));
		
		ProductType type3 = this.validSequences.identifyProductType(sequence3);
		assertTrue(type3.equals(this.type3));
	}
		
	@Test
	public void	
	identifyProductTypeOfANonValidProductionSequenceExpectionWaste(){
	
		ProductType waste = ProductType.createWaste();
		
		ProductType type = this.validSequences.identifyProductType(null);
		assertTrue(type.equals(waste));
	}
	
	@Test
	public void addValidProductionSequenceAndAnalizeThatItIsNotEmpty(){
		
		this.validSequences.clear();
		
		ProductionSequence s1 = new ProductionSequence(new RawMaterials());
		ProductType p1 = new ProductType("lalal");
		
		s1.addMachineType(new MachineType("m1"));
		s1.addMachineType(new MachineType("m2"));
		s1.addMachineType(new MachineType("m3"));
		
		this.validSequences.addValidProductionSequence(s1,p1);
		
		assertFalse(this.validSequences.isEmpty());
	}
	
	@After
	public void tearDown(){
		this.validSequences.clear();
	}		
}
