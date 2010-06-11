package model.production;

import static model.utils.ArgumentUtils.checkGreaterEqual;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

import model.warehouse.Position;

public class MachineType extends AbstractType {

	private int height;
	private int width;
	private int price;

	private Position inputRelativePosition;
	private Position outputRelativePosition;

	public static final int DEFECT_MACHINE_PRICE=0;
	/*
	 * TODO borrar!
	 */
	public MachineType(String name) {
		this(name, 1, 1);
	}

	/*
	 * TODO borrar!
	 */
	public MachineType(String name, int width, int height) {
		this(name, width, height, new Position(0,-1),new Position(0,width),
				MachineType.DEFECT_MACHINE_PRICE);
	}

	/*
	public MachineType(String name, int height,int width,int price){
		this(name, width, height, price,new Position(0,-1),new Position(0,width));
		
	}
	*/
	
	public MachineType(String name, int height, int width, 
			Position inputRelativePosition, Position outputRelativePosition,int price) {
		super(name);
		this.height = height;
		this.width = width;
		this.price = price;
		this.inputRelativePosition = inputRelativePosition;
		this.outputRelativePosition = outputRelativePosition;
	}
	
	/*
	public static MachineType createMachineTypeWithPriceAndInputOutputPositions
		(String name, int height, int width, int price,
			Position inputRelativePosition, Position outputRelativePosition){
		MachineType type = 
				new MachineType(name,height,width,price,inputRelativePosition,
						outputRelativePosition);
		return type;
		
	}
	
	public static MachineType createMachineTypeInputOutputPositions
	(String name, int height, int width,
			Position inputRelativePosition, Position outputRelativePosition){
		MachineType type = 
				new MachineType(name,height,width,0,inputRelativePosition,
						outputRelativePosition);
		return type;
	}
	*/

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getPrice() {
		return price;
	}

	public Position getInputRelativePosition() {
		return inputRelativePosition;
	}

	public Position getOutputRelativePosition() {
		return outputRelativePosition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + height;
		result = prime
				* result
				+ ((inputRelativePosition == null) ? 0 : inputRelativePosition
						.hashCode());
		result = prime
				* result
				+ ((outputRelativePosition == null) ? 0
						: outputRelativePosition.hashCode());
		result = prime * result + price;
		result = prime * result + width;
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
		MachineType other = (MachineType) obj;
		if (height != other.height)
			return false;
		if (inputRelativePosition == null) {
			if (other.inputRelativePosition != null)
				return false;
		} else if (!inputRelativePosition.equals(other.inputRelativePosition))
			return false;
		if (outputRelativePosition == null) {
			if (other.outputRelativePosition != null)
				return false;
		} else if (!outputRelativePosition.equals(other.outputRelativePosition))
			return false;
		if (price != other.price)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

}
