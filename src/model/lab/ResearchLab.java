package model.lab;

import model.core.BusinessLogicException;

public class ResearchLab {

	TechnologyTree technologyTree;
	final int maxFunding;
	int dailyFunding;
	
	public ResearchLab(TechnologyTree technologyTree, int maxFunding) {
		super();
		if (technologyTree == null || maxFunding <= 0)
			throw new BusinessLogicException("Invalid parameter");
		
		this.technologyTree = technologyTree;
		this.maxFunding = maxFunding;
	}
	
	
}
