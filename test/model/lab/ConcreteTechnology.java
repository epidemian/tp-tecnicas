package model.lab;

/**
 * A concrete {@link Technology} that does noting when
 * {@link Technology#research()} is called.
 */
class ConcreteTechnology extends Technology {

	private static final String DEFAULT_NAME = "Name";
	private static final String DEFAULT_DESCRIPTION = "Desc";
	private static final int DEFAULT_COST = 0;

	public ConcreteTechnology(String name, String description, int cost,
			boolean researched) {
		super(name, description, cost, researched);
	}

	@Override
	protected void onResearch() {

	}

	public static Technology createResearchedTecnology() {
		return new ConcreteTechnology(DEFAULT_NAME, DEFAULT_DESCRIPTION,
				DEFAULT_COST, true);
	}

	public static Technology createUnresearchedTecnology() {
		return new ConcreteTechnology(DEFAULT_NAME, DEFAULT_DESCRIPTION,
				DEFAULT_COST, false);
	}

}