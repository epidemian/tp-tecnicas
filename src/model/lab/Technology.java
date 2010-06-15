package model.lab;

import static model.utils.ArgumentUtils.*;
import model.exception.BusinessLogicException;

/**
 * An abstract representation of a technology that can be researched.
 */
public abstract class Technology {
	
	private String name;
	private String description;
	private int researchCost;
	private boolean researched;
	
	public Technology(String name, String description, int researchCost) {
		super();
		setName(name);
		setDescription(description);
		setResearchCost(researchCost);
		setResearched(false);
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getResearchCost() {
		return researchCost;
	}

	public boolean isResearched() {
		return researched;
	}

	/**
	 * Researches the technology.
	 * @throws BusinessLogicException If technology was already researched.
	 */
	public final void research() {
		if (isResearched())
			throw new BusinessLogicException("Tecnology already researched");
		onResearch();
		setResearched(true);
	}

	/**
	 * Called by {@link Technology#research()}.
	 * Should be implemented by {@link Technology} subclasses.  
	 */
	// TODO Maybe a better name would be apply() ?
	protected abstract void onResearch();

	private void setName(String name) {
		checkNotNull(name, "name");
		this.name = name;
	}

	private void setDescription(String description) {
		checkNotNull(description, "description");
		this.description = description;
	}

	private void setResearchCost(int researchCost) {
		checkGreaterEqual(researchCost, 0, "research cost");
		this.researchCost = researchCost;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + researchCost;
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
		Technology other = (Technology) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (researchCost != other.researchCost)
			return false;
		return true;
	}
	
	private void setResearched(boolean researched) {
		this.researched = researched;
	}

	@Override
	public String toString() {
		return "Technology [name=" + name + ", description=" + description
				+ ", researchCost=" + researchCost + ", researched="
				+ researched + "]";
	}
	


}
