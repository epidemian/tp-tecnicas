package controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.game.Game;
import view.game.GamePanel;
import view.game.LineElementsMarketPanel;
import view.game.RawMaterialsMarketPanel;
import view.game.ToolBarPanel;
import view.game.edition.EditionActions;
import view.game.edition.KeyInputActionMapper;

public class GamePanelController {

        private LineElementsMarketPanel lineElementsMarketPanel;
        private RawMaterialsMarketPanel rawMaterialsMarketPanel;

	public GamePanelController(Game game, final GamePanel gamePanel) {
            EditionActions editionActions = new EditionActions(gamePanel, game);
            KeyInputActionMapper mapper = new KeyInputActionMapper(editionActions);
            mapper.mapActions(gamePanel.getInputMap(), gamePanel.getActionMap());

            this.lineElementsMarketPanel = new LineElementsMarketPanel();
            new LineElementsMarketPanelController(game, 
                this.lineElementsMarketPanel, editionActions);

            this.rawMaterialsMarketPanel = new RawMaterialsMarketPanel();
            new RawMaterialsMarketPanelController(game,
                this.rawMaterialsMarketPanel,gamePanel);

            initToolBarPanelButtonsActions(gamePanel);
            gamePanel.getBudgetPanel().setMoneyBalance(game.getBudget().getBalance());
        }

    private void initToolBarPanelButtonsActions(final GamePanel gamePanel) {
        ToolBarPanel toolBar = gamePanel.getToolBarPanel();
        toolBar.addLineElementsMarketActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                gamePanel.setToolPanel(lineElementsMarketPanel);
            }
        });
        toolBar.addRawMaterialsMarketActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                gamePanel.setToolPanel(rawMaterialsMarketPanel);
            }
        });
    }

}
