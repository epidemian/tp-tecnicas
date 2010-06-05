package model.production;

import static org.junit.Assert.*;

import java.util.List;

import model.exception.BusinessLogicException;

import org.junit.Before;
import org.junit.Test;

public class ProductTest {

	private Product product;
	
	@Before
	public void setUp() {
		this.product = new Product(new RawMaterials());
	}
	
	@Test(expected = BusinessLogicException.class)
	public void addNullMachineToProductsHistory(){
		this.product.addMachineTypeToHistory(null);
	}
	
	@Test
	public void isDamaged(){
		assertFalse(this.product.isDamaged());
	}
	
	@Test
	public void setDefectiveAndAnalizeThatIsDamaged(){
		this.product.setDefective();
		assertTrue(this.product.isDamaged());
	}
	
	@Test(expected = BusinessLogicException.class)
	public void getProductTypeBeforeResolvingProductType(){
		this.product.getProductType();
	}
	
	@Test
	public void resolveProductTypeAndAnalizeThatTheProductTypeIsCorrect(){
		
		ValidProductionSequences validSequences 
			= new ValidProductionSequences();
		ProductType type = new ProductType("prod1");
		
		validSequences.addValidProductionSequence(TestUtils
			.createProductionSequence(3,0,2,0,40),type);
		
		Product product = new Product(TestUtils.createRawMaterials(2,0,40));
		
		List<MachineType> machines = TestUtils.createMachineTypeList(3,0);		
		
		for (MachineType entry : machines){
			product.addMachineTypeToHistory(entry);
		}
		
		product.resolveProductType(validSequences);

		assertEquals(type, product.getProductType());
	}
}
