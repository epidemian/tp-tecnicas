package model.production;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        RawMaterialsTest.class,
        StorageAreaTest.class,
        ValidProductionSequencesTest.class,
        TypeTest.class,
        ProductTypeTest.class,
        ProductionSequenceTest.class,
        ProductTest.class,
        ProductionLineTest.class,
})
public class AllProductionTests {

}
