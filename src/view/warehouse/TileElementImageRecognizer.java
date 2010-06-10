package view.warehouse;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import model.production.Conveyor;
import model.production.ProductionMachine;
import model.production.QualityControlMachine;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;
import model.warehouse.Wall;

public abstract class TileElementImageRecognizer extends TileElementVisitor {

	/*
	 * TODO Image class manager soon =)
	 */
	private static BufferedImage IMG_CONVEYOR = loadImage("./conveyor.gif");
	private static BufferedImage IMG_QUALITY_CONTROL_MACHINE = loadImage("./qualityControlMachine.gif");
	private static BufferedImage IMG_PRODUCTION_MACHINE = loadImage("./productionMachine.gif");
	private static BufferedImage IMG_WALL = loadImage("./wall.gif");

	private static BufferedImage loadImage(String path) {

		BufferedImage image = null;
		try {
			URL url = GroundPanel.class.getClassLoader().getResource(path);
			image = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	protected abstract void onTileElmentVisited(TileElement element,
			BufferedImage image);

	@Override
	public void visitConveyor(Conveyor conveyor) {
		this.onTileElmentVisited(conveyor, IMG_CONVEYOR);
	}

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
}
