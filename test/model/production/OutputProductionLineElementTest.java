package model.production;

import static model.production.ProductionLineElement.connectLineElements;
import static org.junit.Assert.*;
import model.production.line.ProductionLine;

import org.junit.Before;
import org.junit.Test;

public class OutputProductionLineElementTest {
	
	StorageArea storageArea;
	
	@Before
	public void setUp(){
		storageArea=new StorageArea(new RawMaterials(),
				new ValidProductionSequences());
		
	}

	
	public ProductionLine createLineProcessingCarton(){
		ProductionLineElement prodLineElement1 = new ProductionMachine(
				new MachineType("Licuado",1,1));
		ProductionLineElement prodLineElement2 = new Conveyor();
		ProductionLineElement prodLineElement3 = new ProductionMachine(
				new MachineType("Horno",1,1));
		ProductionLineElement prodLineElement4 = 
				new OutputProductionLineElement();

		connectLineElements(prodLineElement1, prodLineElement2);
		connectLineElements(prodLineElement2, prodLineElement3);
		connectLineElements(prodLineElement3, prodLineElement4);

		return ProductionLine.createValidProductionLine(prodLineElement1,
				this.storageArea,new RawMaterials());
	}
	
	
	
	@Test
	public void RecieveProductionFromProductsLines(){
		ProductionLine line=this.createLineProcessingCarton();
		
		for(int i=0;i<5;i++){
			line.updateTick();
		}
	
		assertEquals(1,storageArea.getProductsProduced().size());
		
		
	}
}
