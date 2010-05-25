package model.lab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import model.exception.BusinessLogicException;
import static model.lab.ConcreteTechnology.*;

import org.junit.*;

import static org.junit.Assert.*;

public class ResearchLabTest {

	private static final int MAX_DAILY_FUNDING = 100;
	private ResearchLab lab;
	

	@Before
	public void setUpDefault() {
		lab = new ResearchLab(new TechnologyTree(), MAX_DAILY_FUNDING);
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
		Technology tech = setUpWithOneUnresearchedTechnology();
		lab.startResearching(tech);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void startResearchingNonExistentTechnology() {
		lab.startResearching(createUnresearchedTecnology());
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
			techs[i] = new ConcreteTechnology("Tech " + i, "", costs[i], false);
			techTree.addTechnology(techs[i]);
		}
		lab = new ResearchLab(techTree, MAX_DAILY_FUNDING);
		return techs;
	}
	
	private Technology setUpWithOneUnresearchedTechnology() {
		Technology tech = createResearchedTecnology();
		TechnologyTree techTree = new TechnologyTree();
		techTree.addTechnology(tech);
		lab = new ResearchLab(techTree, MAX_DAILY_FUNDING);
		return tech;
	}
	
	private AoeTownCenterTechnologies setUpWithAoeTechnologies() {		
		AoeTownCenterTechnologies techs = new AoeTownCenterTechnologies(0);
		lab = new ResearchLab(techs.createTechnologyTree(), MAX_DAILY_FUNDING);
		return techs;
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
	
	public AoeTownCenterTechnologies(int age) {
		loom                 = createTechnology("Loom",         50,   age > 0);
		advanceToFeudalAge   = createTechnology("Feudal Age",   500,  age > 0);
		wheelbarrow          = createTechnology("Wheelbarrow",  225,  age > 1);
		townWatch            = createTechnology("Town Watch",   75,   age > 1);
		advanceToCastleAge   = createTechnology("Castle Age",   1000, age > 1);
		handCart             = createTechnology("Hand Cart",    550,  age > 2);
		TownPatrol           = createTechnology("Town Patrol",  500,  age > 2);
		advanceToImperialAge = createTechnology("Imperial Age", 1800, age > 2);
	}
	
	public Technology[] getExpectedResearchSequence() {
		return new Technology[] { loom, advanceToFeudalAge, townWatch,
				wheelbarrow, advanceToCastleAge, TownPatrol, handCart,
				advanceToImperialAge };
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
	
	private Technology createTechnology(String name, int cost,
			boolean researched) {
		Technology t = new ConcreteTechnology(name, "Desc", cost, researched);
		return t;
	}
}
