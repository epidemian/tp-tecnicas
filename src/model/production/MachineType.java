package model.production;

public class MachineType extends AbstractType {

	private int height;
	private int width;
	private int price;
	
	
	

	/*
	 * TODO borrar!
	 */
	public MachineType(String name){
		super(name);
		this.height=0;
		this.width=0;
		this.price=0;
	}
	

	public MachineType(String name,int width,int height) {
		super(name);
		this.height = height;
		this.width = width;
		this.price=0;
	}
	
	public MachineType(String name, int height, int width, int price) {
		super(name);
		this.height = height;
		this.width = width;
		this.price = price;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}


	@SuppressWarnings("unused")
	private void setPrice(int price) {
		this.price = price;
	}


	public int getPrice() {
		return price;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + height;
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
		if (price != other.price)
			return false;
		if (width != other.width)
			return false;
		return true;
	}
	
	
}
