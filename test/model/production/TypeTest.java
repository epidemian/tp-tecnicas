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

		assertEquals(type1, type1);
		assertEquals(type1, type2);
		assertEquals(type2, type1);
	}
	
	@Test
	public void notEquals(){
	
		AbstractType type1 = new ProductType("type1");
		AbstractType type2 = new ProductType("type2");
		
		assertFalse(type1.equals(type2));
		assertFalse(type2.equals(type1));
	}
	
	
}
