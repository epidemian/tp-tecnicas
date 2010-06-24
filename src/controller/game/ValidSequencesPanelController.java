package controller.game;

import static model.utils.ArgumentUtils.checkNotNull;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import view.game.ValidSequencesPanel;
import view.game.ViewUtils;
import model.game.Player;
import model.production.ProductType;
import model.production.ProductionSequence;
import model.production.RawMaterialType;
import model.production.ValidProductionSequences;
import model.production.elements.machine.MachineType;

public class ValidSequencesPanelController implements Refreshable {

	private JComboBox productsCombo;
	private JTable rawMaterialsTable;
	private JList lineMachinesList;
	private Player player;

	private static final String[] columnNames = { "Raw material", "Quantity" };

	public ValidSequencesPanelController(Player player,
			ValidSequencesPanel validSequencesPanel) {

		checkNotNull(player, "player");
		checkNotNull(validSequencesPanel, "valisSequencesPanel");

		this.player = player;
		this.productsCombo = validSequencesPanel.getValidSequencesCombo();
		this.rawMaterialsTable = validSequencesPanel
				.getRawMaterialNeededTable();
		this.lineMachinesList = validSequencesPanel.getSequencesList();

		initValidSequencesCombo();
	}

	private void initValidSequencesCombo() {

		this.updateValidSequenceCombo();

		this.productsCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent ie) {
				productsComboAction();
			}
		});
	}

	private void updateValidSequenceCombo(){

		ValidProductionSequences validProductionSequences = player
		.getValidProductionSequences();

		this.productsCombo.removeAllItems();
		
		Map<ProductionSequence, ProductType> validSequences = validProductionSequences
				.getValidProductionSequencesMap();
		
		for (Entry<ProductionSequence, ProductType> entry : validSequences
				.entrySet()) {
			this.productsCombo.addItem(new ValidSequenceComboEntry(entry
					.getKey(), entry.getValue()));
		}
		
		productsComboAction();
	}
	
	private void productsComboAction() {

		ValidSequenceComboEntry entry = (ValidSequenceComboEntry) this.productsCombo
				.getSelectedItem();

		if (entry != null){
		ProductionSequence productionSequence = entry
				.getProductionSequence();

		this.fillSequenceList(productionSequence);
		this.fillRawMaterialsTable(productionSequence);
		}
		
	}

	private void fillSequenceList(ProductionSequence productionSequence) {

		this.lineMachinesList.removeAll();

		DefaultListModel model = new DefaultListModel();
		this.lineMachinesList.setModel(model);

		for (MachineType mtype : productionSequence.getLineMachines())
			model.addElement(mtype);

		this.lineMachinesList.setModel(model);
	}

	private void fillRawMaterialsTable(ProductionSequence productionSequence) {

		DefaultTableModel model = new DefaultTableModel();
		this.rawMaterialsTable.setModel(model);

		model.addColumn(columnNames[0]);
		model.addColumn(columnNames[1]);

		Map<RawMaterialType, Integer> rawMaterials = productionSequence
				.getRawMaterials().getRawMaterials();

		for (Entry<RawMaterialType, Integer> entry : rawMaterials.entrySet())
			model.addRow(new Object[] { entry.getKey(), entry.getValue() });

		ViewUtils.autoResizeColWidth(this.rawMaterialsTable, model);
	}

	private class ValidSequenceComboEntry {

		private ProductionSequence productionSequence;;
		private ProductType productType;

		public ValidSequenceComboEntry(ProductionSequence productionSequence,
				ProductType productType) {

			checkNotNull(productionSequence, "productionSequence");
			checkNotNull(productType, "productType");

			this.productionSequence = productionSequence;
			this.productType = productType;
		}

		public ProductionSequence getProductionSequence() {
			return this.productionSequence;
		}

		@Override
		public String toString() {
			return this.productType.getName();
		}
	}

	@Override
	public void refresh() {
		this.updateValidSequenceCombo();
	}
}
