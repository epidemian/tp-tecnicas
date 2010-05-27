package model.production;

import model.exception.BusinessLogicException;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class ValidProductionSequencesTest {

	private ValidProductionSequences validSequences;

	private List<ProductionSequence> sequencesInTheValidSequences;
	private List<ProductType> productTypes;

	private ProductionSequence nonValidProductionSequence;
	
	@Before
	public void setUp() {
		this.validSequences = new ValidProductionSequences();
		
		sequencesInTheValidSequences
			= new LinkedList<ProductionSequence>();
		
		this.sequencesInTheValidSequences.add(Contexts
			.createProductionSequence(3,0,2,0,40));
		this.sequencesInTheValidSequences.add(Contexts
			.createProductionSequence(3,0,2,4,40));
		this.sequencesInTheValidSequences.add(Contexts
			.createProductionSequence(4,0,8,4,40));
		this.sequencesInTheValidSequences.add(Contexts
			.createProductionSequence(5,0,8,4,40));
	
		productTypes = Contexts.createProductTypeList(
			this.sequencesInTheValidSequences.size(), 0);
		
		for (int i = 0; i < this.productTypes.size(); i++){
			this.validSequences.addValidProductionSequence(
				this.sequencesInTheValidSequences.get(i), 
				this.productTypes.get(i));
		}
		
		nonValidProductionSequence = Contexts
			.createProductionSequence(10,5,20,20,40);		
	}

	/*
	 * For the first two sequences:
	 * 		The same sequence of machines but with different configuration
	 *		of rawMaterials.  
	 */
	@Test
	public void identifyProduct(){
		
		for (int i = 0; i < this.productTypes.size(); i++){
			assertEquals(this.productTypes.get(i), this.validSequences
				.identifyProductType(this.sequencesInTheValidSequences.get(i)));
		}
	}
	
	@Test
	public void identifyProductType(){
	
		
	}

	@Test
	public void	
	identifyProductTypeOfANonValidProductionSequenceExpectingWaste(){
	
		ProductType type = this.validSequences.identifyProductType(
			this.nonValidProductionSequence);
		assertEquals(ProductType.getWaste(), type);
	}

	@Test(expected = BusinessLogicException.class)
	public void addValidProductionSequenceWithNullProductionSequence(){
		this.validSequences.addValidProductionSequence(
				null,this.productTypes.get(0));
	}
	
	@Test(expected = BusinessLogicException.class)
	public void addValidProductionSequenceWithNullProductType(){
		
		this.validSequences.clear();
		this.validSequences.addValidProductionSequence(
			this.sequencesInTheValidSequences.get(0),null);
	}
	
	@Test
	public void addValidProductionSequenceAndAnalizeThatItIsNotEmpty(){
		
		this.validSequences.clear();
		assertTrue(this.validSequences.isEmpty());
		
		this.validSequences.addValidProductionSequence(
			this.sequencesInTheValidSequences.get(0),
			this.productTypes.get(0));
	
		assertFalse(this.validSequences.isEmpty());
	}
	
	@Test(expected = BusinessLogicException.class)
	public void addSameProductionSequenceTwice() {
		this.validSequences.clear();
		ProductionSequence sequence = this.sequencesInTheValidSequences.get(0);
		ProductType productType = this.productTypes.get(0);
		
		this.validSequences.addValidProductionSequence(sequence, productType);
		assertTrue(true);
		this.validSequences.addValidProductionSequence(sequence, productType);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void addTwoEqualProductionSequences() {
		this.validSequences.clear();
		ProductionSequence sequence1 = Contexts.createProductionSequence(3, 0,
				2, 0, 40);
		ProductionSequence sequence2 = Contexts.createProductionSequence(3, 0,
				2, 0, 40);
		ProductType productType = this.productTypes.get(0);

		this.validSequences.addValidProductionSequence(sequence1, productType);
		assertTrue(true);
		this.validSequences.addValidProductionSequence(sequence2, productType);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void addSameProductionSequenceTwiceForTwoDifferentProductTypes() {
		this.validSequences.clear();
		ProductionSequence sequence = this.sequencesInTheValidSequences.get(0);
		ProductType productType1 = this.productTypes.get(0);
		ProductType productType2 = this.productTypes.get(1);
		
		this.validSequences.addValidProductionSequence(sequence, productType1);
		assertTrue(true);
		this.validSequences.addValidProductionSequence(sequence, productType2);
	}
}
