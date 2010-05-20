package model.lab;

import static model.core.ArgumentUtils.*;

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
		checkArgCondition(technology, !technology.isResearched(),
				"already researched");
		checkArgCondition(technology,
				!getTechnologyTree().getTechnologies().contains(technology),
				"not contained on technology tree");
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
		checkInRange(funding, 0, getMaxFunding(), "funding");
		this.funding = funding;
	}

	private TechnologyTree getTechnologyTree() {
		return technologyTree;
	}

	private void setTechnologyTree(TechnologyTree technologyTree) {
		checkNotNull(technologyTree, "technology tree");
		this.technologyTree = technologyTree;
	}

	private void setMaxFunding(int maxFunding) {
		checkGreaterThan(maxFunding, 0, "max funding");
		this.maxFunding = maxFunding;
	}

}
