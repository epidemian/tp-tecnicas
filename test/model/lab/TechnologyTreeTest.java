package model.lab;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import model.exception.BusinessLogicException;

import org.junit.*;
import static org.junit.Assert.*;

public class TechnologyTreeTest {

	private TechnologyTree tree;

	@Before
	public void setUp() {
		this.tree = new TechnologyTree();
	}
	
	// Tests to addTechnology()
	
	@Test
	public void addOneTechnology() {
		createAndAddTechnology();
	}
	
	@Test(expected = BusinessLogicException.class)
	public void addSameTechnologyTwice() {
		Technology t = createTechnology();
		tree.addTechnology(t);
		tree.addTechnology(t);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void addTwoTechnologiesWithSameName() {
		createAndAddTechnology("Same name");
		createAndAddTechnology("Same name");
	}
	
	@Test
	public void addTwoDifferentTechnologies() {
		createAndAddTechnology("Wheel");
		createAndAddTechnology("Laser");
	}

	// Tests to getTechnologies()
	
	@Test
	public void isEmptyAfterCreation() {
		assertTrue(tree.getTechnologies().isEmpty());
	}
	
	@Test
	public void addOneTechnologyAndTestGetTechnologies() {
		Technology t = createAndAddTechnology();
		assertEquals(1, tree.getTechnologies().size());
		assertTrue(tree.getTechnologies().contains(t));
	}
	
	@Test
	public void addManyTechnologiesAndTestGetTechnologies() {
		final int SIZE = 5;
		Technology techs[] = createAndAddTechnologyArray(SIZE);
		assertEquals(SIZE, tree.getTechnologies().size());
		assertTrue(tree.getTechnologies().containsAll(Arrays.asList(techs)));
	}
	
	@Test
	public void addTechnologyToCollectionReturnedByGetTechnologies() {
		createAndAddTechnology("Added");
		try {
			Collection<Technology> techs = tree.getTechnologies();
			techs.add(createTechnology("Should not be added"));
		}
		catch (Exception e) {
			// Nothing. Adding to that collection might not be valid.
		}
		finally {
			assertEquals(1, tree.getTechnologies().size());
		}
	}
	
	// Tests to addDependency()
	
	@Test
	public void addSingleDependency() {
		Technology it = createAndAddTechnology("Information Tecnologies");
		Technology loom = createAndAddTechnology("Loom");
		tree.addDependency(it, loom);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void addDependencyOnNonAddedTechnologies() {
		tree.addDependency(createTechnology("one"), createTechnology("two"));
	}
	
	@Test(expected = RedundantDependencyException.class)
	public void addSameDependencyTwice() {
		Technology son = createAndAddTechnology("Son");
		Technology mom = createAndAddTechnology("Mom");
		tree.addDependency(son, mom);
		tree.addDependency(son, mom);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void addSelfDependency() {
		Technology selfDependant = createAndAddTechnology();
		tree.addDependency(selfDependant, selfDependant);
	}
	
	@Test(expected = CircularDependencyException.class)
	public void addSimpleCircularDependency() {
		Technology egg = createAndAddTechnology("Egg");
		Technology chicken = createAndAddTechnology("Chicken");
		tree.addDependency(egg, chicken);
		tree.addDependency(chicken, egg);
	}
	
	@Test(expected = CircularDependencyException.class)
	public void addLongCircularDependency() {
		Technology[] techs = createAndAddDependencyChain(5);
		assertTrue(true); // Up to here nothing should have exploded...
		tree.addDependency(techs[0], techs[techs.length - 1]);
	}
	
	@Test
	public void addDiamondShapeDependencies() {
		Technology techs[] = createAndAddTechnologyArray(4);
		tree.addDependency(techs[1], techs[0]);
		tree.addDependency(techs[2], techs[0]);
		tree.addDependency(techs[3], techs[1]);
		tree.addDependency(techs[3], techs[2]);
	}
	
	@Test(expected = RedundantDependencyException.class)
	public void addRedundantDependency() {
		Technology techs[] = createAndAddTechnologyArray(3);
		tree.addDependency(techs[0], techs[1]);
		tree.addDependency(techs[1], techs[2]);
		assertTrue(true);
		tree.addDependency(techs[0], techs[2]);
	}
	
	// Tests to getAllDependencies()
	
	@Test
	public void getAllDependenciesOnLongDependencyChain() {
		Technology[] techs = createAndAddDependencyChain(5);
		for (int i = 0; i < techs.length; i++) {
			Technology tec = techs[i];
			Set<Technology> deps = tree.getAllDependencies(tec);
			assertEquals(i, deps.size());
			Technology[] expectedDeps = Arrays.copyOfRange(techs, 0, i);
			assertTrue(deps.containsAll(Arrays.asList(expectedDeps)));
		}
			
	}
	
	// Tests to areAllDependenciesResearched()
	
	@Test
	public void areAllDependenciesResearchedWithNoDependencies() {
		Technology t = createAndAddTechnology();
		assertTrue(tree.areAllDependenciesResearched(t));
	}
	
	@Test
	public void areAllDependenciesResearchedWithSoleDependencyResearched() {
		Technology t1 = createAndAddTechnology("Tech 1");
		Technology t2 = createAndAddTechnology("Tech 2", true);
		tree.addDependency(t1, t2);
		assertTrue(tree.areAllDependenciesResearched(t1));
	}
	
	@Test
	public void areAllDependenciesResearchedWithSoleDependencyUnresearched() {
		Technology t1 = createAndAddTechnology("Tech 1");
		Technology t2 = createAndAddTechnology("Tech 2", false);
		tree.addDependency(t1, t2);
		assertFalse(tree.areAllDependenciesResearched(t1));
	}
	
	@Test
	public void areAllDependenciesResearchedWithAllDeepDependenciesResearched() {
		Technology[] techs = createAndAddDependencyChain(5, true);
		for (Technology tech : techs) 
			assertTrue(tree.areAllDependenciesResearched(tech));
	}
	
	@Test
	public void areAllDependenciesResearchedWithAllDeepDependenciesUnresearched() {
		Technology[] techs = createAndAddDependencyChain(5, false);
		assertTrue(tree.areAllDependenciesResearched(techs[0]));
		for (int i = 1; i < techs.length; i++)
			assertFalse(tree.areAllDependenciesResearched(techs[i]));
		
	}
	
	private Technology[] createAndAddDependencyChain(int size) {
		return createAndAddDependencyChain(size, true);
	}

	/**
	 * Creates and retrieves a sequence of technologies of a given size, N,
	 * where:
	 * 
	 * <pre>
	 * t1 <- t2 <- ... <- tN-1 <- tN
	 * </pre>
	 * 
	 * Where "<tt>X <- Y</tt>" means that Y depends on X. 
	 * 
	 * @param size
	 * @param researched
	 * @return
	 */
	private Technology[] createAndAddDependencyChain(int size,
			boolean researched) {
		Technology techs[] = createAndAddTechnologyArray(size, researched);
		for (int i = 1; i < techs.length; i++)
			tree.addDependency(techs[i], techs[i - 1]);
		return techs;
	}

	private Technology[] createAndAddTechnologyArray(int size) {
		return createAndAddTechnologyArray(size, true);
	}
	
	private Technology[] createAndAddTechnologyArray(int size,
			boolean researched) {
		Technology techs[] = new Technology[size];
		for (int i = 0; i < techs.length; i++)
			techs[i] = createAndAddTechnology("Tech " + i, researched);
		return techs;
	}

	private Technology createTechnology(String name, boolean researched) {
		return new ConcreteTechnology(name, "Desc.", 0, researched);
	}

	private Technology createTechnology(String name) {
		return createTechnology(name, true);
	}

	private Technology createTechnology() {
		return createTechnology("Name");
	}

	private Technology createAndAddTechnology(String name, boolean researched) {
		Technology t = createTechnology(name, researched);
		tree.addTechnology(t);
		return t;
	}

	private Technology createAndAddTechnology(String name) {
		return createAndAddTechnology(name, true);
	}

	private Technology createAndAddTechnology() {
		return createAndAddTechnology("Name");
	}

}
