package controller.game;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import model.game.Budget;
import model.game.Game;
import model.production.RawMaterialType;
import static model.utils.ArgumentUtils.*;
import model.production.StorageArea;
import view.game.GamePanel;
import view.game.RawMaterialsMarketPanel;

public class RawMaterialsMarketPanelController {

	private GamePanel gamePanel;
	private Budget budget;
	private RawMaterialsMarketPanel marketPanel;
	private StorageArea storageArea;
	private List<RawMaterialType> rawMaterialTypes;
	private Map<RawMaterialType, Integer> rawMaterialPrices;

	// Buying info.
	private RawMaterialType rawMaterialType;
	private SpinnerNumberModel rawMaterialQuantityModel;
	private int rawMaterialPrice;

	private static final String[] columnNames = { "Type", "Quantity" };

	public RawMaterialsMarketPanelController(Game game,
			RawMaterialsMarketPanel marketPanel, GamePanel gamePanel) {

		checkNotNull(game, "game");
		checkNotNull(marketPanel, "marketPanel");
		checkNotNull(gamePanel, "gamePanel");

		this.storageArea = game.getStorageArea();
		this.rawMaterialTypes = game.getRawMaterialTypes();
		this.rawMaterialPrices = game.getRawMaterialPrices();
		this.marketPanel = marketPanel;
		this.budget = game.getBudget();
		this.gamePanel = gamePanel;

		initBuyButtonActionListener();
		initQuantitySpinner();
		initBuyCombo();
		updateTableData();
	}

	private void initQuantitySpinner() {
		this.rawMaterialQuantityModel = new SpinnerNumberModel();

		final RawMaterialsMarketPanelController thisRef = this;
		final JSpinner quantitySpinner = this.marketPanel.getQuantitySpinner();
		quantitySpinner.setModel(this.rawMaterialQuantityModel);

		quantitySpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent ce) {
				thisRef.marketPanel.setRawMaterialPrice(thisRef
						.getRawMaterialPrice());
			}
		});
	}

	private void initBuyCombo() {
		final JComboBox buyCombo = this.marketPanel.getBuyCombo();
		final RawMaterialsMarketPanelController thisRef = this;

		buyCombo.removeAllItems();
		for (RawMaterialType mtype : this.rawMaterialTypes) {
			buyCombo.addItem(mtype.getName());
		}

		buyComboAction();

		buyCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent ie) {
				thisRef.buyComboAction();
			}
		});
	}

	/**
	 * Gets the raw material selected by the combo, and set the price label with
	 * the correct value.
	 */
	private void buyComboAction() {
		JComboBox buyCombo = this.marketPanel.getBuyCombo();
		int buyComboSelectedIndex = buyCombo.getSelectedIndex();
		this.rawMaterialType = this.rawMaterialTypes.get(buyComboSelectedIndex);
		this.rawMaterialPrice = this.rawMaterialPrices
				.get(this.rawMaterialType);
		this.marketPanel.setRawMaterialPrice(getRawMaterialPrice());
	}

	private void initBuyButtonActionListener() {
		final RawMaterialsMarketPanelController thisRef = this;
		this.marketPanel.setBuyButtonActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				int price = thisRef.getRawMaterialPrice();
				if (thisRef.canBuyRawMaterial()) {
					thisRef.budget.decrement(thisRef.getRawMaterialPrice());
					thisRef.gamePanel.getBudgetPanel().setMoneyBalance(
							thisRef.budget.getBalance());
					thisRef.storageArea.getRawMaterials().store(
							thisRef.rawMaterialType,
							thisRef.getRawMaterialQuantity());
					thisRef.updateTableData();
				}
			}
		});
	}

	private int getRawMaterialQuantity() {
		return ((Integer) this.rawMaterialQuantityModel.getValue());
	}

	private int getRawMaterialPrice() {
		return this.getRawMaterialQuantity() * this.rawMaterialPrice;
	}

	private void updateTableData() {
		JTable table = this.marketPanel.getStockTable();
		DefaultTableModel model = new DefaultTableModel();
		table.setModel(model);

		model.addColumn(columnNames[0]);
		model.addColumn(columnNames[1]);

		Map<RawMaterialType, Integer> rawMaterials = this.storageArea
				.getRawMaterials().getRawMaterials();

		for (Entry<RawMaterialType, Integer> entry : rawMaterials.entrySet())
			model.addRow(new Object[] { entry.getKey(), entry.getValue() });

		this.autoResizeColWidth(table, model);
	}

	/*
	 * Internet code.
	 */
	private void autoResizeColWidth(JTable table, DefaultTableModel model) {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setModel(model);

		int margin = 5;

		for (int i = 0; i < table.getColumnCount(); i++) {
			int vColIndex = i;
			DefaultTableColumnModel colModel = (DefaultTableColumnModel) table
					.getColumnModel();
			TableColumn col = colModel.getColumn(vColIndex);
			int width = 0;

			// Get width of column header
			TableCellRenderer renderer = col.getHeaderRenderer();

			if (renderer == null) {
				renderer = table.getTableHeader().getDefaultRenderer();
			}

			Component comp = renderer.getTableCellRendererComponent(table, col
					.getHeaderValue(), false, false, 0, 0);

			width = comp.getPreferredSize().width;

			// Get maximum width of column data
			for (int r = 0; r < table.getRowCount(); r++) {
				renderer = table.getCellRenderer(r, vColIndex);
				comp = renderer.getTableCellRendererComponent(table, table
						.getValueAt(r, vColIndex), false, false, r, vColIndex);
				width = Math.max(width, comp.getPreferredSize().width);
			}

			// Add margin
			width += 2 * margin;

			// Set the width
			col.setPreferredWidth(width);
		}

		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.LEFT);

		// table.setAutoCreateRowSorter(true);
		table.getTableHeader().setReorderingAllowed(false);
	}

	private boolean canBuyRawMaterial() {
        	return this.budget.canPurchase(this.getRawMaterialPrice());
	}

}
