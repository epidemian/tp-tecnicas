package model.lab;

import java.util.ArrayList;
import java.util.Collection;

import model.core.BusinessLogicException;

import org.junit.*;
import static org.junit.Assert.*;

public class ResearchLabTest {

	private static final int MAX_FUNDING = 100;
	private ResearchLab lab;
	private Technology loom;
	private Technology advanceToFeudalAge;
	private Technology     wheelbarrow;
	private Technology     townWatch;
	private Technology     advanceToCastleAge;
	private Technology         handCart;
	private Technology         TownPatrol;
	private Technology         advanceToImperialAge;
	private Collection<Technology> allTechnologies;
	

	@Before
	public void setUp() {
		setUpByAge(0);
	}

	@Test
	public void initialDailyFundigToZero() {
		assertEquals(0, lab.getFunding());
	}
	
	@Test
	public void setDailyFundigToMax() {
		lab.setFunding(MAX_FUNDING);
		assertEquals(MAX_FUNDING, lab.getFunding());
	}
	
	@Test(expected = BusinessLogicException.class)
	public void setDailyFundingLowerThanZero() {
		lab.setFunding(-1);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void setDailyFundingGreaterThanMax() {
		lab.setFunding(MAX_FUNDING + 1);
	}
	
	@Test @Ignore
	public void researchCheapestTechnologyFirst() {
		lab.setFunding(10);
		// Loom is the cheapest in the Dark Age.
		assertEquals(loom, updateUntilOneTechnologyIsResearched());
	}
	
	@Test @Ignore
	public void researchCheapestAndCheckCost() {
		lab.setFunding(10);
		for (int i = 0; i < 5; i++) {
			lab.update();
			assertEquals("Iteration " + i, i == 4, loom.isResearched());
		}
	}
	
	private Object updateUntilOneTechnologyIsResearched() {
		final int MAX_UPDATES = 100;
		int i = 0;
		Collection<Technology> techs = getUnresearchedTechnologies();
		while (i++ < MAX_UPDATES) {
			lab.update();
			for (Technology tec : techs) 
				if (tec.isResearched())
					return tec;
		}
		throw new RuntimeException("Infinite loop");
	}

	private Collection<Technology> getUnresearchedTechnologies() {
		Collection<Technology> unresearchedTechs = new ArrayList<Technology>();
		for (Technology tec : allTechnologies)
			if (!tec.isResearched())
				unresearchedTechs.add(tec);
		return unresearchedTechs;
	}

	private Technology createTechnology(String name, int cost,
			boolean researched) {
		Technology t = new TechnologyMock(name, "Desc", cost, researched);
		allTechnologies.add(t);
		return t;
	}
	
	private void setUpByAge(int age) {
		TechnologyTree techTree = new TechnologyTree();
		allTechnologies = new ArrayList<Technology>();
		
		loom                 = createTechnology("Loom",         50,   age > 0);
		advanceToFeudalAge   = createTechnology("Feudal Age",   500,  age > 0);
		wheelbarrow          = createTechnology("Wheelbarrow",  225,  age > 1);
		townWatch            = createTechnology("Town Watch",   75,   age > 1);
		advanceToCastleAge   = createTechnology("Castle Age",   1000, age > 1);
		handCart             = createTechnology("Hand Cart",    500,  age > 2);
		TownPatrol           = createTechnology("Town Patrol",  500,  age > 2);
		advanceToImperialAge = createTechnology("Imperial Age", 1800, age > 2);
		
		techTree.addTechnology(loom);
		techTree.addTechnology(advanceToFeudalAge);
		techTree.addTechnology(wheelbarrow);
		techTree.addTechnology(townWatch);
		techTree.addTechnology(advanceToCastleAge);
		techTree.addTechnology(handCart);
		techTree.addTechnology(TownPatrol);
		techTree.addTechnology(advanceToImperialAge);
		
		techTree.addDependency(wheelbarrow, advanceToFeudalAge);
		techTree.addDependency(townWatch, advanceToFeudalAge);
		techTree.addDependency(advanceToCastleAge, advanceToFeudalAge);
		techTree.addDependency(handCart, advanceToCastleAge);
		techTree.addDependency(handCart, wheelbarrow);
		techTree.addDependency(TownPatrol, advanceToCastleAge);
		techTree.addDependency(TownPatrol, townWatch);
		techTree.addDependency(advanceToImperialAge, advanceToCastleAge);
		
		lab = new ResearchLab(techTree, MAX_FUNDING);
	}
}
