package view.warehouse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.production.Conveyor;
import model.production.ProductionMachine;
import model.production.QualityControlMachine;
import model.warehouse.Ground;
import model.warehouse.GroundVisitor;
import model.warehouse.TileElement;
import model.warehouse.Wall;

import java.util.ArrayList;
import java.util.List;

public class GroundPanel extends JScrollPane {

	private static final long serialVersionUID = 1L;

	public GroundPanel(Ground ground) {

		/*
		 * Specifies scroll-bars policies. The scrolls will be shown ALWAYS.
		 * They can also be specified with NEEDED OR NEVER.
		 */
		super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		this.setViewportView(new DrawingPanel(ground));
	}

	/*
	 * The component inside the scroll pane.
	 */
	private class DrawingPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private Ground ground;

		/*
		 * TODO Do not hard-code tileSize =(.
		 */
		private int tileSize = 75;

		public DrawingPanel(Ground ground) {
			this.ground = ground;
			/*
			 * TODO Do not hard-code Color.white =(.
			 */
			this.setBackground(Color.white);
			/*
			 * This a scroll-able panel. The scroll can be used in the area.
			 */
			setPreferredSize(this.getGroundSize());
		}

		private Dimension getGroundSize() {
			return new Dimension(this.ground.getCols() * this.tileSize,
					this.ground.getRows() * this.tileSize);
		}

		protected void paintComponent(Graphics g) {

			super.paintComponent(g);

			Dimension groundSizeInTiles = new Dimension(this.ground.getCols(),
					this.ground.getRows());

			// Vertical lines.
			for (int col = 0; col <= groundSizeInTiles.width; col++) {

				int x1 = col * this.tileSize;
				int y1 = 0;
				int x2 = col * this.tileSize;
				int y2 = groundSizeInTiles.height * this.tileSize;

				g.drawLine(x1, y1, x2, y2);
			}

			// Horizontal lines.
			for (int row = 0; row <= groundSizeInTiles.height; row++) {

				int x1 = 0;
				int y1 = row * this.tileSize;
				int x2 = groundSizeInTiles.width * this.tileSize;
				int y2 = row * this.tileSize;

				g.drawLine(x1, y1, x2, y2);
			}

			ElementPainter elementPainter = new ElementPainter(g);
			this.ground.visitElements(elementPainter);
		}

		/*
		 * Visitor used to draw each element in the warehouse.
		 */
		private class ElementPainter extends GroundVisitor {

			public Graphics graphics;
			private List<TileElement> bigTouchedTiles;

			public ElementPainter(Graphics g) {
				this.bigTouchedTiles = new ArrayList<TileElement>();
				this.graphics = g;
			}

			private void drawElement(TileElement element, BufferedImage image) {
				/*
				 * Checks if 'element' is contained in the touchedTiles list. If
				 * it is not, adds the element to the list (if size is bigger
				 * than one tile, because is the only way that can try to draw
				 * it twice.)
				 */
				if (!this.bigTouchedTiles.contains(element)) {
					if (element.getWidth() > 1 || element.getHeight() > 1)
						this.bigTouchedTiles.add(element);

					int x = getCurrentPosition().col * tileSize;
					int y = getCurrentPosition().row * tileSize;
					int width = element.getWidth() * tileSize;
					int height = element.getHeight() * tileSize;

					this.graphics.drawImage(image, x, y, width, height, null);
				}
			}

			@Override
			public void visitConveyor(Conveyor conveyor) {
				this.drawElement(conveyor, this.conveyor);
			}

			@Override
			public void visitProductionMachine(ProductionMachine machine) {
				this.drawElement(machine, this.productionMachineImg);
			}

			@Override
			public void visitQualityControlMachine(QualityControlMachine machine) {
				this.drawElement(machine, this.qualityControlMachineImg);
			}

			@Override
			public void visitWall(Wall wall) {
				this.drawElement(wall, this.wallImg);
			}

			/*
			 * TODO Image class manager soon =)
			 */
			private BufferedImage conveyor = this.loadImage("./conveyor.gif");
			private BufferedImage qualityControlMachineImg = this
					.loadImage("./qualityControlMachine.gif");
			private BufferedImage productionMachineImg = this
					.loadImage("./productionMachine.gif");
			private BufferedImage wallImg = this.loadImage("./wall.gif");

			private BufferedImage loadImage(String path) {

				BufferedImage image = null;

				try {
					URL url = getClass().getClassLoader().getResource(path);
					image = ImageIO.read(url);
				} catch (Exception e) {
					e.printStackTrace();
				}

				return image;
			}
		} // End Visitor.
	}
}