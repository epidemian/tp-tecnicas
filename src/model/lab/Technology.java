package model.lab;

import model.exception.BusinessLogicException;
import static model.utils.ArgumentUtils.*;

/**
 * An abstract representation of a technology that can be researched.
 */
public abstract class Technology {
	
	private String name;
	private String description;
	private int researchCost;
	private boolean researched;
	
	public Technology(String name, String description, int researchCost,
			boolean researched) {
		super();
		setName(name);
		setDescription(description);
		setResearchCost(researchCost);
		setResearched(researched);
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
	// Note: package visibility and final modifier are on purpose.
	final void research() {
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
