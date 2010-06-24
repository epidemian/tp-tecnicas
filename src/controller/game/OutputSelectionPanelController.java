package controller.game;

import static model.utils.ArgumentUtils.checkNotNull;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.game.Player;
import model.production.elements.OutputProductionLineElement;
import view.game.OutputSelectionPanel;
import view.game.ViewUtils;

public class OutputSelectionPanelController implements Refreshable {

	private JTable table;
	private OutputProductionLineElement output;
	
	private static final String[] columnNames = { "Day", "Production" };

	public OutputSelectionPanelController(OutputProductionLineElement output,
			OutputSelectionPanel outputPanel, Player player) {

		checkNotNull(output, "output");
		checkNotNull(outputPanel, "outputPanel");
		checkNotNull(player, "player");
		
		this.output = output;
		this.table = outputPanel.getDailyProductionTable();

		updateTableData();
	}

	private void updateTableData() {
		DefaultTableModel model = new DefaultTableModel();
		table.setModel(model);

		model.addColumn(columnNames[0]);
		model.addColumn(columnNames[1]);

		List<Integer> dailyProduction = this.output.getDailyProductionHistory();
		
		for (int i = 0; i < dailyProduction.size(); i++)
			model.addRow(new Object[] { i, dailyProduction.get(i) });

		ViewUtils.autoResizeColWidth(table, model);
	}

	@Override
	public void refresh() {
		this.updateTableData();
	}

}
