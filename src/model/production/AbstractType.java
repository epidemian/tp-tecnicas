package model.production;

import static model.core.ArgumentUtils.checkNotNull;

abstract public class AbstractType {

	private String name;
	
	public AbstractType(String name){
		this.setName(name);
	}
	
	public boolean equals(Object other){
		return this.name.equals(((AbstractType)other).name);
	}
	
	public String getName() {
		return name;
	}
		
	private void setName(String name) {
		checkNotNull(name, "name");
		this.name = name;
	}
}
