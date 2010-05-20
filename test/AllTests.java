

import model.lab.ResearchLabTest;
import model.lab.TechnologyTest;
import model.lab.TechnologyTreeTest;
import model.production.ProductType;
import model.production.ProductTypeTest;
import model.production.RawMaterialTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite to run all test cases at once.
 */
@RunWith(Suite.class)
@SuiteClasses({
        TechnologyTest.class,
        TechnologyTreeTest.class,
        ResearchLabTest.class,
        ProductTypeTest.class,
        RawMaterialTest.class
        })
public class AllTests { }