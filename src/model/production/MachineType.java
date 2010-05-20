package model.production;

import model.core.BusinessLogicException;

public class MachineType {

	private String name;
	
	public MachineType(String name){
		this.setName(name);
	}
	
	public boolean equals(Object other){
		return this.name.equals(((MachineType)other).name);
	}
	
	public String getName() {
		return name;
	}
	
	private void setName(String name) {
		if (name == null)
			throw new BusinessLogicException("Invalid name");
		this.name = name;
	}
}
