package view.warehouse;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import model.production.ProductionLineElement;
import model.warehouse.TileElement;

public class LineElementButton extends JButton {

	public ProductionLineElement lineElement;

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
			 * TODO Scale image! this.lineElement.accept(new
			 * TileElementImageRecognizer() {
			 * 
			 * @Override protected void onTileElmentVisited(TileElement element,
			 * BufferedImage image) { LineElementButton.this.setIcon(new
			 * ImageIcon(image)); } });
			 */
		}
		this.setVisible(!nullLineElementType);
	}

	public ProductionLineElement getProductionLineElement() {
		return this.lineElement;
	}

	@Override
	public String toString() {
		return "LineElementButton [lineElement=" + lineElement + "]";
	}
}
