package model.lab;

import java.util.ArrayList;
import java.util.List;

import model.core.BusinessLogicException;

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
	
	@Test(expected = BusinessLogicException.class)
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
	
	@Test(expected = BusinessLogicException.class)
	public void addSimpleCircularDependency() {
		Technology egg = createAndAddTechnology("Egg");
		Technology chicken = createAndAddTechnology("Chicken");
		tree.addDependency(egg, chicken);
		tree.addDependency(chicken, egg);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void addLongCircularDependency() {
		final int COUNT = 5;
		List<Technology> techs = new ArrayList<Technology>(COUNT); 
		for (int i = 0; i < COUNT; i++)
			techs.add(createAndAddTechnology("Tech " + i));
		for (int i = 1; i < COUNT; i++)
			tree.addDependency(techs.get(i), techs.get(i - 1));
		assertTrue(true); // Up to here nothing should have exploded...
		tree.addDependency(techs.get(0), techs.get(COUNT - 1));
	}
	
//	@Test(expected = BusinessLogicException.class)
//	public void addDependencyFromUnresearchedTechToResearchedTech() {
//		Technology terminator1 = createAndAddTechnology("T-800", true);
//		Technology terminator2 = createAndAddTechnology("T-1000", false);
//		tree.addDependency(terminator1, terminator2);
//	}
	
	private Technology createTechnology(String name, boolean researched) {
		return new TechnologyMock(name, "Desc.", 0, researched);
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

