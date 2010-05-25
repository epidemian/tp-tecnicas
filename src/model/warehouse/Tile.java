package model.warehouse;

import static model.utils.ArgumentUtils.checkGreaterEqual;
import model.production.ProductionLineElement;

public class Tile {
	
	private int x;
	private int y;

	private ProductionLineElement lineElement;
	
	public Tile(int x, int y){
		this.setX(x);
		this.setY(y);
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public ProductionLineElement getLineElement() {
		return lineElement;
	}
	
	public void setLineElement(ProductionLineElement element) {
		this.lineElement = element;
	}
	
	private void setX(int x) {
		checkGreaterEqual(x,0,"x");
		this.x = x;
	}
	
	private void setY(int y) {
		checkGreaterEqual(y,0,"y");
		this.y = y;
	}
}
