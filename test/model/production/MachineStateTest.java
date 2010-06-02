package model.production;

import static org.junit.Assert.*;

import model.exception.BusinessLogicException;

import org.junit.Before;
import org.junit.Test;

public class MachineStateTest {
	private MachineMock machine;
	private ProductionLine line;

	@Before
	public void setUp(){
		
		
		machine=new MachineMock(new MachineType("TestingMachine"), null,null);
		line=ProductionLine.createValidProductionLine(machine, 
				new StorageArea(new RawMaterials(),new ValidProductionSequences()),
				new RawMaterials());
		machine.setProductionLineElementObserver(line);
	}

	@Test (expected = CannotRepairHealthyMachineException.class) 
	public void repairAlreadyHealthyMachine() 
		throws CannotRepairHealthyMachineException{
		
		machine.setMachineState(new HealthyMachineState());
		machine.repair();
	}
	
	@Test
	public void repairDamagedMachine()
		throws CannotRepairHealthyMachineException{
		
		machine.setMachineState(new DamagedMachineState());
		machine.repair();
		
		assertEquals(machine.getMachineState(), new HealthyMachineState());
	}	
	
	@Test
	public void repairBrokenMachine()
		throws CannotRepairHealthyMachineException{
		
		machine.setMachineState(new BrokenMachineState());
		machine.repair();
		
		assertEquals(machine.getMachineState(), new HealthyMachineState());
	}	
	
	@Test
	public void breakHealthyMachine(){
		machine.setMachineState(new HealthyMachineState());
		machine.breakMachine();
		assertEquals(machine.getMachineState(), new BrokenMachineState());
		
	}
	
	/** In order to be able to avoid probability
	 * 
	 */
	private class MachineMock extends ProductionMachine{
	
		private MachineMock(MachineType machineType, 
				ProductionLineElement next, ProductionLineElement previous){
			super( machineType, 
					 next, previous);
		}
		
		public void breakMachine(){
			super.breakMachine();
		}
		
		public void damageMachine(){
			super.damageMachine();
		}
	}

}
