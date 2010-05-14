package tests;

/**
 * Test suite to run all test cases at once.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DummyTest.class,
        })
public class AllTests { }