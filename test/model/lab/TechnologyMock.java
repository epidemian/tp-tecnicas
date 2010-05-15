package model.lab;

class TechnologyMock extends Technology {

	public TechnologyMock(String name, String description, int cost,
			boolean researched) {
		super(name, description, cost, researched);
	}

	@Override
	protected void onResearch() {
		
	}
	
}