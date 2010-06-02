package model.production;

import static org.junit.Assert.*;

import model.exception.BusinessLogicException;

import org.junit.Before;
import org.junit.Test;

public class MachineStateTest {
	Machine machine;

	@Before
	public void setUp(){
		machine=new ProductionMachine(new MachineType("TestingMachine"), null,null);
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

}