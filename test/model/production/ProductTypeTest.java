package model.production;

import model.exception.BusinessLogicException;

import org.junit.Test;

public class ProductTypeTest {

	@Test(expected = BusinessLogicException.class)
	public void createProductTypeWithNullRawMaterials() {
		new ProductType("product2", null);
	}
}
