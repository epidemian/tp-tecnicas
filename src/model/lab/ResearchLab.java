package model.lab;

import static model.utils.ArgumentUtils.checkArgCondition;
import static model.utils.ArgumentUtils.*;

import java.util.HashSet;
import java.util.Set;

/**
 * A research lab that consumes funds to research technologies.
 */
public class ResearchLab {

	private TechnologyTree technologyTree;
	private int maxFunding;
	private int funding;
	private int accumulatedFunds;
	private Technology currentResearchTech;
	private Technology objectiveTech;

	/**
	 * Creates a research lab with a given technology tree, from which
	 * technologies will be researched, and a maximum funding.
	 * 
	 * @param technologyTree
	 * @param maxFunding
	 */
	public ResearchLab(TechnologyTree technologyTree, int maxFunding) {
		super();
		setTechnologyTree(technologyTree);
		setMaxFunding(maxFunding);
		setFunding(0);
		startResearching(null);
	}

	/**
	 * Retrieves all technologies that were or can be researched in the lab.
	 * 
	 * @return
	 */
	public Set<Technology> getTechnologies() {
		return technologyTree.getTechnologies();
	}

	/**
	 * Sets a given technology as the 'objective' technology for researching.
	 * <p>
	 * If the objective technology is null, the lab will start researching the
	 * cheapest technology available. Otherwise, it will research the necessary
	 * dependencies to research the objective technology, i.e: all it's
	 * dependencies and then the objective technology itself.
	 * <p>
	 * When a new objective technology is set, all the funds spent on
	 * researching previous technologies are lost.
	 * 
	 * @param technology
	 *            Must be contained in {@link TechnologyTree#getTechnologies()}
	 *            and not be researched.
	 */
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
