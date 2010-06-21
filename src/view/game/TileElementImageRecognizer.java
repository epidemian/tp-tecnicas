package view.game;

import java.awt.image.BufferedImage;

import model.production.elements.Conveyor;
import model.production.elements.InputProductionLineElement;
import model.production.elements.OutputProductionLineElement;
import model.production.elements.machine.MachineType;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;
import model.warehouse.Wall;

public abstract class TileElementImageRecognizer extends TileElementVisitor {

	protected static final String IMG_WALL = "Wall.png";

	private static final String CONVEYOR_IMG_PREFIX = "./conveyor_";
	private static final String IMG_EXTENSION = ".png";

	private static final String INPUT_ELEMENT_PREFIX = "./input_";
	private static final String OUTPUT_ELEMENT_PREFIX = "./output_";

	protected abstract void onTileElmentVisited(TileElement element,
			BufferedImage image);

	@Override
	public void visitProductionMachine(ProductionMachine machine) {
		BufferedImage image = getMachineImage(machine.getMachineType());
		this.onTileElmentVisited(machine, image);
	}

	@Override
	public void visitQualityControlMachine(QualityControlMachine machine) {
		BufferedImage image = getMachineImage(machine.getMachineType());
		this.onTileElmentVisited(machine, image);
	}

	@Override
	public void visitWall(Wall wall) {
		this.onTileElmentVisited(wall, ImageLoader.getImage(IMG_WALL));
	}

	@Override
	public void visitConveyor(Conveyor conveyor) {
		this.onTileElmentVisited(conveyor, getConveyorImage(conveyor));
	}

	@Override
	public void visitInputProductionLineElement(
			InputProductionLineElement inputElement) {
		BufferedImage image = getInputElementImage(inputElement);
		this.onTileElmentVisited(inputElement, image );
	}

	@Override
	public void visitOutputProductionLineElement(
			OutputProductionLineElement outputElement) {
		BufferedImage image = getOutputElementImage(outputElement);
		this.onTileElmentVisited(outputElement, image);
	}
	
	public static BufferedImage getInputElementImage(
			InputProductionLineElement inputElement) {
		char symbol = inputElement.getOutputConnectionDirection().getSymbol();
		String imgName = INPUT_ELEMENT_PREFIX + symbol + IMG_EXTENSION;
		return ImageLoader.getImage(imgName);
	}

	public static BufferedImage getOutputElementImage(
			OutputProductionLineElement outputElement) {
		char symbol = outputElement.getInputConnectionDirection().getSymbol();
		String imgName = OUTPUT_ELEMENT_PREFIX + symbol + IMG_EXTENSION;
		return ImageLoader.getImage(imgName);
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
