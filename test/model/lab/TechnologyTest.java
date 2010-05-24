package model.lab;

import org.junit.*;
import static org.junit.Assert.*;
import model.exception.BusinessLogicException;
import model.lab.Technology;
import static model.lab.ConcreteTechnology.*;


public class TechnologyTest {

	
	@Test(expected = BusinessLogicException.class)
	public void createTechnologyWithNullName() {
		new ConcreteTechnology(null, "", 0, false);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void createTechnologyWithNullDescription() {
		new ConcreteTechnology("", null, 0, false);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void createTechnologyWithNegativeCost() {
		new ConcreteTechnology("", "", -1, false);
	}
	
	@Test
	public void researchNotResearchedTechnology() {
		createUnresearchedTecnology().research();
	}
	
	@Test(expected = BusinessLogicException.class)
	public void researchAlreadyResearchedTechnology() {
		createResearchedTecnology().research();
	}
	
	@Test
	public void researchNotResearchedTechnologyAndTestIsResearched() {
		Technology t = createUnresearchedTecnology();
		t.research();
		assertTrue(t.isResearched());
	}
	
	@Test(expected = BusinessLogicException.class)
	public void researchTechnologyTwice() {
		Technology t = createUnresearchedTecnology();
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
	
}

class HelloWorldTechnology extends Technology {

	static final Object MESSAGE = "Hello World!";
	private StringBuffer output;

	public HelloWorldTechnology(StringBuffer output) {
		super("Hello World Tec.", "Says hello", 0, false);
		this.output = output;
	}
	
	@Override
	protected void onResearch() {
		this.output.append(MESSAGE);
	}
}