package view.warehouse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.production.Conveyor;
import model.production.ProductionMachine;
import model.production.QualityControlMachine;
import model.warehouse.Ground;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;
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

	private Point getViewPortPosition() {
		return new Point(this.getHorizontalScrollBar().getValue(), this
				.getVerticalScrollBar().getValue());
	}

	/*
	 * The component inside the scroll pane.
	 */
	private class DrawingPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private Ground ground;
		private GUIvisitor visitor;

		/*
		 * TODO Do not hard-code tileSize =(.
		 */
		private int tileSize = 75;

		public DrawingPanel(Ground ground) {
			this.ground = ground;
			this.visitor = new GUIvisitor();
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

			Point viewPort = getViewPortPosition();
			Dimension groundSizeInTiles = new Dimension(this.ground.getCols(),
					this.ground.getRows());
			Dimension dimension = getSize();

			this.visitor.graphics = g;

			/*
			 * CLIPPING! =) Efficiency above everything else!
			 * 
			 * Determines the index in which the ground begins to draw based on
			 * the position of the viewport.
			 */
			/*
			 * double startIndexTileX = viewPort.x / (double) this.tileSize;
			 * double startIndexTileY = viewPort.y / (double) this.tileSize;
			 * 
			 * double widthScreenInTiles = dimension.width / (double)
			 * this.tileSize; double heightScreenInTiles = dimension.height /
			 * (double) this.tileSize;
			 * 
			 * int lastIndexTileX = (groundSizeInTiles.width <=
			 * widthScreenInTiles + startIndexTileX) ? groundSizeInTiles.width :
			 * (int) (startIndexTileX + widthScreenInTiles + 1.0f);
			 * 
			 * int lastIndexTileY = (groundSizeInTiles.height <=
			 * heightScreenInTiles + startIndexTileY) ? groundSizeInTiles.height
			 * : (int) (startIndexTileY + heightScreenInTiles + 1.0f);
			 */
			int startIndexTileX = 0;
			int startIndexTileY = 0;
			int lastIndexTileX = groundSizeInTiles.width;
			int lastIndexTileY = groundSizeInTiles.height;

			// Vertical lines.
			for (int i = (int) startIndexTileX; i <= lastIndexTileX; i++) {
				g.drawLine(i * this.tileSize, (int) startIndexTileY
						* this.tileSize, i * this.tileSize,
						(int) lastIndexTileY * this.tileSize);
			}

			// Horizontal lines.
			for (int i = (int) startIndexTileY; i <= lastIndexTileY; i++) {
				g.drawLine((int) startIndexTileX * this.tileSize, i
						* this.tileSize, (int) lastIndexTileX * this.tileSize,
						i * this.tileSize);
			}

			/*
			 * int cast to start before (or equals) the position of the
			 * viewport.
			 */
			for (int i = (int) startIndexTileX; i < lastIndexTileX; i++)
				for (int j = (int) startIndexTileY; j < lastIndexTileY; j++) {
					this.visitor.currentCol = i;
					this.visitor.currentRow = j;
					TileElement element = this.ground.getTile(j, i)
							.getTileElement();
					if (element != null)
						element.accept(this.visitor);
				}

			this.visitor.flush();
		}

		/*
		 * Visitor used to draw each element in the warehouse.
		 */
		private class GUIvisitor implements TileElementVisitor {

			/*
			 * GUIvisito state! GUIvisitos is a private class, no one outside
			 * GroundPanel can see it. To simplify things, we use public
			 * attributes.
			 */
			public int currentRow;
			public int currentCol;
			public Graphics graphics;

			private List<TileElement> bigTouchedTiles;

			public GUIvisitor() {
				this.bigTouchedTiles = new ArrayList<TileElement>();
				this.currentCol = 0;
				this.currentRow = 0;
				this.graphics = null;
			}

			/*
			 * Clears the list.
			 */
			public void flush() {
				this.bigTouchedTiles.clear();
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
					/*
					 * TODO Add element size!
					 */
					this.graphics.drawImage(image, this.currentCol * tileSize,
							this.currentRow * tileSize, tileSize
									* element.getWidth(), tileSize
									* element.getHeight(), null);
				}
			}

			@Override
			public void visitConveyor(Conveyor conveyor) {
				this.drawElement(conveyor, this.conveyor);
			}

			@Override
			public void visitProductionMachine(ProductionMachine machine) {
				this.drawElement(machine, this.productionMachine);
			}

			@Override
			public void visitQualityControlMachine(QualityControlMachine machine) {
				this.drawElement(machine, this.qualityControlMachine);
			}

			@Override
			public void visitWall(Wall wall) {
				this.drawElement(wall, this.wall);
			}

			/*
			 * TODO Image class manager soon =)
			 */
			private BufferedImage conveyor = this.loadImage("./conveyor.gif");
			private BufferedImage qualityControlMachine = this
					.loadImage("./qualityControlMachine.gif");
			private BufferedImage productionMachine = this
					.loadImage("./productionMachine.gif");
			private BufferedImage wall = this.loadImage("./wall.gif");

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