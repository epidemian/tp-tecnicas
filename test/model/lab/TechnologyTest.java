package model.lab;

import org.junit.*;
import static org.junit.Assert.*;
import model.exception.BusinessLogicException;
import model.lab.Technology;


public class TechnologyTest {

	
	@Test(expected = BusinessLogicException.class)
	public void createTechnologyWithNullName() {
		new TechnologyMock(null, "", 0, false);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void createTechnologyWithNullDescription() {
		new TechnologyMock("", null, 0, false);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void createTechnologyWithNegativeCost() {
		new TechnologyMock("", "", -1, false);
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

	private Technology createResearchedTecnology() {
		return new TechnologyMock("Name", "Desc", 0, true);
	}
	
	private Technology createUnresearchedTecnology() {
		return new TechnologyMock("Name", "Desc", 0, false);
	}
	
}

class HelloWorldTechnology extends TechnologyMock {

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