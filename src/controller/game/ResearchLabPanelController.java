package controller.game;

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
import static model.utils.ArgumentUtils.*;
import model.game.Game;
import model.lab.ResearchLab;
import model.lab.Technology;
import view.game.MoneyConstants;
import view.game.ResearchLabPanel;

public class ResearchLabPanelController {

    private ResearchLab lab;
    private Technology technologyToBeResearched;
    private int dailyFunding;

    public ResearchLabPanelController(Game game, ResearchLabPanel labPanel){
        checkNotNull(game, "game");
        checkNotNull(labPanel, "labPanel");

        this.lab = game.getLab();

        initDailyFundingSpinner(labPanel);
        initDailyFundingAcceptButton(labPanel);
        initTechnologyCombo(labPanel);
        initTechnologyResearchButton(labPanel);
    }

    private void initDailyFundingSpinner(ResearchLabPanel labPanel) {

        int value = 0;
        int minimum = 0;
        int maximun = Integer.MAX_VALUE;
        int stepSize = 1;

        SpinnerNumberModel model = new SpinnerNumberModel(value, minimum,
            maximun, stepSize);
        final JSpinner spinner = labPanel.getDailyFundingSpinner();
        spinner.setModel(model);

        spinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                dailyFunding = (Integer)(spinner.getValue());
            }
        });
    }

    private void initDailyFundingAcceptButton(ResearchLabPanel labPanel) {

        final JButton accept = labPanel.getDailyFundingAcceptButton();

        accept.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                lab.setDailyFunding(dailyFunding);
            }
        });
    }

    private void initTechnologyCombo(final ResearchLabPanel labPanel) {

        final JComboBox technologyCombo = labPanel.geTechnologyCombo();

        Set<Technology> labTechnologies = this.lab.getTechnologies();

        technologyCombo.removeAllItems();
	for (Technology tech : labTechnologies)
            technologyCombo.addItem(new TechnologyComboEntry(tech));

        technologyComboAction(technologyCombo,labPanel);

        technologyCombo.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent ie) {
                technologyComboAction(technologyCombo,labPanel);
            }
        });
    }

    private void technologyComboAction(JComboBox technologyCombo,
        ResearchLabPanel labPanel){

        TechnologyComboEntry technologyEntry =
            (TechnologyComboEntry)technologyCombo.getSelectedItem();
        Technology technology = technologyEntry.getTechnology();
        Set<Technology> dependencies = lab.getUnresearchedDependencies(technology);

        // Set techonology price.
        labPanel.setTechnologyPrice(technology.getResearchCost());
        // Set description.
        String description = "Unresearched dependecies: \n";
        if (dependencies.isEmpty())
            description += "\t - \n";
        else
            for (Technology dependency : dependencies)
            description += "\t " + dependency.getName() + "\n";
        description += "Description: \n" + "\t " + technology.getDescription() + "\n";
        description += "Research cost: " + MoneyConstants.MONEY_SYMBOL +
            technology.getResearchCost() + "\n";
        labPanel.setTechnologyInfo(description);
    }

    private void initTechnologyResearchButton(ResearchLabPanel labPanel) {

        JButton research = labPanel.getResearchButton();
        research.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                lab.startResearching(technologyToBeResearched);
            }
        });
    }

    private class TechnologyComboEntry {

        private Technology technology;

        public TechnologyComboEntry(Technology technology){
            checkNotNull(technology, "technology");
            this.technology = technology;
        }

        public Technology getTechnology(){
            return this.technology;
        }

        @Override
        public String toString(){
            return this.technology.getName() + " - "
                    + this.technology.getResearchCost();
        }
    }
}
