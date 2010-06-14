package view.warehouse;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import model.production.Conveyor;
import model.production.Direction;
import model.production.MachineType;
import model.production.ProductionMachine;
import model.production.QualityControlMachine;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;
import model.warehouse.Wall;

public abstract class TileElementImageRecognizer extends TileElementVisitor {

	/*
	 * TODO Image class manager soon =)
	 */
	private static final BufferedImage IMG_QUALITY_CONTROL_MACHINE = loadImage("./qualityControlMachine.gif");
	private static final BufferedImage IMG_PRODUCTION_MACHINE = loadImage("./productionMachine.gif");
	private static final BufferedImage IMG_WALL = loadImage("./wall.gif");

	private static final String CONVEYOR_IMG_PREFIX = "./conveyor_";
	private static final String IMG_EXTENSION = ".png";

	private static Map<String, BufferedImage> machineImages = new HashMap<String, BufferedImage>();
	private static Map<String, BufferedImage> conveyorImages = new HashMap<String, BufferedImage>();

	private static BufferedImage loadImage(String path) {
		BufferedImage image = null;
		try {
			URL url = TileElementImageRecognizer.class.getClassLoader()
					.getResource(path);
			image = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	protected abstract void onTileElmentVisited(TileElement element,
			BufferedImage image);

	@Override
	public void visitProductionMachine(ProductionMachine machine) {
		this.onTileElmentVisited(machine, IMG_PRODUCTION_MACHINE);
	}

	@Override
	public void visitQualityControlMachine(QualityControlMachine machine) {
		this.onTileElmentVisited(machine, IMG_QUALITY_CONTROL_MACHINE);
	}

	@Override
	public void visitWall(Wall wall) {
		this.onTileElmentVisited(wall, IMG_WALL);
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

		if (!conveyorImages.containsKey(imgName))
			conveyorImages.put(imgName, loadImage(imgName));
		return conveyorImages.get(imgName);
	}
	
	public static BufferedImage getMachineImage(MachineType mtype){
		String imgName = mtype.getName() + IMG_EXTENSION;
		
		if (!machineImages.containsKey(imgName))
			machineImages.put(imgName, loadImage(imgName));
		return machineImages.get(imgName);
	}
}
