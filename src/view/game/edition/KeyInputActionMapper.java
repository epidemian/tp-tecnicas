package view.game.edition;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import model.production.MachineType;
import model.warehouse.Position;

public class KeyInputActionMapper {

	// TODO: Delete this...
	private static final MachineType MACHINE_TYPE = new MachineType(
			"Big Machine", 3, 2, new Position(0, -1), new Position(1, 3), 250);

	private static final String ESC_PRESSED = "esc-pressed";
	private static final String M_PRESSED = "m-pressed";
	private static final Object S_PRESSED = "s-pressed";
	private static final Object C_PRESSED = "c-pressed";

	private EditionActions editionActions;

	public KeyInputActionMapper(EditionActions editionActions) {
		this.editionActions = editionActions;
	}

	public void mapActions(InputMap inputMap, ActionMap actionMap) {
		inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), ESC_PRESSED);
		inputMap.put(KeyStroke.getKeyStroke("M"), M_PRESSED);
		inputMap.put(KeyStroke.getKeyStroke("S"), S_PRESSED);
		inputMap.put(KeyStroke.getKeyStroke("C"), C_PRESSED);

		actionMap.put(ESC_PRESSED, editionActions.getEscAction());
		actionMap.put(M_PRESSED, editionActions
				.getActionToSetNewProductionMachineTool(MACHINE_TYPE));
		actionMap.put(S_PRESSED, editionActions.getActionToSetSelectionTool());
		actionMap.put(C_PRESSED, editionActions.getActionToSetConveyorTool());
	}

}
