package model.production;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.warehouse.Ground;
import model.warehouse.ProductionLinesCreator;

public class ProductionLinesCreatorTest {

	private ProductionLinesCreator linesCreator;
	private Ground ground;
	private StorageArea storageArea;
	
	@Before
	public void setUp(){
		this.linesCreator = new ProductionLinesCreator(new StorageArea(
				new RawMaterials(), new ValidProductionSequences()));
	
		this.ground = new Ground(1000, 10, 10);
		this.storageArea = new StorageArea(new RawMaterials(), 
			new ValidProductionSequences());
		this.linesCreator = new ProductionLinesCreator(this.storageArea);
	}
	
	/*
	 * Generates context!
	 */
	private List<ProductionLine> putProductionLineElements(Ground ground){
		
		MachineType machineType1 = new MachineType("Licuado");
		MachineType machineType2 = new MachineType("Haz");
		MachineType machineType3 = new MachineType("Horno");
		MachineType machineType4 = new MachineType("Procesador");
		MachineType machineType5 = new MachineType("Super-Machine");
		
		/*
		 * Creates the first line.
		 */
		ProductionLineElement prodLineElement1 =
			new ProductionMachine(machineType1,null,null);
		
		ProductionLineElement prodLineElement2 =
			new Conveyor(null,prodLineElement1);
		prodLineElement1.setNextLineElement(prodLineElement2);
		
		ProductionLineElement prodLineElement3 =
			new ProductionMachine(machineType2,null,prodLineElement2);
		prodLineElement2.setNextLineElement(prodLineElement3);
		
		ProductionLineElement prodLineElement4 =
			new Conveyor(null,prodLineElement3);
		prodLineElement3.setNextLineElement(prodLineElement4);
		
		ProductionLineElement prodLineElement5 =
			new ProductionMachine(machineType3,null,prodLineElement4);
		prodLineElement4.setNextLineElement(prodLineElement5);
				
		ProductionLine line = ProductionLine.createValidProductionLine(
			prodLineElement1, this.storageArea, new RawMaterials());
		
		/*
		 * Creates the second line.
		 */
		ProductionLineElement prodLine2Element1 =
			new ProductionMachine(machineType4,null,null);
		
		ProductionLineElement prodLine2Element2 =
			new Conveyor(null,prodLine2Element1);
		prodLine2Element1.setNextLineElement(prodLine2Element2);
		
		ProductionLineElement prodLine2Element3 =
			new ProductionMachine(machineType3,null,prodLine2Element2);
		prodLine2Element2.setNextLineElement(prodLine2Element3);
		
		ProductionLine line2 = ProductionLine.createValidProductionLine(
			prodLine2Element1, this.storageArea, new RawMaterials());
		
		/*
		 * Creates the third line.
		 */
		
		ProductionLineElement prodLine3Element1 =
			new ProductionMachine(machineType5,null,null);
		
		ProductionLine line3 = ProductionLine.createValidProductionLine(
				prodLine3Element1, this.storageArea, new RawMaterials());
				
		/*
		 * Puts the lines in the ground.
		 */
		ground.getTile(3, 2).setTileElement(prodLineElement1);
		ground.getTile(2, 2).setTileElement(prodLineElement2);
		ground.getTile(2, 3).setTileElement(prodLineElement3);
		ground.getTile(3, 4).setTileElement(prodLineElement4);
		ground.getTile(4, 4).setTileElement(prodLineElement5);
		
		ground.getTile(5, 5).setTileElement(prodLine2Element1);
		ground.getTile(6, 5).setTileElement(prodLine2Element2);
		ground.getTile(6, 6).setTileElement(prodLine2Element3);
		
		/*
		 * Isolated machine.
		 */
		ground.getTile(8, 8).setTileElement(prodLine3Element1);
				
		List<ProductionLine> linesCreated = new ArrayList<ProductionLine>();
		linesCreated.add(line);
		linesCreated.add(line2);
		linesCreated.add(line3);
				
		return linesCreated;
	}
	
	@Test 
	public void createFromGroundTest(){

		/*
		 * Context.
		 */
		List<ProductionLine> linesExpected = this
			.putProductionLineElements(this.ground);
		
		List<ProductionLine> linesGround = this.linesCreator
			.createFromGround(ground);

		assertEquals(linesExpected.size(), linesGround.size());
		
		for (int i = 0; i < linesExpected.size(); i++)
			assertEquals(linesExpected.get(i).getLineElements(), linesGround.get(i).getLineElements());
	}
}
