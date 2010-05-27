import model.game.time.TimeManagerTest;
import model.lab.NewProductionSequenceTechnologyTest;
import model.lab.ResearchLabTest;
import model.lab.TechnologyTest;
import model.lab.TechnologyTreeTest;
import model.production.ProductTest;
import model.production.ProductTypeTest;
import model.production.ProductionSequenceTest;
import model.production.RawMaterialsTest;
import model.production.StorageAreaTest;
import model.production.TypeTest;
import model.production.ValidProductionSequencesTest;

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
        NewProductionSequenceTechnologyTest.class,
        RawMaterialsTest.class,
        TimeManagerTest.class,
        StorageAreaTest.class,
        ValidProductionSequencesTest.class,
        TypeTest.class,
        ProductTypeTest.class,
        ProductionSequenceTest.class,
        ProductTest.class
})
public class AllTests { }