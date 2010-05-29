package model.warehouse;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

import model.game.Budget;
import model.production.MachineType;
import model.production.ProductionLine;
import model.production.ProductionLineElement;
import model.production.ProductionMachine;
import model.production.RawMaterials;
import model.production.StorageArea;
import model.production.ValidProductionSequences;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

public class WarehouseTest {
	private static final int INITIAL_BALANCE = 5000;
	private static final int PRICE_GROUND = 1000;
	private static final int PRICE_MACHINES = 0;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void sellPurcheaseWarehouseAndCheckBalance() {
		Budget budget = new Budget(INITIAL_BALANCE);
		Warehouse purchaseWarehouse = new PurchaseWarehouse(new Ground(PRICE_GROUND, 10, 10), budget); 
		
		purchaseWarehouse.sell();
		//TODO: Ojo que al m�todo sell le falta la parte de la venta de las m�quinas en buen estado
		assertEquals(budget.getBalance(), (int)(INITIAL_BALANCE + 0.8 * PRICE_GROUND + 0.5 * PRICE_MACHINES));
	}
	
	@Test
	public void sellRentWarehouseAndCheckBalance() {
		Budget budget = new Budget(INITIAL_BALANCE);
		Warehouse rentWarehouse = new RentedWarehouse(new Ground(PRICE_GROUND, 10, 10), budget); 
		
		rentWarehouse.sell();
		//TODO: Ojo que al m�todo sell le falta la parte de la venta de las m�quinas en buen estado
		assertEquals(budget.getBalance(), (int)(INITIAL_BALANCE + 0.5 * PRICE_MACHINES));
	}	
	
	@Test
	public void updateMonthAndCheckBalance() {
		Budget budget = new Budget(INITIAL_BALANCE);
		Warehouse rentWarehouse = new RentedWarehouse(new Ground(PRICE_GROUND, 10, 10), budget); 
		
		rentWarehouse.updateMonth();
		assertEquals(budget.getBalance(), (int)(INITIAL_BALANCE - PRICE_GROUND));
	}
	
	@Test @Ignore
	public void parseValidProductionLine(){
		
		MachineType machineType1 = new MachineType("Licuado");
		MachineType machineType2 = new MachineType("Haz");
		MachineType machineType3 = new MachineType("Horno");
		
		ProductionLineElement prodLineElement1=
			new ProductionMachine(machineType1,null,null);
		
		ProductionLineElement prodLineElement2=
			new ProductionMachine(machineType2,null,prodLineElement1);
		prodLineElement1.setNextLineElement(prodLineElement2);
		
		ProductionLineElement prodLineElement3=
			new ProductionMachine(machineType3,null,prodLineElement2);
		prodLineElement2.setNextLineElement(prodLineElement3);
		
		Ground ground = new Ground(0,10,10);
		ground.getTile(2, 2).setTileElement(prodLineElement1);
		ground.getTile(2, 4).setTileElement(prodLineElement2);
		ground.getTile(2, 6).setTileElement(prodLineElement3);
		
		Warehouse warehouse = new PurchaseWarehouse(ground,new Budget(1000));
		warehouse.createProductionLines();
		
		ProductionLine prodLine=ProductionLine.createValidProductionLine(
			prodLineElement1, new StorageArea(new RawMaterials(),
			new ValidProductionSequences()), new  RawMaterials());

		Collection<ProductionLine> prodLinesCreated = warehouse
				.getProductionLines();
		assertTrue(prodLinesCreated.contains(prodLine));
	}
}
