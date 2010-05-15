package model.lab;

import model.core.BusinessLogicException;

/**
 * An abstract representation of a technology that can be researched.
 *
 */
public abstract class Technology {
	
	final private String name;
	final private String description;
	final private int cost;
	private boolean researched;
	
	public Technology(String name, String description, int cost,
			boolean researched) {
		super();
		if (name == null || description == null || cost < 0)
			throw new BusinessLogicException("Invalid parameters");
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.researched = researched;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getCost() {
		return cost;
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
		if (this.researched)
			throw new BusinessLogicException("Tecnology already researched");
		onResearch();
		this.researched = true;
	}

	protected abstract void onResearch();
	
}
