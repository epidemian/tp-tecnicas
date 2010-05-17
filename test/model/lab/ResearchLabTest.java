package model.lab;

import model.core.BusinessLogicException;

import org.junit.*;
import static org.junit.Assert.*;

public class ResearchLabTest {

	private static final int MAX_FUNDING = 100;
	private ResearchLab lab;

	@Before
	public void setUp() {
		this.lab = new ResearchLab(new TechnologyTree(), MAX_FUNDING);
	}
	
	@Test
	public void initialDailyFundigIsZero() {
		assertEquals(0, lab.getDailyFunding());
	}
	
	@Test
	public void setDailyFundigToMax() {
		lab.setDailyFunding(MAX_FUNDING);
		assertEquals(MAX_FUNDING, lab.getDailyFunding());
	}
	
	@Test(expected = BusinessLogicException.class)
	public void setDailyFundingLowerThanZero() {
		lab.setDailyFunding(-1);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void setDailyFundingGreaterThanMax() {
		lab.setDailyFunding(MAX_FUNDING + 1);
	}
	
	
}
