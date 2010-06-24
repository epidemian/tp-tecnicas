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

public class ResearchLabPanelController implements Refreshable {

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

		setLabels();
	}

	private void setLabels() {
		setCurrentResearchTechnologyLabel();
		setResearchProgress();
		setObjectiveTechnologyLabel();
	}

	private void setObjectiveTechnologyLabel() {
		Technology objectiveTech = this.lab.getObjectiveTechnology();
		String objectiveTechStr;
		if (objectiveTech != null)
			objectiveTechStr = objectiveTech.getName();
		else
			objectiveTechStr = "Research cheapest";
		this.labPanel.setTargetTechnology(objectiveTechStr);
	}

	private void setResearchProgress() {
		double progress = this.lab.getCurrentReasearchTechnologyProgress();
		this.labPanel.setResearchProgress((int) (progress * 100));
	}

	private void setCurrentResearchTechnologyLabel() {
		Technology currentTech = this.lab.getCurrentResearchTechnology();
		String currentTechStr;
		if (currentTech != null)
			currentTechStr = currentTech.getName();
		else
			currentTechStr = "All researched";
		this.labPanel.setResearchingTechnology(currentTechStr);
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
		this.technologyToBeResearched = technologyEntry.getTechnology();
		Set<Technology> dependencies = lab
				.getUnresearchedDependencies(this.technologyToBeResearched);

		// Set techonology price.
		this.labPanel.setTechnologyPrice(this.technologyToBeResearched
				.getResearchCost());
		// Set description.
		String description = "";
		if (!dependencies.isEmpty()) {
			description += "Unresearched dependecies: \n";
			for (Technology dependency : dependencies)
				description += "\t " + dependency.getName() + "\n";
		}
		description += "Description: \n" + "\t "
				+ this.technologyToBeResearched.getDescription() + "\n";

		if (this.technologyToBeResearched.isResearched()) {
			description += "Already researched!\n";
		} else {
			description += "Research cost: "
					+ getMoneyString(this.technologyToBeResearched
							.getResearchCost()) + "\n";
		}
		this.labPanel.setTechnologyInfo(description);

		this.labPanel.getResearchButton().setEnabled(
				!this.technologyToBeResearched.isResearched());
	}

	private void initTechnologyResearchButton() {

		JButton research = this.labPanel.getResearchButton();
		research.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				lab.startResearching(technologyToBeResearched);
				refresh();
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
		setLabels();
	}
}
