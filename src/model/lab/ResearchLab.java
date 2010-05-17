package model.lab;

import model.core.BusinessLogicException;

public class ResearchLab {

	TechnologyTree technologyTree;
	int maxDailyFunding;
	int dailyFunding;

	public ResearchLab(TechnologyTree technologyTree, int maxDailyFunding) {
		super();
		setTechnologyTree(technologyTree);
		setMaxDailyFunding(maxDailyFunding);
		setDailyFunding(0);
	}

	public int getMaxDailyFunding() {
		return maxDailyFunding;
	}

	public int getDailyFunding() {
		return dailyFunding;
	}
	
	public void setDailyFunding(int dailyFunding) {
		if (dailyFunding < 0 || maxDailyFunding < dailyFunding)
			throw new BusinessLogicException("Daily funding must be in the "
					+ "range [0, getMaxDailyFunding()]");
		this.dailyFunding = dailyFunding;
	}

	private TechnologyTree getTechnologyTree() {
		return technologyTree;
	}

	private void setTechnologyTree(TechnologyTree technologyTree) {
		if (technologyTree == null)
			throw new BusinessLogicException();
		this.technologyTree = technologyTree;
	}

	private void setMaxDailyFunding(int maxDailyFunding) {
		if (maxDailyFunding <= 0)
			throw new BusinessLogicException("Max daily funding must be "
					+ "greater than zero");
		this.maxDailyFunding = maxDailyFunding;
	}

}
