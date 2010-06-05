package model.lab.technologies;

import static model.utils.ArgumentUtils.checkNotNull;
import model.lab.Technology;
import model.production.ProductType;
import model.production.ProductionSequence;
import model.production.ValidProductionSequences;

/**
 * A technology that adds a new valid product sequence when researched.
 */
public class NewProductionSequenceTechnology extends Technology {

	private ProductionSequence sequence;
	private ProductType productType;
	private ValidProductionSequences validSequences;

	/**
	 * TODO: As description could well be derived from the product type and the
	 * production sequence, like:
	 * <p>
	 * description = "Develop " + productType.getName() + " with materials: " +
	 * sequence.getRawMaterials().toPrettyString() + " and machines: "
	 * sequence.getLineMachinesPrettyString()
	 * <p>
	 * It might be useful to create an entity that takes care of that. I assume
	 * that it wouldn't be cool to put that logic here because the presentation
	 * words "develop", "with", "machines", etc, would be hard-coded into this
	 * model class.
	 */
	public NewProductionSequenceTechnology(ProductionSequence sequence,
			ProductType productType, ValidProductionSequences validSequences,
			String description, int researchCost) {
		super(checkNotNull(productType, "product type").getName(), description,
				researchCost);
		checkNotNull(validSequences, "valid production sequences");
		checkNotNull(sequence, "production sequence");
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
