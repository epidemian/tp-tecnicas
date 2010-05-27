package model.lab.technologies;

import model.lab.Technology;
import model.production.ProductType;
import model.production.ProductionSequence;
import model.production.ValidProductionSequences;
import static model.utils.ArgumentUtils.*;

/**
 * A technology that adds a new valid product sequence when researched.
 */
public class NewProductionSequenceTechnology extends Technology {

	private ProductionSequence sequence;
	private ProductType productType;
	private ValidProductionSequences validSequences;

	// TODO: 7 parameters, Haaaaaa!... creational method??
	// Demian
	public NewProductionSequenceTechnology(ProductionSequence sequence,
			ProductType productType, ValidProductionSequences validSequences,
			String name, String description, int researchCost,
			boolean researched) {
		super(name, description, researchCost, researched);
		checkNotNull(sequence, "production sequence");
		checkNotNull(productType, "product type");
		checkNotNull(validSequences, "valid production sequences");
		this.sequence = sequence;
		this.productType = productType;
		this.validSequences = validSequences;
	}

	@Override
	protected void onResearch() {
		this.validSequences.addValidProductionSequence(this.sequence,
				this.productType);
	}
}
