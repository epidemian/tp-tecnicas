package model.production;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProductionLineTest {

	@Test
	public void equalsTest(){
			
		ProductionLineElement prodLineElement1 =
			new ProductionMachine(new MachineType("Licuado"),null,null);
		
		ProductionLineElement prodLineElement2 =
			new ProductionMachine(new MachineType("Haz"),null,prodLineElement1);
		prodLineElement1.setNextLineElement(prodLineElement2);
		
		ProductionLineElement prodLineElement3 = new ProductionMachine(
			new MachineType("Horno"),null,prodLineElement2);
		prodLineElement2.setNextLineElement(prodLineElement3);
		
		ProductionLine prodLine = ProductionLine.createValidProductionLine(
				prodLineElement1, new StorageArea(new RawMaterials(),
					new ValidProductionSequences()), new  RawMaterials());
		
		ProductionLineElement prodLineElement1Equals =
			new ProductionMachine(new MachineType("Licuado"),null,null);
		
		ProductionLineElement prodLineElement2Equals =
			new ProductionMachine(new MachineType("Haz")
			,null,prodLineElement1Equals);
		prodLineElement1Equals.setNextLineElement(prodLineElement2Equals);
		
		ProductionLineElement prodLineElement3Equals = new ProductionMachine(
			new MachineType("Horno"),null,prodLineElement2Equals);
		prodLineElement2Equals.setNextLineElement(prodLineElement3Equals);
		
		ProductionLine prodLineEquals = ProductionLine.createValidProductionLine(
				prodLineElement1Equals, new StorageArea(new RawMaterials(),
					new ValidProductionSequences()), new  RawMaterials());
				
		assertEquals(prodLineEquals, prodLine);	
	}
	
	
	
}
