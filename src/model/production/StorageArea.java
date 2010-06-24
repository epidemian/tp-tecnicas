package model.production;

import static model.utils.ArgumentUtils.checkNotNull;

import java.util.LinkedList;
import java.util.List;

import model.production.exception.NotEnoughRawMaterialException;

public class StorageArea {

	private List<Product> productsProduced;

	public List<Product> getProductsProduced() {
		return productsProduced;
	}

	private ValidProductionSequences validProductionSequences;

	/**
	 * Contains all the raw materials of the Warehouse
	 */
	private RawMaterials rawMaterials;

	public StorageArea(ValidProductionSequences validSequences) {
		this(new RawMaterials(), validSequences);
	}

	public StorageArea(RawMaterials rawMaterials,
			ValidProductionSequences validSequences) {
		this.setRawMaterials(rawMaterials);
		this.setValidProductionSequences(validSequences);
		this.productsProduced = new LinkedList<Product>();
	}

	public RawMaterials getRawMaterials() {
		return rawMaterials;
	}

	public void addProduct(Product product) {
		product.resolveProductType(this.validProductionSequences);
		if (!product.isWaste())
			this.productsProduced.add(product);
	}

	public int countDefectiveProducts() {

		int defectiveProducts = 0;

		for (Product entry : this.productsProduced) {
			if (entry.isDamaged())
				defectiveProducts++;
		}

		return defectiveProducts;
	}

	/**
	 * Creation of the product which will enter the inputStorage.
	 */
	public Product createProduct(RawMaterials inputRawMaterials) {

		if (inputRawMaterials.isEmpty())
			return null;
		try {
			this.rawMaterials.extract(inputRawMaterials);
		} catch (NotEnoughRawMaterialException e) {
			return null;
		}

		return new Product(inputRawMaterials);
	}

	public void storeRawMaterial(RawMaterialType rawMaterialType,
			int quantityStore) {
		this.rawMaterials.store(rawMaterialType, quantityStore);
	}

	private void setValidProductionSequences(
			ValidProductionSequences validProductionSequences) {
		checkNotNull(validProductionSequences, "validProductionSequences");
		this.validProductionSequences = validProductionSequences;
	}

	private void setRawMaterials(RawMaterials rawMaterials) {
		checkNotNull(rawMaterials, "rawMaterials");
		this.rawMaterials = rawMaterials;
	}

	public void clearProductsProduced() {
		this.productsProduced.clear();
	}
}