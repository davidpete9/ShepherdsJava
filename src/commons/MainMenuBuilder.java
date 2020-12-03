package commons;

import abstracts.ViewBuilder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Fomenu, vagyis a 4 fo gomb letrehozasa.
 */
class MainMenuBuilder extends ViewBuilder {

    @Override
    public void addComponentsToPanel() {
        base = new JPanel(new BorderLayout());

        JButton toShepherds = new JButton("Shepherds");
        toShepherds.addActionListener(MenuButtonListeners.shepherdListTarget);

        JButton toAnimals = new JButton("Animals");
        toAnimals.addActionListener(MenuButtonListeners.animalListTarget);

        JButton toCompanies = new JButton("Companies");
        toCompanies.addActionListener(MenuButtonListeners.companyListTarget);

        JButton toVisualization = new JButton("Visualization");
        toVisualization.addActionListener(MenuButtonListeners.dataVisualizationTarget);

        JPanel btns = new JPanel(new FlowLayout());
        btns.add(toShepherds);
        btns.add(toAnimals);
        btns.add(toCompanies);
        btns.add(toVisualization);
        base.add(btns, BorderLayout.CENTER);

        JLabel label = new JLabel("Shepherd administration system", SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(50, 10, 50, 10));
        label.setFont(new Font("TimesRoman", Font.ITALIC, 18));
        base.add(label, BorderLayout.NORTH);
    }
}
