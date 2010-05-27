package model.production;

import model.exception.BusinessLogicException;
import org.junit.Test;
import static org.junit.Assert.*;

public class TypeTest {

	@Test(expected = BusinessLogicException.class)
	public void createProductTypeWithNullName(){
		new ProductType(null);
	}	
	
	@Test(expected = BusinessLogicException.class)
	public void createMachineTypeWithNullName(){
		new MachineType(null);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void createRawMaterialTypeWithNullName(){
		new RawMaterialType(null);
	}
	
	@Test
	public void equals(){
	
		AbstractType type1 = new ProductType("type");
		AbstractType type2 = new ProductType("type");
		AbstractType type3 = new ProductType("type");
		
		assertTrue(type1.equals(type2));
		assertTrue(type1.equals(type3));
	}
	
}
