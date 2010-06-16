package view.game;

import java.awt.image.BufferedImage;

import model.production.elements.Conveyor;
import model.production.elements.machine.MachineType;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;
import model.warehouse.Wall;

public abstract class TileElementImageRecognizer extends TileElementVisitor {

	private static final String IMG_WALL = "wall.gif";

	private static final String CONVEYOR_IMG_PREFIX = "./conveyor_";
	private static final String IMG_EXTENSION = ".png";

	protected abstract void onTileElmentVisited(TileElement element,
			BufferedImage image);

	@Override
	public void visitProductionMachine(ProductionMachine machine) {
		this.onTileElmentVisited(machine, getMachineImage(machine.getMachineType()));
	}

	@Override
	public void visitQualityControlMachine(QualityControlMachine machine) {
		this.onTileElmentVisited(machine, getMachineImage(machine.getMachineType()));
	}

	@Override
	public void visitWall(Wall wall) {
		this.onTileElmentVisited(wall, ImageLoader.getImage(IMG_WALL));
	}

	@Override
	public void visitConveyor(Conveyor conveyor) {
		this.onTileElmentVisited(conveyor, getConveyorImage(conveyor));
	}

	public static BufferedImage getConveyorImage(Conveyor conveyor) {
		char prevSymbol = conveyor.getInputConnectionDirection().getSymbol();
		char nextSymbol = conveyor.getOutputConnectionDirection().getSymbol();
		String imgName = CONVEYOR_IMG_PREFIX + prevSymbol + nextSymbol
				+ IMG_EXTENSION;
		return ImageLoader.getImage(imgName);
	}
	
	public static BufferedImage getMachineImage(MachineType mtype){
		String imgName = mtype.getName() + IMG_EXTENSION;
		return ImageLoader.getImage(imgName);
	}
}
