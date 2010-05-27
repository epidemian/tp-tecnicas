package model.production;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ProductionLineTest {

	private ProductionLine productionLine;
	
	@Before
	public void setUp(){
		this.productionLine = this
			.createProductionLineProcesamientoDeCartones();
	}
	
	private ProductionLine createProductionLineProcesamientoDeCartones(){
	
		ProductionLineElement prodLineElement1 =
			new ProductionMachine(new MachineType("Licuado"),null,null);
		
		ProductionLineElement prodLineElement2 =
			new ProductionMachine(new MachineType("Haz"),null,prodLineElement1);
		prodLineElement1.setNextLineElement(prodLineElement2);
		
		ProductionLineElement prodLineElement3 = new ProductionMachine(
			new MachineType("Horno"),null,prodLineElement2);
		prodLineElement2.setNextLineElement(prodLineElement3);
		
		return ProductionLine.createValidProductionLine(
			prodLineElement1, new StorageArea(new RawMaterials(),
			new ValidProductionSequences()), new  RawMaterials());
	}
	
	@Test
	public void equalsTest(){
			
		ProductionLine prodLineEquals = this
			.createProductionLineProcesamientoDeCartones();
							
		assertEquals(prodLineEquals, this.productionLine);	
	}
	
	@Test
	public void dailyProduction(){
	
		int ticksInADay = 500;
		
		for (int ticks = 0; ticks < ticksInADay; ticks++){
			this.productionLine.updateTick();
		}
		
		int dailyProduction = this.productionLine.getDailyProduction();
		
		assertEquals(dailyProduction,ticksInADay-
			this.productionLine.productionLineSize());
		
		this.productionLine.updateDay();
		
		List<Integer> dailyProductionList 
			= this.productionLine.getProductionHistory();
		
		assertEquals(dailyProductionList.get(dailyProductionList.size()-1)
			.intValue(),ticksInADay - this.productionLine.productionLineSize());
		
		assertEquals(0,this.productionLine.getDailyProduction());
	}	
}
