package model.lab;

import static model.utils.ArgumentUtils.checkArgCondition;
import static model.utils.ArgumentUtils.*;

import java.util.HashSet;
import java.util.Set;

import model.game.Budget;
import model.game.time.DailyUpdatable;

/**
 * A research lab that consumes funds to research technologies.
 */
public class ResearchLab implements DailyUpdatable {

	private TechnologyTree technologyTree;
	private Budget budget;
	private int maxDailyFunding;
	private int dailyFunding;
	private int accumulatedFunds;
	private Technology currentResearchTech;
	private Technology objectiveTech;

	/**
	 * Creates a research lab with a given technology tree, from which
	 * technologies will be researched, and a maximum funding.
	 * 
	 * @param technologyTree
	 * @param maxDailyFunding
	 */
	public ResearchLab(TechnologyTree technologyTree, int maxDailyFunding,
			Budget budget) {
		super();
		setTechnologyTree(technologyTree);
		setMaxDailyFunding(maxDailyFunding);
		setBudget(budget);
		setDailyFunding(0);
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

	@Override
	public void updateDay() {
		incrementFunds();
		while (canResearchCurrentTechnology()) {
			researchCurrentTechnology();
			resolveCurrentResearchTechnology();
		}
	}

	public int getMaxDailyFunding() {
		return maxDailyFunding;
	}

	public int getDailyFunding() {
		return dailyFunding;
	}

	public void setDailyFunding(int dailyFunding) {
		checkInRange(dailyFunding, 0, getMaxDailyFunding(), "daily funding");
		this.dailyFunding = dailyFunding;
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
		this.accumulatedFunds += getDailyFunding();
		//this.getBudget().decrement(getDailyFunding());
		// TODO: que no gaste más que lo necesario.
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

	private void setMaxDailyFunding(int maxDailyFunding) {
		checkGreaterThan(maxDailyFunding, 0, "max daily funding");
		this.maxDailyFunding = maxDailyFunding;
	}

	private Budget getBudget() {
		return budget;
	}

	private void setBudget(Budget budget) {
		checkNotNull(budget, "budget");
		this.budget = budget;
	}

}
