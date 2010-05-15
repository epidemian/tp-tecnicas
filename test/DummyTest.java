

import org.junit.*;
import static org.junit.Assert.*;

/**
 * A dummy test case to check out the new JUnit 4 syntax ;)
 */
public class DummyTest {

	Object testedObject;
	
	@Before
	public void setUp() {
		testedObject = new Object();
	}

	@After
	public void tearDown() {
		// Do some cleaning up.
	}
	
	@Test
	public void testSomething() {
		Object other = new Object();
		assertNotSame(other, testedObject);
	}
	
}
