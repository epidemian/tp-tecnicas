package controller.game;

import model.game.Game;
import view.game.GamePanel;
import view.game.LineElementsMarketPanel;
import view.game.edition.EditionActions;
import view.game.edition.KeyInputActionMapper;

public class GamePanelController {

        private LineElementsMarketPanel lineElementsMarketPanel;

	public GamePanelController(Game game, GamePanel gamePanel) {
            EditionActions editionActions = new EditionActions(gamePanel, game);
            KeyInputActionMapper mapper = new KeyInputActionMapper(editionActions);
            mapper.mapActions(gamePanel.getInputMap(), gamePanel.getActionMap());

            this.lineElementsMarketPanel = new LineElementsMarketPanel();
            new LineElementsMarketPanelController(game, 
                this.lineElementsMarketPanel, editionActions);

            gamePanel.getBudgetPanel().setMoneyBalance(game.getBudget().getBalance());
            gamePanel.setToolPanel(this.lineElementsMarketPanel);
        }

}
