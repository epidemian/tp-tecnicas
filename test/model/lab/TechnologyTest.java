package model.lab;

import org.junit.*;
import static org.junit.Assert.*;
import model.core.BusinessLogicException;
import model.lab.Technology;


public class TechnologyTest {

	
	@Test
	public void researchNotResearchedTechnology() {
		createTecnology(false).research();
	}
	
	@Test(expected = BusinessLogicException.class)
	public void researchAlreadyResearchedTechnology() {
		createTecnology(true).research();
	}
	
	@Test(expected = BusinessLogicException.class)
	public void researchTechnologyTwice() {
		Technology t = createTecnology(false);
		t.research();
		t.research();
	}
	
	@Test
	public void testOnResearchEffect() {
		StringBuffer message = new StringBuffer();
		Technology helloWorldTec = new HelloWorldTechnology(message);
		helloWorldTec.research();
		assertEquals(HelloWorldTechnology.MESSAGE, message.toString());
	}

	private Technology createTecnology(boolean researched) {
		return new TechnologyMock(researched);
	}
	
}


class TechnologyMock extends Technology {

	public TechnologyMock(boolean researched) {
		super("Name", "Description", 0, researched);
	}

	@Override
	protected void onResearch() {
		
	}
	
}

class HelloWorldTechnology extends TechnologyMock {

	static final Object MESSAGE = "Hello World!";
	private StringBuffer output;

	public HelloWorldTechnology(StringBuffer output) {
		super(false);
		this.output = output;
	}
	
	@Override
	protected void onResearch() {
		this.output.append(MESSAGE);
	}
}