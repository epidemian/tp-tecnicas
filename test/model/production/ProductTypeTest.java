package model.production;

import static org.junit.Assert.assertEquals;
import model.exception.BusinessLogicException;

import org.junit.Test;

public class ProductTypeTest {

	@Test(expected = BusinessLogicException.class)
	public void createWithWasteName() {
		String wasteName = ProductType.getWaste().getName();
		new ProductType(wasteName);
	}
	
	@Test
	public void twoWastesAreEqual() {
		assertEquals(ProductType.getWaste(), ProductType.getWaste());
	}
	
}
