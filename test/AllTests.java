

import model.lab.TechnologyTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite to run all test cases at once.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DummyTest.class,
        TechnologyTest.class,
        })
public class AllTests { }