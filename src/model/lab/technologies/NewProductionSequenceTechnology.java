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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((productType == null) ? 0 : productType.hashCode());
		result = prime * result
				+ ((sequence == null) ? 0 : sequence.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewProductionSequenceTechnology other = (NewProductionSequenceTechnology) obj;
		if (productType == null) {
			if (other.productType != null)
				return false;
		} else if (!productType.equals(other.productType))
			return false;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		return true;
	}

}
