package model.production;

import static model.utils.ArgumentUtils.checkArgCondition;

/**
 * Specifies a product type.
 */
public class ProductType extends AbstractType {

	private static final String WASTE_NAME = "waste";

	public static ProductType getWaste() {
		return new ProductType(WASTE_NAME, false);
	}

	public ProductType(String name) {
		this(name, true);
	}

	private ProductType(String name, boolean validateName) {
		super(name);
		boolean invalidName = validateName && name.equals(WASTE_NAME);
		checkArgCondition(name, !invalidName,
				"cannot create product with name \"" + WASTE_NAME + "\"");
	}

	public boolean isWaste() {
		return this.equals(getWaste());
	}
}
