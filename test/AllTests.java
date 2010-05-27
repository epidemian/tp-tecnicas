import model.game.AllGameTests;
import model.lab.AllLabTests;
import model.production.AllProductionTests;
import model.warehouse.WarehouseTest;

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
	    WarehouseTest.class,
})
public class AllTests { }