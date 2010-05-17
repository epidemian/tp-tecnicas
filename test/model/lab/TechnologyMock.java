package model.lab;

/**
 * A dummy {@link Technology} that does noting when 
 * {@link Technology#research()} is called.
 */
class TechnologyMock extends Technology {

	public TechnologyMock(String name, String description, int cost,
			boolean researched) {
		super(name, description, cost, researched);
	}

	@Override
	protected void onResearch() {
		
	}
	
}