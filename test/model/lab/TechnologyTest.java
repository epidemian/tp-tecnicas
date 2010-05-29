package model.lab;

import static org.junit.Assert.*;
import model.exception.BusinessLogicException;

import org.junit.Before;
import org.junit.Test;


public class TechnologyTest {

	private ConcreteTechnology technology;

	@Before
	public void setUp() {
		this.technology = new ConcreteTechnology();
	}
	
	@Test(expected = BusinessLogicException.class)
	public void createTechnologyWithNullName() {
		new ConcreteTechnology(null, "", 0);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void createTechnologyWithNullDescription() {
		new ConcreteTechnology("", null, 0);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void createTechnologyWithNegativeCost() {
		new ConcreteTechnology("", "", -1);
	}
	
	@Test
	public void researchUnresearchedTechnology() {
		this.technology.research();
	}

	@Test
	public void researchUnresearchedTechnologyAndTestIsResearched() {
		this.technology.research();
		assertTrue(this.technology.isResearched());
	}
	
	@Test(expected = BusinessLogicException.class)
	public void researchTechnologyTwice() {
		this.technology.research();
		this.technology.research();
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
		super("Hello World Tec.", "Says hello", 0);
		this.output = output;
	}
	
	@Override
	protected void onResearch() {
		this.output.append(MESSAGE);
	}
}