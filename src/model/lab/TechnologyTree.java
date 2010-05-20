package model.lab;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.core.BusinessLogicException;
import static model.core.ArgumentUtils.*;

/**
 * A collection of {@link Technology}'s with dependencies between them. Notice
 * that the Age-of-Empires-inspired name 'technology tree' might not be strictly
 * accurate, as the nodes in the dependency structure might have more than one
 * parent (i.e. dependency). e.g: A has no dependencies, B and C depends on A,
 * and D depends on both B and C; are valid dependencies:
 * 
 * <pre>
 *    A
 *   / \
 *  B   C
 *   \ /
 *    D
 * </pre>
 */
public class TechnologyTree {

	/**
	 * Key: technology, value: dependencies.
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
	public Set<Technology> getTechnologies() {
		return Collections.unmodifiableSet(this.technologies.keySet());
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

	/**
	 * Retrieves all dependencies for a given technology. e.g: If A depends on
	 * B, and B depends on C and D; getAllDependencies(A) will return B, C and
	 * D.
	 * 
	 * @param technology
	 * @return
	 */
	Set<Technology> getAllDependencies(Technology technology) {
		Set<Technology> deps = new HashSet<Technology>();
		for (Technology tech : getImmediateDependencies(technology)) {
			deps.add(tech);
			deps.addAll(getAllDependencies(tech));
		}
		return deps;
	}
	
	boolean areAllDependenciesResearched(Technology technology) {
		for (Technology dep : getAllDependencies(technology)) {
			if (!dep.isResearched())
				return false;
		}
		return true;
	}

	private Set<Technology> getImmediateDependencies(Technology technology) {
		return this.technologies.get(technology);
	}

	private void testAddDependency(Technology technology, Technology dependency) {
		checkArgCondition(technology, contains(technology),
				"technology must be contained in technology tree");
		checkArgCondition(dependency, contains(dependency),
				"dependency must be contained in technology tree");
		checkArgCondition(technology, !technology.equals(dependency),
				"technology cannot depend on itself");
		checkCircualDependencies(technology, dependency);
		checkRedundantDependencies(technology, dependency);
	}

	private void checkCircualDependencies(Technology technology,
			Technology dependency) {
		if (technologyDependsOn(dependency, technology))
			throw new CircularDependencyException(technology, dependency);
	}

	private void checkRedundantDependencies(Technology technology,
			Technology dependency) {
		if (technologyDependsOn(technology, dependency))
			throw new RedundantDependencyException(technology, dependency);

	}

	private void doAddDependency(Technology technology, Technology dependency) {
		this.technologies.get(technology).add(dependency);
	}

	private void testAddTechnology(Technology technology) {
		checkNotNull(technology, "technology");
		checkArgCondition(technology, !contains(technology),
				"already exists in technology tree");
		checkArgCondition(technology, !containsTechnologyWithName(technology
				.getName()), "technology with same name already exists");
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

}

class CircularDependencyException extends BusinessLogicException {
	private static final long serialVersionUID = 1055933768429983181L;

	public CircularDependencyException(Technology technology,
			Technology dependency) {
		super("Circular dependency: " + technology + " cannot depend on "
				+ dependency);
	}
}

class RedundantDependencyException extends BusinessLogicException {
	private static final long serialVersionUID = -4565594124803529862L;

	public RedundantDependencyException(Technology technology,
			Technology dependency) {
		super("Redundant dependency: " + technology + " already depends on "
				+ dependency);
	}
}