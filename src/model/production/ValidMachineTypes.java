package model.production;
import java.util.ArrayList;
import java.util.List;

import model.production.elements.machine.MachineType;

public class ValidMachineTypes {

	List<MachineType> machineList;
	
	public ValidMachineTypes(List<MachineType> list){
		this.machineList=list;
	}
	
	public ValidMachineTypes(){
		this.machineList=new ArrayList<MachineType>();
	}
	
	public void add(MachineType machineType){
		this.machineList.add(machineType);
	}
	
}
