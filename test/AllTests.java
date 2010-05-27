import model.game.time.TimeManagerTest;
import model.lab.ResearchLabTest;
import model.lab.TechnologyTest;
import model.lab.TechnologyTreeTest;
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
        RawMaterialsTest.class,
        TimeManagerTest.class,
        StorageAreaTest.class,
        ValidProductionSequencesTest.class,
        TypeTest.class,
        ProductionSequenceTest.class
})
public class AllTests { }