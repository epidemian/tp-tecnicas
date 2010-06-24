package controller.game;

import static model.utils.ArgumentUtils.checkNotNull;
import static view.game.MoneyConstants.getMoneyString;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.game.Player;
import model.lab.ResearchLab;
import model.lab.Technology;
import view.game.ResearchLabPanel;

public class ResearchLabPanelController implements Refreshable{

	private ResearchLab lab;
	private Technology technologyToBeResearched;

	private ResearchLabPanel labPanel;

	public ResearchLabPanelController(Player player, ResearchLabPanel labPanel) {
		checkNotNull(player, "player");
		checkNotNull(labPanel, "labPanel");

		this.lab = player.getLab();
		this.labPanel = labPanel;

        this.labPanel.setMaxDailyFunding(this.lab.getMaxDailyFunding());

		initDailyFundingSpinner();
		initTechnologyCombo();
		initTechnologyResearchButton();
		
		
	}

	private void initDailyFundingSpinner() {

		int value = 0;
		int minimum = 0;
		int maximun = this.lab.getMaxDailyFunding();
		int stepSize = 1;

		SpinnerNumberModel model = new SpinnerNumberModel(value, minimum,
				maximun, stepSize);
		final JSpinner spinner = this.labPanel.getDailyFundingSpinner();
		spinner.setModel(model);

		spinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent ce) {
				int dailyFunding = (Integer) (spinner.getValue());
                                lab.setDailyFunding(dailyFunding);
			}
		});
	}

	private void initTechnologyCombo() {

		final JComboBox technologyCombo = this.labPanel.geTechnologyCombo();

		Set<Technology> labTechnologies = this.lab.getTechnologies();

		technologyCombo.removeAllItems();
		for (Technology tech : labTechnologies)
			technologyCombo.addItem(new TechnologyComboEntry(tech));

		technologyComboAction(technologyCombo);

		technologyCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent ie) {
				technologyComboAction(technologyCombo);
			}
		});
	}

	private void technologyComboAction(JComboBox technologyCombo) {

		TechnologyComboEntry technologyEntry = (TechnologyComboEntry) technologyCombo
				.getSelectedItem();
		Technology technology = technologyEntry.getTechnology();
		Set<Technology> dependencies = lab
				.getUnresearchedDependencies(technology);

		// Set techonology price.
		this.labPanel.setTechnologyPrice(technology.getResearchCost());
		// Set description.
		String description = "";
		if (!dependencies.isEmpty()) {
			description += "Unresearched dependecies: \n";
			for (Technology dependency : dependencies)
				description += "\t " + dependency.getName() + "\n";
		}
		description += "Description: \n" + "\t " + technology.getDescription()
				+ "\n";

		if (technology.isResearched()) {
			description += "Already researched!\n";
		} else {
			description += "Research cost: "
					+ getMoneyString(technology.getResearchCost()) + "\n";
		}
		this.labPanel.setTechnologyInfo(description);

		this.labPanel.getResearchButton()
				.setEnabled(!technology.isResearched());
	}

	private void initTechnologyResearchButton() {

		JButton research = this.labPanel.getResearchButton();
		research.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				lab.startResearching(technologyToBeResearched);
			}
		});
	}

	private class TechnologyComboEntry {

		private Technology technology;

		public TechnologyComboEntry(Technology technology) {
			checkNotNull(technology, "technology");
			this.technology = technology;
		}

		public Technology getTechnology() {
			return this.technology;
		}

		@Override
		public String toString() {
			return this.technology.getName() + " - "
					+ this.technology.getResearchCost();
		}
	}

	@Override
	public void refresh() {
		this.technologyComboAction(labPanel.geTechnologyCombo());
		
	}
}
