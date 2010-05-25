package model.production;

import java.util.HashMap;
import java.util.Map;

import model.exception.BusinessLogicException;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class RawMaterialsTest {

	private RawMaterials rawMaterial;
	
	private RawMaterialType rawMaterialType1;
	private RawMaterialType rawMaterialType2;
	private RawMaterialType rawMaterialType3;
	private RawMaterialType rawMaterialType4;	
	private RawMaterialType rawMaterialType5;	
	
	@Before
	public void setUp() {
		
		Map<RawMaterialType,Integer> rawMaterials 
			= new HashMap<RawMaterialType, Integer>();
		
		rawMaterialType1 = new RawMaterialType("mat1");
		rawMaterialType2 = new RawMaterialType("mat2");
		rawMaterialType3 = new RawMaterialType("mat3");
		rawMaterialType4 = new RawMaterialType("mat4");
		rawMaterialType5 = new RawMaterialType("mat5");
	
		rawMaterials.put(rawMaterialType1, 10);
		rawMaterials.put(rawMaterialType2, 10);
		rawMaterials.put(rawMaterialType3, 10);
				
		this.rawMaterial = new RawMaterials(rawMaterials);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void createRawMaterialsWithNullMap() {
		new RawMaterials(null);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void storeRawMaterialWithNegativeQuantity() 
		throws NotEnoughRawMaterialException {
		
		int quantityExtract = -10;
		this.rawMaterial.store(this.rawMaterialType1, quantityExtract);
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
		this.storeRawMaterialAuxiliary(this.rawMaterialType4, quantityStore);
	}
	
	@Test
	public void storeRawMaterialThatIsInTheMap() 
		throws NotEnoughRawMaterialException {
		
		int quantityStore = 10;
		this.storeRawMaterialAuxiliary(this.rawMaterialType1, quantityStore);
	}
	
	@Test
	public void canExtractRawMaterialMoreThanAvailable() {
		
		int quantityExtract = 100;
		assertFalse(this.rawMaterial
			.canExtract(this.rawMaterialType1, quantityExtract));
	}

	@Test
	public void canExtractRawMaterialAvailable() {
		
		int quantityExtract = 8;
		assertTrue(this.rawMaterial
			.canExtract(this.rawMaterialType1, quantityExtract));
	}
	
	@Test
	public void canExtractRawMaterialThatIsNotInTheMap() {
		
		int quantityExtract = 100;
		assertFalse(this.rawMaterial
			.canExtract(this.rawMaterialType5, quantityExtract));
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
		
		int quantityExtract = 100;
		this.extractRawMaterialAuxiliary(this.rawMaterialType2,
				quantityExtract);
	}
	
	@Test
	public void extractRawMaterialAvailable() 
		throws NotEnoughRawMaterialException {
		
		int quantityExtract = 1;
		this.extractRawMaterialAuxiliary(this.rawMaterialType3,
				quantityExtract);
	}	
	
	@Test(expected = BusinessLogicException.class)
	public void extractRawMaterialWithNegativeQuantity() 
		throws NotEnoughRawMaterialException {
		
		int quantityExtract = -10;
		this.extractRawMaterialAuxiliary(this.rawMaterialType1,
				quantityExtract);
	}
	
	@Test (expected = NotEnoughRawMaterialException.class)
	public void extractRawMaterialsMoreThanAvailable() 
		throws NotEnoughRawMaterialException {
		
		Map<RawMaterialType,Integer> rawMaterialsNeeded 
			= new HashMap<RawMaterialType, Integer>();
		
		rawMaterialsNeeded.put(rawMaterialType1, 5);
		rawMaterialsNeeded.put(rawMaterialType2, 5);
		rawMaterialsNeeded.put(rawMaterialType3, 15);
				
		this.rawMaterial.extract(new RawMaterials(rawMaterialsNeeded));
	}
	
	@Test
	public void extractRawMaterialsAvailable() 
		throws NotEnoughRawMaterialException {
		
		Map<RawMaterialType,Integer> rawMaterialsNeeded 
			= new HashMap<RawMaterialType, Integer>();
		
		int quantityExtract1 = 2;
		int quantityExtract2 = 2;
		int quantityExtract3 = 2;
		
		rawMaterialsNeeded.put(rawMaterialType1, quantityExtract1);
		rawMaterialsNeeded.put(rawMaterialType2, quantityExtract2);
		rawMaterialsNeeded.put(rawMaterialType3, quantityExtract3);
		
		int quantityBeforeStore1 = this.rawMaterial
			.getRawMaterialQuantity(rawMaterialType1);
		int quantityBeforeStore2 = this.rawMaterial
			.getRawMaterialQuantity(rawMaterialType2);
		int quantityBeforeStore3 = this.rawMaterial
			.getRawMaterialQuantity(rawMaterialType3);
	
		this.rawMaterial.extract(new RawMaterials(rawMaterialsNeeded));
		
		int quantityAfterStore1 = this.rawMaterial
			.getRawMaterialQuantity(rawMaterialType1);
		int quantityAfterStore2 = this.rawMaterial
			.getRawMaterialQuantity(rawMaterialType2);
		int quantityAfterStore3 = this.rawMaterial
			.getRawMaterialQuantity(rawMaterialType3);
			
		assertTrue((quantityAfterStore1 + quantityExtract1) 
				== quantityBeforeStore1);
		assertTrue((quantityAfterStore2 + quantityExtract2) 
				== quantityBeforeStore2);
		assertTrue((quantityAfterStore3 + quantityExtract3) 
				== quantityBeforeStore3);
	}	
}
