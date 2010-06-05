package model.production;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StorageAreaTest {

	private StorageArea storageArea;
	
	@Before
	public void setUp(){
		storageArea = new StorageArea(TestUtils.createRawMaterials(2,1,10),
					  new ValidProductionSequences());
	}
	
	private List<Product> generateNonDefectiveProducts(){
				
		List<Product> products = new ArrayList<Product>();
		
		products.add(new Product(TestUtils.createRawMaterials(2, 1, 10)));
		products.add(new Product(TestUtils.createRawMaterials(2, 2, 10)));
		products.add(new Product(TestUtils.createRawMaterials(3, 1, 10)));
		products.add(new Product(TestUtils.createRawMaterials(3, 2, 10)));
		
		return products;
	}
	
	@Test
	public void countDefectiveWhenDefectiveProductsExists(){
	
		List<Product> products = generateNonDefectiveProducts();
		
		for (Product prod : products){
			prod.setDefective();
			this.storageArea.addProduct(prod);
		}
		
		assertEquals(products.size(),storageArea.countDefectiveProducts());
	}
	
	@Test
	public void countDefectiveWhenNoDefectiveProductsExists(){
		List<Product> products=generateNonDefectiveProducts();
		
		for (Product prod : products){
			this.storageArea.addProduct(prod);	
		}
		
		assertEquals(0,storageArea.countDefectiveProducts());
	}
	
}
