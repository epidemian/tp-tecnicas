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

	public NewProductionSequenceTechnology(ProductionSequence sequence,
			ProductType productType, ValidProductionSequences validSequences,
			int researchCost) {
		super(checkNotNull(productType, "product type").getName(),
				createDescription(productType, checkNotNull(sequence,
						"production sequence")), researchCost, false);
		checkNotNull(validSequences, "valid production sequences");
		this.sequence = sequence;
		this.productType = productType;
		this.validSequences = validSequences;
	}

	private static String createDescription(ProductType productType,
			ProductionSequence sequence) {
		return "Develop " + productType.getName() + " with "
				+ sequence.toPrettyString();
	}

	@Override
	protected void onResearch() {
		this.validSequences.addValidProductionSequence(this.sequence,
				this.productType);
	}
}
