package model.lab;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import model.exception.BusinessLogicException;
import model.game.Budget;

import org.junit.Before;
import org.junit.Test;

public class ResearchLabTest {

	private static final int MAX_DAILY_FUNDING = 100;
	private static final int INITIAL_BUDGET = 1000;
	private ResearchLab lab;
	private Budget budget;

	@Before
	public void setUpDefault() {
		setUpWithTechnologyTree(new TechnologyTree());
	}

	@Test
	public void initialDailyFundigToZero() {
		assertEquals(0, lab.getDailyFunding());
	}
	
	@Test
	public void setDailyFundigToMax() {
		lab.setDailyFunding(MAX_DAILY_FUNDING);
		assertEquals(MAX_DAILY_FUNDING, lab.getDailyFunding());
	}
	
	@Test(expected = BusinessLogicException.class)
	public void setDailyFundingLowerThanZero() {
		lab.setDailyFunding(-1);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void setDailyFundingGreaterThanMax() {
		lab.setDailyFunding(MAX_DAILY_FUNDING + 1);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void startResearchingAlreadyResearchedTechnology() {
		Technology tech = setUpWithOneResearchedTechnology();
		lab.startResearching(tech);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void startResearchingNonExistentTechnology() {
		lab.startResearching(new ConcreteTechnology());
	}
	
	@Test
	public void researchCheapestTechnologyFirst() {
		Technology[] techs = setUpWithTechnologiesByCosts(100, 50);
		Technology expensive = techs[0];
		Technology cheapest = techs[1];
		lab.setDailyFunding(10);
		Technology firstResearched = updateUntilOneTechnologyIsResearched();
		assertEquals(cheapest, firstResearched);
		assertTrue(cheapest.isResearched());
		assertFalse(expensive.isResearched());
	}
	
	@Test
	public void researchCheapestAndCheckCost() {
		Technology[] techs = setUpWithTechnologiesByCosts(100, 50);
		Technology cheapest = techs[1];
		lab.setDailyFunding(10);
		for (int i = 1; i <= 5; i++) {
			lab.updateDay();
			assertEquals("Iteration " + i, i == 5, cheapest.isResearched());
		}
	}
	
	@Test
	public void notResearchCheapestIfStartedResearchingSomethingElse() {
		Technology[] techs = setUpWithTechnologiesByCosts(100, 50);
		Technology expensive = techs[0];
		Technology cheapest = techs[1];
		lab.setDailyFunding(10);
		lab.startResearching(expensive);
		Technology firstResearched = updateUntilOneTechnologyIsResearched();
		assertEquals(expensive, firstResearched);
		assertTrue(expensive.isResearched());
		assertFalse(cheapest.isResearched());
	}
	
	@Test
	public void researchMoreThanOneTechnologyOnSameUpdate() {
		Technology[] techs = setUpWithTechnologiesByCosts(75, 25);
		Technology expensive = techs[0];
		Technology cheapest = techs[1];
		lab.setDailyFunding(50);
		lab.startResearching(expensive);
		
		lab.updateDay();
		assertFalse(expensive.isResearched());
		assertFalse(cheapest.isResearched());
		lab.updateDay();
		assertTrue(expensive.isResearched());
		assertTrue(cheapest.isResearched());
	}
	
	@Test
	public void researchTwoTechnologiesAndCheckBudget() {
		Technology[] techs = setUpWithTechnologiesByCosts(25, 30);
		int expectedBalance = INITIAL_BUDGET - 30 - 25;
		lab.setDailyFunding(30);
		lab.updateDay();
		lab.updateDay();
		
		assertEquals(2, techs.length);
		assertTrue(techs[0].isResearched());
		assertTrue(techs[1].isResearched());
		assertEquals(expectedBalance, budget.getBalance());
		
		lab.updateDay();
		assertEquals(expectedBalance, budget.getBalance());
	}
	
	@Test
	public void researchFreeTechnologiesAndCheckBudget() {
		Technology[] techs = setUpWithTechnologiesByCosts(0, 0, 0);
		lab.setDailyFunding(30);
		lab.updateDay();
		
		assertEquals(3, techs.length);
		for (int i = 0; i < techs.length; i++)
			assertTrue(techs[i].isResearched());
		assertEquals(INITIAL_BUDGET, budget.getBalance());
	}
	
	@Test
	public void researchAllAoeTechnologiesAndCheckOrder() {
		AoeTownCenterTechnologies techs = setUpWithAoeTechnologies();
		Technology[] expectedTechs = techs.getExpectedResearchSequence();
		lab.setDailyFunding(50);
		for (int i = 0; i < expectedTechs.length; i++) {
			Technology tech = updateUntilOneTechnologyIsResearched();
			assertEquals(expectedTechs[i], tech);
		}
		assertTrue(getUnresearchedTechnologies().isEmpty());
	}
	
	@Test
	public void startResearchingImperialAgeAndUpdateUntilItIsResearched() {
		AoeTownCenterTechnologies techs = setUpWithAoeTechnologies();
		lab.setDailyFunding(50);
		lab.startResearching(techs.advanceToImperialAge);
		while (!techs.advanceToImperialAge.isResearched())
			lab.updateDay();
		
		List<Technology> agesTechs = Arrays.asList(new Technology[] {
				techs.advanceToFeudalAge, techs.advanceToCastleAge,
				techs.advanceToImperialAge });
		for (Technology tech : lab.getTechnologies()) 
			assertEquals(
					"Only advance-to-age technologies should be researched",
					agesTechs.contains(tech), tech.isResearched());
	}
	
	@Test
	public void researchAllAoeTechnologiesAndCheckCost() {
		AoeTownCenterTechnologies techs = setUpWithAoeTechnologies();
		int totalCost = techs.getTotalResearchCost();
		lab.setDailyFunding(50);
		int nUpdates = totalCost / 50 + 1;
		for (int i = 0; i < nUpdates; i++)
			lab.updateDay();
		for (Technology tech : lab.getTechnologies())
			assertTrue(tech.isResearched());
		assertEquals(INITIAL_BUDGET - totalCost, budget.getBalance());
	}
	
	private Technology updateUntilOneTechnologyIsResearched() {
		final int MAX_UPDATES = 100;
		int i = 0;
		Collection<Technology> unresearchedTechs = getUnresearchedTechnologies();
		while (i++ < MAX_UPDATES) {
			lab.updateDay();
			for (Technology tech : unresearchedTechs) 
				if (tech.isResearched())
					return tech;
		}
		throw new RuntimeException("Infinite loop");
	}
	
	private Collection<Technology> getUnresearchedTechnologies() {
		Collection<Technology> unresearchedTechs = new ArrayList<Technology>();
		for (Technology tec : lab.getTechnologies())
			if (!tec.isResearched())
				unresearchedTechs.add(tec);
		return unresearchedTechs;
	}
	
	private Technology[] setUpWithTechnologiesByCosts(int... costs) {
		TechnologyTree techTree = new TechnologyTree();
		Technology[] techs = new Technology[costs.length];
		for (int i = 0; i < costs.length; i++) {
			techs[i] = new ConcreteTechnology("Tech " + i, "", costs[i]);
			techTree.addTechnology(techs[i]);
		}
		setUpWithTechnologyTree(techTree);
		return techs;
	}
	
	private Technology setUpWithOneResearchedTechnology() {
		Technology tech = new ConcreteTechnology();
		tech.research();
		TechnologyTree techTree = new TechnologyTree();
		techTree.addTechnology(tech);
		setUpWithTechnologyTree(techTree);
		return tech;
	}
	
	private AoeTownCenterTechnologies setUpWithAoeTechnologies() {		
		AoeTownCenterTechnologies techs = new AoeTownCenterTechnologies();
		setUpWithTechnologyTree(techs.createTechnologyTree());
		return techs;
	}
	
	private void setUpWithTechnologyTree(TechnologyTree technologyTree) {
		budget = new Budget(INITIAL_BUDGET);
		lab = new ResearchLab(technologyTree, MAX_DAILY_FUNDING, budget);
	}
}

class AoeTownCenterTechnologies {
	final Technology loom;
	final Technology advanceToFeudalAge;
	final Technology     wheelbarrow;
	final Technology     townWatch;
	final Technology     advanceToCastleAge;
	final Technology         handCart;
	final Technology         TownPatrol;
	final Technology         advanceToImperialAge;
	
	public AoeTownCenterTechnologies() {
		loom                 = createTechnology("Loom",         50);
		advanceToFeudalAge   = createTechnology("Feudal Age",   500);
		wheelbarrow          = createTechnology("Wheelbarrow",  225);
		townWatch            = createTechnology("Town Watch",   75);
		advanceToCastleAge   = createTechnology("Castle Age",   1000);
		handCart             = createTechnology("Hand Cart",    550);
		TownPatrol           = createTechnology("Town Patrol",  500);
		advanceToImperialAge = createTechnology("Imperial Age", 1800);
	}
	
	public Technology[] getExpectedResearchSequence() {
		return new Technology[] { loom, advanceToFeudalAge, townWatch,
				wheelbarrow, advanceToCastleAge, TownPatrol, handCart,
				advanceToImperialAge };
	}
	
	public int getTotalResearchCost() {
		int total = 0;
		for (Technology tech : getExpectedResearchSequence())
			total += tech.getResearchCost();
		return total;
	}

	public TechnologyTree createTechnologyTree() {
		TechnologyTree techTree = new TechnologyTree();
		addAllTechnologies(techTree);
		addAllDependencies(techTree);
		return techTree;
	}
	
	private void addAllTechnologies(TechnologyTree techTree) {
		techTree.addTechnology(loom);
		techTree.addTechnology(advanceToFeudalAge);
		techTree.addTechnology(wheelbarrow);
		techTree.addTechnology(townWatch);
		techTree.addTechnology(advanceToCastleAge);
		techTree.addTechnology(handCart);
		techTree.addTechnology(TownPatrol);
		techTree.addTechnology(advanceToImperialAge);
	}
	
	private void addAllDependencies(TechnologyTree techTree) {
		techTree.addDependency(wheelbarrow, advanceToFeudalAge);
		techTree.addDependency(townWatch, advanceToFeudalAge);
		techTree.addDependency(advanceToCastleAge, advanceToFeudalAge);
		techTree.addDependency(handCart, advanceToCastleAge);
		techTree.addDependency(handCart, wheelbarrow);
		techTree.addDependency(TownPatrol, advanceToCastleAge);
		techTree.addDependency(TownPatrol, townWatch);
		techTree.addDependency(advanceToImperialAge, advanceToCastleAge);
	}
	
	private Technology createTechnology(String name, int cost) {
		Technology t = new ConcreteTechnology(name, "Desc", cost);
		return t;
	}
}
