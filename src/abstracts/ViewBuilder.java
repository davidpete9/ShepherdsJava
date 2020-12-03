package abstracts;

import commons.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class ViewBuilder {

    protected JPanel base = null;
    public MainFrame frame =  MainFrame.getInstance();

    /**
     * Itt kell felepitani a nezet tartalmaz, a base-JPanelre.
     */
    public abstract void addComponentsToPanel();

    /**
     * Akkor fut le, amikor egy menu-navigalo gombra kattintunk. (A MenuButtonLister actionPerformed metodusaban hivodik)
     * Elvegzi a JPanel cseret, mikor meg akarjuk nyitni ezt nezetet.
     */
    public void switchToTargetView() {
        if (this.base == null) {
            this.addComponentsToPanel();
        }
        this.frame.getContentPane().removeAll();
        this.frame.getContentPane().add(this.base);
        this.frame.getContentPane().revalidate();
        this.frame.getContentPane().repaint();

        this.onEntered();
    }

    /**
     * Segit elkesziteni az ablakok kozepen lathato cimeket.
     * @param title: String
     * @return JLabel
     */
    protected  JLabel createCenterTitle(String title) {
        JLabel label = new JLabel(title, JLabel.CENTER);
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setBorder(new EmptyBorder(10, 0, 10, 0));
        label.setFont(new Font("TimesRoman", Font.ITALIC, 16));
        return label;
    }

    /**
    * Ha kell overrideolni lehet, lefuttatom minden cserekor
    * */
    protected void onEntered() { }

}

