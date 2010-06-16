package view.game.edition;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

public class KeyInputActionMapper {

	private static final String ESC_PRESSED = "esc-pressed";
	private static final Object S_PRESSED = "s-pressed";
	private static final Object C_PRESSED = "c-pressed";

	private EditionActions editionActions;

	public KeyInputActionMapper(EditionActions editionActions) {
		this.editionActions = editionActions;
	}

	public void mapActions(InputMap inputMap, ActionMap actionMap) {
		inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), ESC_PRESSED);
		inputMap.put(KeyStroke.getKeyStroke("S"), S_PRESSED);
		inputMap.put(KeyStroke.getKeyStroke("C"), C_PRESSED);

		actionMap.put(ESC_PRESSED, editionActions.getEscAction());
		actionMap.put(S_PRESSED, editionActions.getActionToSetSelectionTool());
		actionMap.put(C_PRESSED, editionActions.getActionToSetConveyorTool());
	}

}
