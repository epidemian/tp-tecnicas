package view.warehouse.edition;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import model.production.MachineType;

public class KeyInputActionMapper {

	private static final String ESC_PRESSED = "esc-pressed";
	private static final String M_PRESSED = "m-pressed";
	private static final Object S_PRESSED = "s-pressed";
	
	private EditionActions editionActions;

	public KeyInputActionMapper(EditionActions editionActions) {
		this.editionActions = editionActions;
	}

	public void mapActions(InputMap inputMap, ActionMap actionMap) {
		inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), ESC_PRESSED);
		inputMap.put(KeyStroke.getKeyStroke("M"), M_PRESSED);
		inputMap.put(KeyStroke.getKeyStroke("S"), S_PRESSED);
		
		
		actionMap.put(ESC_PRESSED, editionActions.getEscAction());
		// TODO: Delete this action... it's just an example.
		actionMap.put(M_PRESSED, editionActions
				.getActionToSetNewMachineTool(new MachineType("Big Machine", 3, 2)));
		actionMap.put(S_PRESSED, editionActions.getActionToSetSelectionTool());
	}

	
}
