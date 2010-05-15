package model.lab;

import java.util.ArrayList;
import java.util.Collection;

import model.core.BusinessLogicException;

/**
 * A collection of {@link Technology}'s with dependencies between them.
 * Notice that the Age-of-Empires-inspired name 'technology tree' might not be
 * strictly accurate, as the nodes in the dependency structure might have more
 * than one parent (i.e. dependency).
 * e.g: A has no dependencies, B and C depends on A, and D depends on both B 
 * and C; are valid dependencies. 
 *     A
 *    / \
 *   B   C
 *    \ /
 *     D  
 */
public class TechnologyTree {

	Collection<TechnologyTreeEntry> technologyTree;

	public TechnologyTree() {
		super();
		this.technologyTree = new ArrayList<TechnologyTreeEntry>();
	}

	/**
	 * Adds a dependency between two technologies in the technology tree.
	 * 
	 * @param technology
	 * @param dependency
	 */
	public void addDependency(Technology technology, Technology dependency) {
		if (!contains(technology) || !contains(dependency))
			throw new BusinessLogicException("Invalid parameters: technology "
					+ "and dependency must be contained in technology tree");
		// TODO Auto-generated method stub
	}

	/**
	 * Adds a new technology to the technology tree.
	 * @param technology
	 */
	public void addTechnology(Technology technology) {
		if (technology == null)
			throw new BusinessLogicException("Invalid parameter");
		if (contains(technology))
			throw new BusinessLogicException("Technology " + technology
					+ " already exists in technology tree");
		if (containsTechnologyWithName(technology.getName()))
			throw new BusinessLogicException("Technology with same name as "
					+ technology + " already exists in technoloy tree");

		technologyTree.add(new TechnologyTreeEntry(technology));
	}

	private boolean contains(Technology technology) {
		for (TechnologyTreeEntry entry : technologyTree)
			if (entry.technology.equals(technology))
				return true;
		return false;
	}

	private boolean containsTechnologyWithName(String name) {
		for (TechnologyTreeEntry entry : technologyTree)
			if (entry.technology.getName().equals(name))
				return true;
		return false;
	}

}

class TechnologyTreeEntry {

	Collection<Technology> dependencies;
	final Technology technology;

	public TechnologyTreeEntry(Technology technology) {
		super();
		this.technology = technology;
	}

}