package model.production;

import java.util.ArrayList;

import model.core.BusinessLogicException;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ProductTest {

	private Product product;

	@Before
	public void setUp() {
		this.product = new Product(new ProductType("product"
				, new ArrayList<RawMaterial>()));
	}
	
	@Test(expected = BusinessLogicException.class)
	public void createProductWithNullProductType() {
		new Product(null);
	}
	
	@Test
	public void breakProduct(){
		this.product.setDefective();
		assertTrue(this.product.isDamaged());
	}	
}
