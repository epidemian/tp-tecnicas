package model.production;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
	private Collection<ProductionLine> putProductionLineElements(Ground ground){
		
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
				
		Collection<ProductionLine> linesCreated = new ArrayList<ProductionLine>();
		linesCreated.add(line);
		linesCreated.add(line2);
		linesCreated.add(line3);
				
		return linesCreated;
	}
	
	/*
	 * This is only used in this test. It is not a real equals =).
	 */
	private static boolean equalsElement(ProductionLineElement element1,
			ProductionLineElement element2){
		
		String toStringElement1 = element1.toString();
		String toStringElement2 = element2.toString();
		
		return toStringElement1.equals(toStringElement2);		
	}
		
	private static boolean equalsLine(ProductionLine line1,
			ProductionLine line2){
		
		if (line1.productionLineSize() != line2.productionLineSize())
			return false;
		
		Iterator<ProductionLineElement> line1Iterator = line1.iterator();
		Iterator<ProductionLineElement> line2Iterator = line2.iterator();	
		
		while (line1Iterator.hasNext()){
			if (!ProductionLinesCreatorTest.equalsElement(line1Iterator.next()
				, line2Iterator.next()))
				return false;			
		}
		
		return true;
	}
	
	/*
	 * Analyze if the elements in prodLinesColl1 are also in prodLinesColl2.
	 */
	private static boolean contains(Collection<ProductionLine> prodLinesColl1,
		Collection<ProductionLine> prodLinesColl2){
		
		boolean contains = true;
		
		Iterator<ProductionLine> coll1Iterator = prodLinesColl1.iterator();
		while (coll1Iterator.hasNext() && contains){
			
			ProductionLine line1 = coll1Iterator.next();
			Iterator<ProductionLine> coll2Iterator = prodLinesColl2.iterator();
			contains = false;
			while (coll2Iterator.hasNext() && !contains){
				ProductionLine line2 = coll2Iterator.next();
				contains = ProductionLinesCreatorTest.equalsLine(line1, line2);
			}			
		}
		
		return contains;		
	}
	
	@Test 
	public void createFromGroundTest(){

		/*
		 * Context.
		 */
		Collection<ProductionLine> linesCreated = this
			.putProductionLineElements(this.ground);
		
		Collection<ProductionLine> linesGround = this.linesCreator
			.createFromGround(ground);
		
		assertTrue(ProductionLinesCreatorTest.contains(
				linesGround,linesCreated));
	}
}
