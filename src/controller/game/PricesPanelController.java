package controller.game;

import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static model.utils.ArgumentUtils.*;
import model.game.Player;
import model.warehouse.MarketPrices;
import view.game.PricesPanel;
import view.game.ViewUtils;

public class PricesPanelController implements Refreshable {

    private MarketPrices prices;
    private JTable table;

    private static final String[] columnNames = { "Product", "Price" };

    public PricesPanelController(PricesPanel pricesPanel, Player player){

        checkNotNull(pricesPanel, "pricesPanel");
        checkNotNull(player, "player");

        this.prices = player.getMarketPrices();
        this.table = pricesPanel.getPricesTable();

        updateTableData();
    }

    private void updateTableData() {
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);

        model.addColumn(columnNames[0]);
        model.addColumn(columnNames[1]);

        Map<String, Integer> mapPrices = this.prices.getMap();

        for (Entry<String, Integer> entry : mapPrices.entrySet())
                model.addRow(new Object[] { entry.getKey(), entry.getValue() });

        ViewUtils.autoResizeColWidth(table, model);
    }

    @Override
    public void refresh() {
        this.updateTableData();
    }
}
