package view.warehouse;

import java.awt.Dimension;
import java.awt.Image;
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
			
			  // TODO Scale image!
                          this.lineElement.accept(new
			  TileElementImageRecognizer() {
			  
                            @Override protected void onTileElmentVisited(TileElement element,
                            BufferedImage image) {

                              Dimension dimension = LineElementButton.this.getSize();

                              if (dimension.width != 0 && dimension.height != 0){
                              ImageIcon imageButton = new ImageIcon(image);
                              Image imageIcon = imageButton.getImage();
                              Image scaleImageIcon = imageIcon.getScaledInstance(dimension.width,dimension.height, Image.SCALE_SMOOTH);
                              imageButton.setImage(scaleImageIcon);

                              LineElementButton.this.setIcon(imageButton);
                              }
                            }

                          });
			 
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
