package persistence;

import model.warehouse.Position;
import model.warehouse.Wall;

public class WallItem {
	private Wall wall;
	private Position pos;
	
	public WallItem(Wall wall, Position pos) {
		super();
		this.setWall(wall);
		this.setPos(pos);
	}

	public void setWall(Wall wall) {
		this.wall = wall;
	}

	public Wall getWall() {
		return wall;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public Position getPos() {
		return pos;
	}
	
	
}
