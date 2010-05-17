package model.lab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.core.BusinessLogicException;

/**
 * A collection of {@link Technology}'s with dependencies between them. Notice
 * that the Age-of-Empires-inspired name 'technology tree' might not be strictly
 * accurate, as the nodes in the dependency structure might have more than one
 * parent (i.e. dependency). e.g: A has no dependencies, B and C depends on A,
 * and D depends on both B and C; are valid dependencies. A / \ B C \ / D
 */
public class TechnologyTree {

	/**
	 * Key: technology, Value: dependencies.
	 */
	private Map<Technology, Set<Technology>> technologies;

	/**
	 * Creates an empty technology tree.
	 */
	public TechnologyTree() {
		super();
		this.technologies = new HashMap<Technology, Set<Technology>>();
	}

	/**
	 * Retrieves all the technologies in the technology tree.
	 * 
	 * @return A collection of all the technologies in the technology tree.
	 *         Changes to the returned collection do not affect the technology
	 *         tree.
	 */
	public Collection<Technology> getTechnologies() {
		return Collections.unmodifiableCollection(this.technologies.keySet());
	}

	/**
	 * Adds a new technology to the technology tree.
	 * 
	 * @param technology
	 * @throws BusinessLogicException
	 *             If <code>technology</code> is already in tree, or a
	 *             technology with the same name is already in tree, or
	 *             <code>technology</code> is <code>null</null>.
	 */
	public void addTechnology(Technology technology) {
		testAddTechnology(technology);
		doAddTechnology(technology);
	}

	/**
	 * Adds a dependency between two technologies in the technology tree.
	 * 
	 * @param technology
	 * @param dependency
	 */
	public void addDependency(Technology technology, Technology dependency) {
		testAddDependency(technology, dependency);
		doAddDependency(technology, dependency);
	}

	private void testAddDependency(Technology technology, Technology dependency) {
		if (!contains(technology) || !contains(dependency))
			throw new BusinessLogicException("Invalid parameters: technology "
					+ "and dependency must be contained in technology tree");
		if (technology.equals(dependency))
			throw new BusinessLogicException("technology cannot depend on "
					+ "itself");
		checkCircualDependencies(technology, dependency);
		checkRedundantDependencies(technology, dependency);
	}

	private void checkCircualDependencies(Technology technology,
			Technology dependency) {
		if (technologyDependsOn(dependency, technology))
			throw new CircularDependencyException();
	}

	private void checkRedundantDependencies(Technology technology,
			Technology dependency) {
		if (technologyDependsOn(technology, dependency))
			throw new RedundantDependencyException();

	}

	private void doAddDependency(Technology technology, Technology dependency) {
		this.technologies.get(technology).add(dependency);
	}

	private void testAddTechnology(Technology technology) {
		if (technology == null)
			throw new BusinessLogicException("Invalid parameter");
		if (contains(technology))
			throw new BusinessLogicException("Technology " + technology
					+ " already exists in technology tree");
		if (containsTechnologyWithName(technology.getName()))
			throw new BusinessLogicException("Technology with same name as "
					+ technology + " already exists in technoloy tree");
	}

	private void doAddTechnology(Technology technology) {
		this.technologies.put(technology, new HashSet<Technology>());
	}

	private boolean contains(Technology technology) {
		return this.technologies.containsKey(technology);
	}

	private boolean containsTechnologyWithName(String name) {
		for (Technology tech : this.technologies.keySet())
			if (tech.getName().equals(name))
				return true;
		return false;
	}

	private boolean technologyDependsOn(Technology technology,
			Technology dependency) {
		return getAllDependencies(technology).contains(dependency);
	}
	
	private Set<Technology> getAllDependencies( Technology technology) {
		Set<Technology> deps = new HashSet<Technology>();
		for (Technology tec : getImmediateDependencies(technology)) {
			deps.add(tec);
			deps.addAll(getAllDependencies(tec));
		}
		return deps;
	}
	
	private Set<Technology> getImmediateDependencies(
			Technology technology) {
		return this.technologies.get(technology);
	}

}

class CircularDependencyException extends BusinessLogicException {
	private static final long serialVersionUID = 1055933768429983181L;

	public CircularDependencyException() {
		super("Circular dependency");
	}
}

class RedundantDependencyException extends BusinessLogicException {
	private static final long serialVersionUID = -4565594124803529862L;

	public RedundantDependencyException() {
		super("Redundant dependency");
	}
}