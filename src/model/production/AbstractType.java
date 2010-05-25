package model.production;

import static model.utils.ArgumentUtils.checkNotNull;

abstract public class AbstractType {

	protected String name;
	
	public AbstractType(String name){
		this.setName(name);
	}
	
	protected void setName(String name) {
		checkNotNull(name, "name");
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractType other = (AbstractType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public String getName() {
		return name;
	}	
}
