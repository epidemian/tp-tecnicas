package model.production;

import static org.junit.Assert.*;


import java.util.ArrayList;

import org.junit.Test;

public class ProductionSequenceTest {
	
	//ProductionSequence productionSequence;
	
	@Test
	public void equalsProductionSequenceCorrect(){
		MachineType machine1a=new MachineType("Horno");
		MachineType machine2a=new MachineType("Licudora");
		MachineType machine3a=new MachineType("Haz de Plasma");
		
		MachineType machine1b=new MachineType("Horno");
		MachineType machine2b=new MachineType("Licudora");
		MachineType machine3b=new MachineType("Haz de Plasma");

		ArrayList<MachineType> lineMachinesA=new ArrayList<MachineType>();
		lineMachinesA.add(machine1a);
		lineMachinesA.add(machine2a);
		lineMachinesA.add(machine3a);
		
		ArrayList<MachineType> lineMachinesB=new ArrayList<MachineType>();;
		lineMachinesB.add(machine1b);
		lineMachinesB.add(machine2b);
		lineMachinesB.add(machine3b);
		
		ProductionSequence prodSeqA=new ProductionSequence(lineMachinesA);
		ProductionSequence prodSeqB=new ProductionSequence(lineMachinesB);
		
		boolean isEquals=prodSeqA.equals(prodSeqB);
		assertTrue(isEquals);
	}
	
	@Test
	public void equalsProductionSequenceDifferentQuantities(){
		MachineType machine1a=new MachineType("Horno");
		MachineType machine2a=new MachineType("Licudora");
		MachineType machine3a=new MachineType("Haz de Plasma");
		
		MachineType machine1b=new MachineType("Horno");
		MachineType machine2b=new MachineType("Licudora");
		
		ArrayList<MachineType> lineMachinesA=new ArrayList<MachineType>();
		lineMachinesA.add(machine1a);
		lineMachinesA.add(machine2a);
		lineMachinesA.add(machine3a);
		
		ArrayList<MachineType> lineMachinesB=new ArrayList<MachineType>();;
		lineMachinesB.add(machine1b);
		lineMachinesB.add(machine2b);
		
		ProductionSequence prodSeqA=new ProductionSequence(lineMachinesA);
		ProductionSequence prodSeqB=new ProductionSequence(lineMachinesB);
		
		boolean isEquals=prodSeqA.equals(prodSeqB);
		assertFalse(isEquals);
	}
	
	@Test
	public void equalsProductionSequenceDifferentMachines(){
		MachineType machine1a=new MachineType("Horno");
		MachineType machine2a=new MachineType("Licuadora");
		MachineType machine3a=new MachineType("Haz de Plasma");
		
		MachineType machine1b=new MachineType("Torno");
		MachineType machine2b=new MachineType("Licudora");
		MachineType machine3b=new MachineType("Haz de Plasma");

		ArrayList<MachineType> lineMachinesA=new ArrayList<MachineType>();
		lineMachinesA.add(machine1a);
		lineMachinesA.add(machine2a);
		lineMachinesA.add(machine3a);
		
		ArrayList<MachineType> lineMachinesB=new ArrayList<MachineType>();;
		lineMachinesB.add(machine1b);
		lineMachinesB.add(machine2b);
		lineMachinesB.add(machine3b);
		
		ProductionSequence prodSeqA=new ProductionSequence(lineMachinesA);
		ProductionSequence prodSeqB=new ProductionSequence(lineMachinesB);
		
		boolean isEquals=prodSeqA.equals(prodSeqB);
		assertFalse(isEquals);
	}
}
