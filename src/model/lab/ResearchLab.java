package model.lab;

import static model.utils.ArgumentUtils.checkArgCondition;
import static model.utils.ArgumentUtils.*;

import java.util.HashSet;
import java.util.Set;

public class ResearchLab {

	private TechnologyTree technologyTree;
	private int maxFunding;
	private int funding;
	private int accumulatedFunds;
	private Technology currentResearchTech;
	private Technology objectiveTech;

	public ResearchLab(TechnologyTree technologyTree, int maxFunding) {
		super();
		setTechnologyTree(technologyTree);
		setMaxFunding(maxFunding);
		setFunding(0);
		startResearching(null);
	}

	public Set<Technology> getTechnologies() {
		return technologyTree.getTechnologies();
	}

	public void startResearching(Technology technology) {
		if (technology != null) {
			checkArgCondition(technology, !technology.isResearched(),
					"already researched");
			checkArgCondition(technology, getTechnologyTree().getTechnologies()
					.contains(technology), "not contained on technology tree");
		}
		this.objectiveTech = technology;
		this.accumulatedFunds = 0;
		resolveCurrentResearchTechnology();
	}

	public void update() {
		incrementFunds();
		while (canResearchCurrentTechnology()) {
			researchCurrentTechnology();
			resolveCurrentResearchTechnology();
		}
	}
	
	private boolean canResearchCurrentTechnology() {
		return this.currentResearchTech != null
				&& this.accumulatedFunds >= this.currentResearchTech
						.getResearchCost();
	}

	private void researchCurrentTechnology() {
		this.accumulatedFunds -= this.currentResearchTech.getResearchCost();
		this.currentResearchTech.research();
		if (this.currentResearchTech == this.objectiveTech)
			this.objectiveTech = null;
	}

	private void incrementFunds() {
		this.accumulatedFunds += getFunding();
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

	private void resolveCurrentResearchTechnology() {
		if (this.objectiveTech != null)
			this.currentResearchTech = getTechnologyToResearchObjective();
		else
			this.currentResearchTech = getCheapestResearchableTechnology();
	}

	private Technology getCheapestResearchableTechnology() {
		return getCheapest(getAllResearchableTechnologies());
	}

	private Technology getTechnologyToResearchObjective() {
		if (canResearch(this.objectiveTech))
			return this.objectiveTech;
		Set<Technology> dependencies = this.technologyTree
				.getAllDependencies(this.objectiveTech);
		return getCheapest(getResearchableTechnologies(dependencies));
	}

	private Set<Technology> getAllResearchableTechnologies() {
		return getResearchableTechnologies(getTechnologyTree()
				.getTechnologies());
	}

	private Set<Technology> getResearchableTechnologies(
			Set<Technology> technologies) {
		Set<Technology> researchableTechs = new HashSet<Technology>();
		for (Technology tech : technologies) {
			if (canResearch(tech))
				researchableTechs.add(tech);
		}
		return researchableTechs;
	}

	private boolean canResearch(Technology tech) {
		return !tech.isResearched()
				&& this.technologyTree.areAllDependenciesResearched(tech);
	}

	private Technology getCheapest(Set<Technology> technologies) {
		Technology cheapest = null;
		for (Technology tech : technologies)
			if (cheapest == null
					|| tech.getResearchCost() < cheapest.getResearchCost())
				cheapest = tech;
		return cheapest;
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
