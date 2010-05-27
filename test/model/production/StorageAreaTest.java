package model.production;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class StorageAreaTest {

	private StorageArea storageArea;
	
	@Before
	public void setUp(){
		storageArea = new StorageArea(Contexts.createRawMaterials(2,1,10));
	}
	
	private List<Product> generateNonDefectiveProducts(){
				
		List<Product> products = new ArrayList<Product>();
		
		products.add(new Product(Contexts.createRawMaterials(2, 1, 10)));
		products.add(new Product(Contexts.createRawMaterials(2, 2, 10)));
		products.add(new Product(Contexts.createRawMaterials(3, 1, 10)));
		products.add(new Product(Contexts.createRawMaterials(3, 2, 10)));
		
		return products;
	}
	
	@Test
	public void countDefectiveWhenDefectiveProductsExists(){
	
		List<Product> products = generateNonDefectiveProducts();
		
		for (Product prod : products){
			prod.setDefective();
			storageArea.addProduct(prod);
		}
		
		assertEquals(products.size(),storageArea.countDefectiveProducts());
	}
	
	@Test
	public void countDefectiveWhenNoDefectiveProductsExists(){
		List<Product> products=generateNonDefectiveProducts();
		
		for (Product prod : products){
			storageArea.addProduct(prod);	
		}
		
		assertEquals(0,storageArea.countDefectiveProducts());
	}
	
}
