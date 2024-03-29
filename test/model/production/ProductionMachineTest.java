package model.production;

import static model.production.TestUtils.createMachineType;
import static org.junit.Assert.*;
import model.production.elements.machine.Machine;
import model.production.elements.machine.ProductionMachine;
import model.utils.ConfigMock;

import org.junit.Ignore;
import org.junit.Test;

public class ProductionMachineTest {

	@Test
	public void processTwoProducts() {

		Product product1 = new Product(new RawMaterials());
		Product product2 = new Product(new RawMaterials());

		Machine machine = new ProductionMachine(createMachineType("m1",1,1),new ConfigMock());

		Product productProcessed1 = machine.process(product1);
		assertNull(productProcessed1);
		Product productProcessed2 = machine.process(product2);
		assertNotNull(productProcessed2);
		Product productProcessed3 = machine.process(null);
		assertNotNull(productProcessed3);

		ProductionSequence history1 = productProcessed2.getHistory();
		ProductionSequence history2 = productProcessed3.getHistory();

		ProductionSequence historyExpected1 = new ProductionSequence(
				new RawMaterials());
		historyExpected1.addMachineType(createMachineType("m1"));

		assertEquals(historyExpected1, history1);

		ProductionSequence historyExpected2 = new ProductionSequence(
				new RawMaterials());
		historyExpected2.addMachineType(createMachineType("m1"));

		assertEquals(historyExpected2, history2);
	}
}
