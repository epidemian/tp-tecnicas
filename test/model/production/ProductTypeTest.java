package model.production;

import java.util.ArrayList;

import model.core.BusinessLogicException;

import org.junit.Test;

public class ProductTypeTest {

	@Test(expected = BusinessLogicException.class)
	public void createProductTypeWithNullName() {
		new ProductType(null, new ArrayList<RawMaterial>());
	}
	
	@Test(expected = BusinessLogicException.class)
	public void createProductTypeWithNullRawMaterials() {
		new ProductType("product2", null);
	}
}
