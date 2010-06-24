package model.production;

import static model.utils.ArgumentUtils.*;
import static model.utils.StringUtils.join;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.production.exception.NotEnoughRawMaterialException;

/**
 * Representation of the raw materials that can be used to create products.
 */
public class RawMaterials {

	public static final String NO_MATERIALS_PRETTY_STRING = "no materials";
	private Map<RawMaterialType, Integer> rawMaterials;

	public RawMaterials(Map<RawMaterialType, Integer> rawMaterials) {
		checkNotNull(rawMaterials, "rawMaterials");
		this.rawMaterials = rawMaterials;
	}

	public RawMaterials() {
		this(new HashMap<RawMaterialType, Integer>());
	}

	public void extract(RawMaterials rawMaterials)
			throws NotEnoughRawMaterialException {

		if (canExtract(rawMaterials)) {
			for (RawMaterialType key : rawMaterials.rawMaterials.keySet()) {
				Integer value = rawMaterials.rawMaterials.get(key);
				this.extract(key, value.intValue());
			}
		} else
			throw new NotEnoughRawMaterialException();
	}

	public void extract(RawMaterialType rawMaterialType, int quantityNeeded)
			throws NotEnoughRawMaterialException {

		validateQuantity(quantityNeeded);

		if (canExtract(rawMaterialType, quantityNeeded)) {
			Integer quantity = this.rawMaterials.get(rawMaterialType);
			this.rawMaterials.put(rawMaterialType, quantity.intValue()
					- quantityNeeded);
		} else
			throw new NotEnoughRawMaterialException();
	}

	public boolean canExtract(RawMaterials rawMaterials) {

		for (RawMaterialType key : rawMaterials.rawMaterials.keySet()) {
			int value = rawMaterials.getRawMaterialQuantity(key);

			if (!this.canExtract(key, value))
				return false;
		}

		return true;
	}

	public boolean canExtract(RawMaterialType rawMaterialType,
			int quantityNeeded) {

		int quantity = this.getRawMaterialQuantity(rawMaterialType);
		return quantity >= quantityNeeded;
	}

	public void store(RawMaterialType rawMaterialType, int quantityStore) {

		validateQuantity(quantityStore);

		int quantity = this.getRawMaterialQuantity(rawMaterialType);
		this.rawMaterials.put(rawMaterialType, quantityStore + quantity);
	}

	public void put(RawMaterialType rawMaterialType, int quantityStore) {

		validateQuantity(quantityStore);
		this.rawMaterials.put(rawMaterialType, quantityStore);
	}

	public void remove(RawMaterialType rawMaterialType) {
		this.rawMaterials.remove(rawMaterialType);
	}

	public int getRawMaterialQuantity(RawMaterialType rawMaterialType) {

		Integer quantity = this.rawMaterials.get(rawMaterialType);
		if (quantity != null)
			return quantity.intValue();
		else
			return 0;
	}

	private void validateQuantity(int quantity) {
		checkGreaterEqual(quantity, 0, "quantity");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((rawMaterials == null) ? 0 : rawMaterials.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "RawMaterials [rawMaterials=" + rawMaterials + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RawMaterials other = (RawMaterials) obj;
		if (rawMaterials == null) {
			if (other.rawMaterials != null)
				return false;
		} else if (!rawMaterials.equals(other.rawMaterials))
			return false;
		return true;
	}

	public String toPrettyString() {
		if (rawMaterials.isEmpty())
			return NO_MATERIALS_PRETTY_STRING;
		List<String> matStrings = new ArrayList<String>(rawMaterials.size());
		for (Entry<RawMaterialType, Integer> entry : rawMaterials.entrySet())
			matStrings.add(entry.getValue() + " " + entry.getKey());
		return join(matStrings, ", ");
	}

	public Map<RawMaterialType, Integer> getRawMaterials() {
		return Collections.unmodifiableMap(this.rawMaterials);
	}

	public boolean isEmpty() {
		return this.rawMaterials.isEmpty();
	}
}