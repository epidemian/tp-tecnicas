package view.warehouse;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import model.production.ProductionLineElement;
import model.warehouse.TileElement;

public class LineElementButton extends JButton {

	private static final long serialVersionUID = 1L;

	/*
	 * TODO If we make the buttons no resizable this size is pretty good. We can
	 * discuss this. I found a way to do it with the size of the button,
	 * resizing if it is necessary but it was too slow!
	 */
	private final static Dimension IMAGE_SIZE = new Dimension(50, 35);

	public ProductionLineElement lineElement;
	public BufferedImage image;

	public LineElementButton() {
		this(null);
	}

	public LineElementButton(ProductionLineElement lineElement) {
		this.setProductionLineElement(lineElement);
	}

	public void setProductionLineElement(ProductionLineElement lineElement) {

		boolean nullLineElementType = lineElement == null;
		if (!nullLineElementType) {

			this.lineElement = lineElement;

			/*
			 * This visitor is used to get the image from the line element.
			 */
			this.lineElement.accept(new TileElementImageRecognizer() {
				@Override
				protected void onTileElmentVisited(TileElement element,
						BufferedImage image) {

					// TODO Check not null!
					LineElementButton.this.image = image;
					LineElementButton.this.setIconFromBufferedImage();
				}
			}); // End Visitor.

		}
		this.setVisible(!nullLineElementType);
	}

	public BufferedImage getImage() {
		return this.image;
	}

	public ProductionLineElement getProductionLineElement() {
		return this.lineElement;
	}

	@Override
	public String toString() {
		return "LineElementButton [lineElement=" + lineElement + "]";
	}

	private void setIconFromBufferedImage() {

		Image scaleImage = new ImageIcon(this.image).getImage()
				.getScaledInstance(IMAGE_SIZE.width, IMAGE_SIZE.height,
						Image.SCALE_SMOOTH);

		this.setIcon(new ImageIcon(scaleImage));
	}
}
