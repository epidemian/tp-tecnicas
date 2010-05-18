package model.lab;

import model.core.BusinessLogicException;

public class ResearchLab {

	TechnologyTree technologyTree;
	int maxFunding;
	int funding;
	int accumulatedFunds;
	Technology onResearch;
	Technology researchTarget;

	public ResearchLab(TechnologyTree technologyTree, int maxFunding) {
		super();
		setTechnologyTree(technologyTree);
		setMaxFunding(maxFunding);
		setFunding(0);
	}
	
	public void startResearching(Technology technology) {
		if (technology.isResearched())
			throw new BusinessLogicException("Technology already researched");
		if (getTechnologyTree().getTechnologies().contains(technology))
			throw new BusinessLogicException("Invalid parameter");
	}
	
	public void update() {
		
	}

	public int getMaxFunding() {
		return maxFunding;
	}

	public int getFunding() {
		return funding;
	}
	
	public void setFunding(int funding) {
		if (funding < 0 || maxFunding < funding)
			throw new BusinessLogicException("Daily funding must be in the "
					+ "range [0, getMaxDailyFunding()]");
		this.funding = funding;
	}

	private TechnologyTree getTechnologyTree() {
		return technologyTree;
	}

	private void setTechnologyTree(TechnologyTree technologyTree) {
		if (technologyTree == null)
			throw new BusinessLogicException();
		this.technologyTree = technologyTree;
	}

	private void setMaxFunding(int maxFunding) {
		if (maxFunding <= 0)
			throw new BusinessLogicException("Max daily funding must be "
					+ "greater than zero");
		this.maxFunding = maxFunding;
	}

}
