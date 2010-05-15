package model.lab;

import java.util.ArrayList;
import java.util.Collection;

import model.core.BusinessLogicException;

/**
 * A collection of {@link Technology}'s with dependencies between them.
 * 
 */
public class TechnologyTree {

	Collection<TechnologyTreeEntry> technologyTree;

	public TechnologyTree() {
		super();
		this.technologyTree = new ArrayList<TechnologyTreeEntry>();
	}

	public void addDependency(Technology technology, Technology dependency) {
		if (!contains(technology) || !contains(dependency))
			throw new BusinessLogicException("Invalid parameters: technology "
					+ "and dependency must be contained in technology tree");
		// TODO Auto-generated method stub
	}

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