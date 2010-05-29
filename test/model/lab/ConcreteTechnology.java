package model.lab;

/**
 * A concrete {@link Technology} that does noting when
 * {@link Technology#research()} is called.
 */
class ConcreteTechnology extends Technology {

	private static final String DEFAULT_NAME = "Name";
	private static final String DEFAULT_DESCRIPTION = "Desc";
	private static final int DEFAULT_COST = 0;

	
	public ConcreteTechnology() {
		this(DEFAULT_NAME, DEFAULT_DESCRIPTION, DEFAULT_COST);
	}
	
	public ConcreteTechnology(String name, String description, int cost) {
		super(name, description, cost);
	}

	@Override
	protected void onResearch() {

	}

}