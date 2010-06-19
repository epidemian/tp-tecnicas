package controller.game;

import model.game.Player;
import static model.utils.ArgumentUtils.*;
import model.production.elements.InputProductionLineElement;
import view.game.InputSelectionPanel;

public class InputSelectionPanelController {

    private InputProductionLineElement input;

    public InputSelectionPanelController(InputProductionLineElement input,
        InputSelectionPanel inputPanel, Player player){

        checkNotNull(input, "input");
        checkNotNull(inputPanel, "inputPanel");

        this.input = input;


    }
}
