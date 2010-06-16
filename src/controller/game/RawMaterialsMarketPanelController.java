package controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JSpinner;
import javax.swing.JTable;
import model.production.RawMaterialType;
import static model.utils.ArgumentUtils.*;
import model.production.StorageArea;
import view.game.RawMaterialsMarketPanel;

public class RawMaterialsMarketPanelController {

    private RawMaterialsMarketPanel marketPanel;
    private StorageArea storageArea;
    private static final String[] columnNames = {"Raw material Type","Quantity"};

    public RawMaterialsMarketPanelController(StorageArea storageArea,
            RawMaterialsMarketPanel marketPanel){

        checkNotNull(storageArea, "storageArea");
        checkNotNull(marketPanel, "marketPanel");

        this.storageArea = storageArea;
        this.marketPanel = marketPanel;

        initBuyButtonActionListener(marketPanel);


        JSpinner spinner = this.marketPanel.getRawMaterialSizeSpinner();
        


    }

    private void initBuyButtonActionListener(RawMaterialsMarketPanel marketPanel) {
        marketPanel.setBuyButtonActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                // TODO agregar la materia prima.
            }
        });
    }

    private void setTableData(){
        JTable table = this.marketPanel.getStockTable();
        Map<RawMaterialType,Integer> rawMaterials
            = this.storageArea.getRawMaterials().getRawMaterials();

    }

    private boolean canBuyRawMaterial(){
        return true;
    }

}
