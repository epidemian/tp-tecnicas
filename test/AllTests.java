import model.game.AllGameTests;
import model.lab.AllLabTests;
import model.production.AllProductionTests;
import model.utils.AllUtilsTests;
import model.warehouse.AllWarehouseTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite to run all test cases at once.
 */
@RunWith(Suite.class)
@SuiteClasses({
        AllLabTests.class,
        AllProductionTests.class,
        AllGameTests.class,
	    AllWarehouseTests.class,
	    AllUtilsTests.class,
	    
})
public class AllTests { }