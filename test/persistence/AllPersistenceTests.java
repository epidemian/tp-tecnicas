package persistence;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;



@RunWith(Suite.class)
@SuiteClasses({
		GroundListPersistentTest.class,
        GroundPersistentTest.class,
        MachineTypeListPersistentTest.class,
        MachineTypePersistentTest.class,
        ProductTypePersistentTest.class,
        RawMaterialsPersistentTest.class,
        PriceMapPersistentTest.class

})
public class AllPersistenceTests {

}

