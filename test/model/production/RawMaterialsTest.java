package model.production;

import java.util.LinkedList;
import java.util.List;

import model.exception.BusinessLogicException;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class RawMaterialsTest {

	private int rawMaterialSize = 4;
	private int rawMaterialStandardQuantity = 10;
	
	private RawMaterials rawMaterial;
	private List<RawMaterialType> rawMaterialTypesInRawMaterials;
	private List<RawMaterialType> rawMaterialTypesThatAreNotInRawMaterials;
		
	@Before
	public void setUp() {
		
		this.rawMaterial = TestUtils.createRawMaterials(
				rawMaterialSize, 0, rawMaterialStandardQuantity);
		this.rawMaterialTypesInRawMaterials = TestUtils
			.createRawMaterialTypeList(rawMaterialSize, 0);
		this.rawMaterialTypesThatAreNotInRawMaterials = TestUtils
			.createRawMaterialTypeList(rawMaterialSize, rawMaterialSize+1);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void createRawMaterialsWithNullMap() {
		new RawMaterials(null);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void storeRawMaterialWithNegativeQuantity() 
		throws NotEnoughRawMaterialException {
				
		int quantityExtract = -10;
		this.rawMaterial.store(this.rawMaterialTypesInRawMaterials.get(0)
			, quantityExtract);
	}
	
	public void storeRawMaterialAuxiliary(RawMaterialType rawMaterialType,
			int quantityStore) throws NotEnoughRawMaterialException {
	
		int quantityBeforeStore = this.rawMaterial
			.getRawMaterialQuantity(rawMaterialType);
		
		this.rawMaterial.store(rawMaterialType, quantityStore);

		int quantityAfterStore = this.rawMaterial
			.getRawMaterialQuantity(rawMaterialType);
				
		assertTrue((quantityAfterStore - quantityStore) == quantityBeforeStore);
	}
	
	@Test
	public void storeRawMaterialThatIsNotInTheMap() 
		throws NotEnoughRawMaterialException {
		
		int quantityStore = 10;
		this.storeRawMaterialAuxiliary(this
			.rawMaterialTypesThatAreNotInRawMaterials.get(0), quantityStore);
	}
	
	@Test
	public void storeRawMaterialThatIsInTheMap() 
		throws NotEnoughRawMaterialException {
		
		int quantityStore = 10;
		this.storeRawMaterialAuxiliary(this
			.rawMaterialTypesInRawMaterials.get(0), quantityStore);
	}
	
	@Test
	public void canExtractRawMaterialMoreThanAvailable() {
		
		assertFalse(this.rawMaterial
			.canExtract(this.rawMaterialTypesInRawMaterials.get(0), 
			this.rawMaterialStandardQuantity + 1));
	}

	@Test
	public void canExtractRawMaterialAvailable() {
		
		assertTrue(this.rawMaterial
			.canExtract(this.rawMaterialTypesInRawMaterials.get(0), 
			this.rawMaterialStandardQuantity - 1));
	}
	
	@Test
	public void canExtractRawMaterialThatIsNotInTheMap() {
		
		assertFalse(this.rawMaterial
			.canExtract(this.rawMaterialTypesThatAreNotInRawMaterials.get(0),
			this.rawMaterialStandardQuantity));
	}
	
	public void extractRawMaterialAuxiliary(RawMaterialType rawMaterialType,
			int quantityExtract) throws NotEnoughRawMaterialException {
	
		int quantityBeforeStore = this.rawMaterial
			.getRawMaterialQuantity(rawMaterialType);
		
		this.rawMaterial.extract(rawMaterialType, quantityExtract);
	
		int quantityAfterStore = this.rawMaterial
			.getRawMaterialQuantity(rawMaterialType);
				
		assertTrue((quantityAfterStore + quantityExtract) 
				== quantityBeforeStore);
	}
	
	@Test(expected = NotEnoughRawMaterialException.class)
	public void extractRawMaterialMoreThanAvailable() 
		throws NotEnoughRawMaterialException {
		
		this.extractRawMaterialAuxiliary(this
			.rawMaterialTypesInRawMaterials.get(1),
			this.rawMaterialStandardQuantity + 1);
	}
	
	@Test
	public void extractRawMaterialAvailable() 
		throws NotEnoughRawMaterialException {
		
		this.extractRawMaterialAuxiliary(this
			.rawMaterialTypesInRawMaterials.get(1),
			this.rawMaterialStandardQuantity - 1);
	}	
	
	@Test(expected = BusinessLogicException.class)
	public void extractRawMaterialWithNegativeQuantity() 
		throws NotEnoughRawMaterialException {
		
		this.extractRawMaterialAuxiliary(this
			.rawMaterialTypesInRawMaterials.get(1),
			-this.rawMaterialStandardQuantity);
	}
	
	@Test (expected = NotEnoughRawMaterialException.class)
	public void extractRawMaterialsMoreThanAvailable() 
		throws NotEnoughRawMaterialException {
		
		RawMaterials rawMaterial = TestUtils.createRawMaterials(
			this.rawMaterialSize, 0, this.rawMaterialStandardQuantity + 1);
				
		this.rawMaterial.extract(rawMaterial);
	}
	
	@Test
	public void extractRawMaterialsAvailable() 
		throws NotEnoughRawMaterialException {
	
		int quantityExtract = this.rawMaterialStandardQuantity - 1;
		
		List<RawMaterialType> types = TestUtils.createRawMaterialTypeList(
			this.rawMaterialSize, 0);
		RawMaterials rawMaterial = TestUtils.createRawMaterials(
			this.rawMaterialSize, 0, quantityExtract);
		
		List<Integer> quantityBeforeStore = new LinkedList<Integer>();
		for (RawMaterialType entry : types){
			quantityBeforeStore.add(this.rawMaterial
				.getRawMaterialQuantity(entry));
		}
		
		this.rawMaterial.extract(rawMaterial);
			
		List<Integer> quantityAfterStore = new LinkedList<Integer>();
		for (RawMaterialType entry : types){
			quantityAfterStore.add(this.rawMaterial
				.getRawMaterialQuantity(entry));
		}
		
		for (int i = 0; i < types.size(); i++){
			assertTrue((quantityAfterStore.get(i) + quantityExtract) 
					== quantityBeforeStore.get(i));
		}
	}	
	
	@Test
	public void equalsTest(){
		
		RawMaterials rawMaterial = TestUtils.createRawMaterials(
			this.rawMaterialSize, 0, this.rawMaterialStandardQuantity);
			
		assertEquals(this.rawMaterial, rawMaterial);
	}
	
	@Test
	public void equalsTestUsingStore(){
	
		RawMaterials rawMaterial = new RawMaterials();
		
		List<RawMaterialType> types = TestUtils.createRawMaterialTypeList(
			this.rawMaterialSize, 0);
		
		for (RawMaterialType entry : types){
			rawMaterial.store(entry, rawMaterialStandardQuantity);
		}
			
		assertEquals(this.rawMaterial, rawMaterial);
	}
}
