package tests;

/**
 * A dummy test case to check out the new JUnit 4 syntax ;)
 */

import org.junit.*;
import static org.junit.Assert.*;

public class DummyTest {

	Object testedObject;
	
	@Before
	public void setUp() throws Exception {
		testedObject = new Object();
	}

	@After
	public void tearDown() throws Exception {
		// Do some cleaning up.
	}
	
	@Test
	public void testSomething() {
		Object other = new Object();
		assertNotSame(other, testedObject);
	}
	
}
