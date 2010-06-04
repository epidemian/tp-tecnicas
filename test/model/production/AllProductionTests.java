package model.production;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	MachineStateTest.class,
	ProductionLinesCreatorTest.class,
	ProductionLineTest.class,
	ProductionMachineTest.class,
	ProductionSequenceTest.class,
	ProductTest.class,
	ProductTypeTest.class,
    RawMaterialsTest.class,
    StorageAreaTest.class,
    TypeTest.class,
    ValidProductionSequencesTest.class,
})
public class AllProductionTests {

}
