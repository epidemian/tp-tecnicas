package model.production;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class StorageAreaTest {

	private StorageArea storageArea;
	
	private RawMaterials rawMaterials;
	
	private RawMaterialType rawMaterialType1;
	private RawMaterialType rawMaterialType2;
	
	private List<Product> productsProduced;
	
	@Before
	public void setUp(){
		
		//Creation of the Storage Area containing several RawMaterials
		Map<RawMaterialType,Integer> mapMaterials 
		= new HashMap<RawMaterialType, Integer>();
		
		rawMaterialType1=new RawMaterialType("type1");
		rawMaterialType2=new RawMaterialType("type2");
		
		mapMaterials.put(this.rawMaterialType1, 10);
		mapMaterials.put(this.rawMaterialType2, 10);
		
		rawMaterials=new RawMaterials(mapMaterials);
		productsProduced=new ArrayList<Product>();
		
		storageArea=new StorageArea(rawMaterials);
		
	}
	
	private List<Product> generateNonDefectiveProducts(){
		
		List<Product> products=new ArrayList<Product>();
		
		Map<RawMaterialType,Integer> mapMaterialForProducts
		= new HashMap<RawMaterialType, Integer>();
		mapMaterialForProducts.put(rawMaterialType1, 1);
		mapMaterialForProducts.put(rawMaterialType2, 1);
		
		Product prodA=new Product(new RawMaterials(mapMaterialForProducts));
		Product prodB=new Product(new RawMaterials(mapMaterialForProducts));
		Product prodC=new Product(new RawMaterials(mapMaterialForProducts));
		Product prodD=new Product(new RawMaterials(mapMaterialForProducts));
		
		
		products.add(prodA);
		products.add(prodB);
		products.add(prodC);
		products.add(prodD);
		
		return products;
		
	}
	
	@Test
	public void countDefectiveWhenDefectiveProductsExists(){
	
		List<Product> products=generateNonDefectiveProducts();
		
		for (Product prod : products){
			storageArea.addProduct(prod);
			prod.setDefective();
			prod.resolveProductType();
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
