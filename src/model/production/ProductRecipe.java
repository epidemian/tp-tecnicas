package model.production;

import static model.utils.ArgumentUtils.checkNotNull;

import java.util.List;

public class ProductRecipe {

	private ProductionSequence productionSequence;
	private List<RawMaterial> ingredients;
	
	public ProductRecipe(ProductionSequence productionSequence,
			List<RawMaterial> ingredients) {
		this.setProductionSequence(productionSequence);
		this.setIngredients(ingredients);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ingredients == null) ? 0 : ingredients.hashCode());
		result = prime
				* result
				+ ((productionSequence == null) ? 0 : productionSequence
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductRecipe other = (ProductRecipe) obj;
		if (ingredients == null) {
			if (other.ingredients != null)
				return false;
		} else if (!ingredients.equals(other.ingredients))
			return false;
		if (productionSequence == null) {
			if (other.productionSequence != null)
				return false;
		} else if (!productionSequence.equals(other.productionSequence))
			return false;
		return true;
	}

	public ProductionSequence getProductionSequence() {
		return productionSequence;
	}

	public List<RawMaterial> getIngredients() {
		return ingredients;
	}

	public void setProductionSequence(ProductionSequence productionSequence) {
		checkNotNull(productionSequence,"productionSequence");
		this.productionSequence = productionSequence;
	}

	public void setIngredients(List<RawMaterial> ingredients) {
		checkNotNull(ingredients,"ingredients");
		this.ingredients = ingredients;
	}
}
