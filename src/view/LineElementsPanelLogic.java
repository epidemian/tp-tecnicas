package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import model.production.ProductionLineElement;
import model.warehouse.TileElement;

public class LineElementsPanelLogic {

	// The machine created by clicking in a button.
	private ProductionLineElement lineElement;
	// List of machines that can be created.
	private List<ProductionLineElement> lineElements;
	// Buttons of the panel.
	private List<LineElementButton> lineElementButtons;
	// Number of buttons permitted.
	private int lineElementButtonsSize = 15;
	// Determinate the tab of the panel that has the buttons.
	private int lineElementsTab = 0;
	// Panel used to preview the line element.
	private BackGroundPanel lineElementPreview;

	public LineElementsPanelLogic(List<ProductionLineElement> lineElements,
			List<LineElementButton> lineElementButtons,
			JButton previousMachines, JButton nextMachines,
			BackGroundPanel lineElementPreview) {

		this.lineElements = lineElements;
		this.lineElementButtons = lineElementButtons;
		this.lineElementPreview = lineElementPreview;

		this.setUpLineElementButtons();

		// Init action listeners.
		previousMachines.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				LineElementsPanelLogic.this.decreseLineElementsTab();
			}
		});

		nextMachines.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				LineElementsPanelLogic.this.increseLineElementsTab();
			}
		});

		// TODO Refactor!
		for (final LineElementButton button : this.lineElementButtons) {
			button.addActionListener(new ActionListener() {

				/*
				 * Sets the lineElement store in this class with the one that is
				 * contained in the button.
				 */
				@Override
				public void actionPerformed(ActionEvent ae) {
					LineElementsPanelLogic.this.setProductionLineElement(button
							.getProductionLineElement());

					button.getProductionLineElement().accept(
							new TileElementImageRecognizer() {

								/*
								 * Visitor used to select the image in the
								 * lineElementPreview.
								 */
								@Override
								protected void onTileElmentVisited(
										TileElement element, BufferedImage image) {
									LineElementsPanelLogic.this.lineElementPreview
											.setImage(image);
									LineElementsPanelLogic.this.lineElementPreview
											.repaint();
								}
							});

				}
			});
		} // End for.
	}

	public void increseLineElementsTab() {
		int lineElementsSize = this.lineElements.size();
		if (lineElementsSize / this.lineElementButtonsSize >= this.lineElementsTab + 1) {
			this.lineElementsTab++;
		}
		this.setUpLineElementButtons();
	}

	public void decreseLineElementsTab() {
		if (this.lineElementsTab - 1 >= 0) {
			this.lineElementsTab--;
		}
		this.setUpLineElementButtons();
	}

	public ProductionLineElement getLineElement() {
		// TODO clone the object!
		return this.lineElement;
	}

	private void setProductionLineElement(ProductionLineElement lineElement) {
		// Check not null.
		this.lineElement = lineElement;
	}

	private void setUpLineElementButtons() {
		int startIndex = this.lineElementButtonsSize * this.lineElementsTab;
		int lastIndex = this.lineElements.size() >= startIndex
				+ this.lineElementButtonsSize ? startIndex
				+ this.lineElementButtonsSize : this.lineElements.size();

		System.out.println("Inicio: " + startIndex);
		System.out.println("Fin: " + lastIndex);

		int j = 0;
		for (int i = startIndex; i < lastIndex; i++, j++) {
			this.lineElementButtons.get(j).setProductionLineElement(
					this.lineElements.get(i));
		}
		for (; j < this.lineElementButtonsSize; j++) {
			this.lineElementButtons.get(j).setVisible(false);
		}

	}

	@Override
	public String toString() {
		return "LineElementsPanelLogic [lineElement=" + lineElement
				+ ", lineElementButtons=" + lineElementButtons
				+ ", lineElementButtonsSize=" + lineElementButtonsSize
				+ ", lineElements=" + lineElements + ", lineElementsTab="
				+ lineElementsTab + "]";
	}
}
