package model.lab;

import static java.lang.Math.min;
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
	private int unresearchedTechsCost;
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
		initializeUnresearchedTechnologiesCost();
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
		consumeBudget();
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

	public TechnologyTree getTechnologyTree() {
		return technologyTree;
	}

	public void setDailyFunding(int dailyFunding) {
		checkInRange(dailyFunding, 0, getMaxDailyFunding(), "daily funding");
		this.dailyFunding = dailyFunding;
	}

	public Set<Technology> getUnresearchedDependencies(Technology technology) {
		Set<Technology> deps = technologyTree.getAllDependencies(technology);
		return getUnresearchedTechnologies(deps);
	}

	/**
	 * Retrieves the technology that is currently being researched in the lab.
	 * Might be null in case everything has been researched.
	 * 
	 * @return
	 */
	public Technology getCurrentResearchTechnology() {
		return currentResearchTech;
	}

	/**
	 * Retrieves the current objective technology, i.e: the technology that was
	 * set by {@link ResearchLab#startResearching(Technology)} if it has not
	 * been researched yet, or, otherwise, null.
	 * 
	 * @return
	 */
	public Technology getObjectiveTechTechnology() {
		return objectiveTech;
	}
	
	

	private boolean canResearchCurrentTechnology() {
		return this.currentResearchTech != null
				&& this.accumulatedFunds >= this.currentResearchTech
						.getResearchCost();
	}

	private void researchCurrentTechnology() {
		int researchCost = this.currentResearchTech.getResearchCost();
		this.accumulatedFunds -= researchCost;
		this.unresearchedTechsCost -= researchCost;
		this.currentResearchTech.research();
		if (this.currentResearchTech == this.objectiveTech)
			this.objectiveTech = null;
	}

	private void consumeBudget() {
		int researchEverythingCost = this.unresearchedTechsCost
				- this.accumulatedFunds;
		int budgetToConsume = min(getDailyFunding(), researchEverythingCost);
		this.getBudget().decrement(budgetToConsume);
		this.accumulatedFunds += budgetToConsume;
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
		if (isResearchable(this.objectiveTech))
			return this.objectiveTech;
		Set<Technology> dependencies = this.technologyTree
				.getAllDependencies(this.objectiveTech);
		return getCheapest(getResearchableTechnologies(dependencies));
	}

	private Set<Technology> getAllResearchableTechnologies() {
		return getResearchableTechnologies(getTechnologies());
	}

	private Set<Technology> getResearchableTechnologies(
			Set<Technology> technologies) {
		Set<Technology> researchableTechs = new HashSet<Technology>();
		for (Technology tech : technologies) {
			if (isResearchable(tech))
				researchableTechs.add(tech);
		}
		return researchableTechs;
	}

	private boolean isResearchable(Technology tech) {
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

	private void initializeUnresearchedTechnologiesCost() {
		this.unresearchedTechsCost = 0;
		for (Technology tech : getAllUnresearchedTechnologies())
			this.unresearchedTechsCost += tech.getResearchCost();
	}

	private Set<Technology> getAllUnresearchedTechnologies() {
		return getUnresearchedTechnologies(getTechnologies());
	}

	private Set<Technology> getUnresearchedTechnologies(
			Set<Technology> technologies) {
		Set<Technology> unresearchedTechs = new HashSet<Technology>();
		for (Technology tech : technologies) {
			if (!tech.isResearched())
				unresearchedTechs.add(tech);
		}
		return unresearchedTechs;
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
