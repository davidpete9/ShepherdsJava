package visualization;

import abstracts.ViewBuilder;
import commons.MenuButtonListeners;
import commons.Storage;
import shepherd.Shepherd;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VisualizationViewBuilder extends ViewBuilder implements ActionListener {

    private JList<Shepherd> list;
    private JLabel title;
    private List<Shepherd> shepherds = Storage.getInstance().getShepherds();
    private JButton generateBtn = new JButton("Draw");
    private JComboBox<String> graphOption;
    private GraphDrawer canvas = new GraphDrawer();
    private String[] options = {"Sum of costs", "Salaries", "Num. of animals"};

    public VisualizationViewBuilder() {
        this.list = new JList<Shepherd>();

        this.graphOption = new JComboBox<>(this.options);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.title = this.createCenterTitle("Compare shepherds");
    }

    @Override
    public void addComponentsToPanel() {
        this.base = new JPanel();
        this.base.add(this.title);


        JPanel content = new JPanel();
        JPanel selectpanel = new JPanel();

        content.setBorder(new EmptyBorder(0, 5, 10, 0));
        selectpanel.setBorder(new EmptyBorder(0, 0, 0, 5));


        this.graphOption.setBorder(new EmptyBorder(0, 0, 10, 0));
        this.graphOption.setMaximumSize(new Dimension(this.graphOption.getMinimumSize().width, 100));

        JLabel label = new JLabel("Choose!");
        label.setAlignmentX(JLabel.LEFT);
        selectpanel.add(label);
        JScrollPane sp = new JScrollPane(list);
        sp.setBorder(new EmptyBorder(10, 0, 10, 0));
        selectpanel.add(sp);
        selectpanel.add(this.graphOption);
        selectpanel.add(this.generateBtn);
        selectpanel.setLayout(new BoxLayout(selectpanel, BoxLayout.Y_AXIS));
        content.add(selectpanel);
        content.add(canvas);
        content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));

        this.base.add(content);
        JButton back = new JButton("back");
        base.add(back);
        base.setLayout(new BoxLayout(this.base, BoxLayout.Y_AXIS));

        back.addActionListener(MenuButtonListeners.mainMenuTarget);
        this.generateBtn.addActionListener(this);
    }

    public void onEntered() {
        list.setListData(shepherds.toArray(new Shepherd[0]));
    }

    /**
     * Ebben az egy esetben valositom meg itt az actionListenert, nem a controllerben.
     * A generalos gombra kattintva, az optionPane-ben kivalastott opcio alapjan, a GraphDrawer osztallyal kirajzoltatja a diagrammot.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.generateBtn) {
            List<Shepherd> sheps = this.list.getSelectedValuesList();
            String selected = (String) this.graphOption.getSelectedItem();
            if (sheps.size() > 0 && selected != null) {
                if (sheps.size() > 10) {
                    this.frame.showErrorMessage("You can maximum select 10 shepherds");
                    return;
                }

                List<String> labels = sheps.stream().map(s -> s.getName()).collect(Collectors.toList());
                if (selected.equals(this.options[0])) //tartasi koltseg
                {
                    List<Integer> values = sheps.stream().map(s -> s.sumAllAnimalCost()).collect(Collectors.toList());
                    this.canvas.setParameters(values, labels);
                    this.title.setText("Compare the sum the costs of all the selected shepherds animals");

                } else if (selected.equals(this.options[1])) { //fizetes
                    List<Integer> values = sheps.stream().map(s -> s.getSalary()).collect(Collectors.toList());
                    this.canvas.setParameters(values, labels);
                    this.title.setText("Compare the salaries of the chosen shepherd");
                }
                else if (selected.equals(this.options[2])) { //allatok szam
                    List<Integer> values = sheps.stream().map(s -> s.getAnimals().size()).collect(Collectors.toList());
                    this.canvas.setParameters(values, labels);
                    this.title.setText("Number of animals of the chosen shepherds");
                }
                this.canvas.repaint();
            }
        }
    }
}

/**
 * Oszlopdiagram rajzolo.
 */
class GraphDrawer extends Canvas {


    private final int PADDING_VERTICAL = 50;
    private final int PADDING_HORIZONTAL = 50;

    private List<Integer> values;
    private List<String> labels;
    private int colPad = 0;
    private int colWidth = 0;

    private int scaleMin = 0;
    private int scaleMax = 0;
    private int scaleDiff = 0;

    private final Color[] colorSequence = {Color.RED, Color.GREEN, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.ORANGE};

    public GraphDrawer() {
        this.setSize(new Dimension(600, 400));
        this.setBackground(Color.GRAY);
    }

    /**
     * Itt kell beallitani, hogy mi alapen adatok alapjan rajzoljon
     * @param values
     * @param labels
     */
    public void setParameters(List<Integer> values, List<String> labels) {
        this.values = values;
        this.labels = labels;
        int maxColWidth = (this.getGraphWidth() / values.size());
        if (maxColWidth >= 50) { //oszlopok kozotti kulonbseg
            this.colPad = 10;
        } else if (maxColWidth >= 20) {
            this.colPad = 5;
        } else if (maxColWidth > 5) {
            this.colPad = 1;
        }
        this.colWidth = maxColWidth - this.colPad;
    }


    /**
     * A diagramterulet magasssaga
     * @return
     */
    private int getGraphHeight() {
        return this.getHeight() - PADDING_VERTICAL;
    }

    /**
     * A diagramterulet szelessege
     * @return
     */
    private int getGraphWidth() {
        return this.getWidth() - PADDING_HORIZONTAL;
    }

    /**
     * Normalizalja az adatokat 0 es getGraphHeight kozotti szamra.
     * @param values: List<Integer>
     * @return List<Integer>
     */
    private List<Integer> normalizeToHeightValuesAndScale(List<Integer> values) {
        int max = (int) Math.round(Collections.max(values) * 1.1); //kis rahagyas, feltetletn a lehtetejeig erjenek az adatok
        int min = (int) Math.round(Collections.min(values) * 0.2); //lehetoleg minel kisebb szamrol induljon a skalazas.

        this.scaleMin = min;
        this.scaleMax = max;
        this.scaleDiff = (max - min) / 10;
        this.scaleDiff = this.scaleDiff <= 0? 1 : this.scaleDiff;

        return values.stream().map(i -> (int) Math.round(this.getGraphHeight() * ((i - min) / (double) (max - min)))).collect(Collectors.toList());
    }

    /**
     * Skalazas. Jelen esetben 10 erteket jelenitek meg a bal oldalon.
     * @param g
     */
    private void drawScale(Graphics g) {
        int min = this.scaleMin;
        int max = this.scaleMax;

        int i = this.scaleMin;
        while (i < this.scaleMax) {
            g.setColor(Color.RED);
            int currentHeight = (int) Math.round(this.getGraphHeight() * ((i - min) / (double) (max - min)));
            int yCord = PADDING_VERTICAL / 2 + this.getGraphHeight() - currentHeight;

            g.drawLine(PADDING_HORIZONTAL / 2, yCord, PADDING_HORIZONTAL / 2 + 10, yCord);

            g.setColor(Color.BLACK);
            int numLen = g.getFontMetrics().stringWidth("" + i);
            g.drawString("" + i, PADDING_HORIZONTAL / 2 - numLen / 2, yCord - 3);
            i += this.scaleDiff;
        }
    }

    /**
     * Maga a kirajzolas tortenik meg.
     * @param g: Graphics
     */
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(PADDING_HORIZONTAL / 2, PADDING_VERTICAL / 2, this.getGraphWidth(), this.getGraphHeight());

        int colorSeqLen = this.colorSequence.length;

        if (this.values != null && this.labels != null) {
            List<Integer> normalized = this.normalizeToHeightValuesAndScale(this.values);
            for (int i = 0; i < this.values.size(); i++) {
                g.setColor(this.colorSequence[(i + 1) % colorSeqLen]);
                int xCord = (this.colPad + PADDING_HORIZONTAL / 2) + i * (this.colWidth + this.colPad);
                int yCord = PADDING_VERTICAL / 2 + this.getGraphHeight() - normalized.get(i);
                g.fillRect(
                        xCord,
                        yCord,
                        this.colWidth,
                        normalized.get(i));

                //kirajzolom a cimkeket
                int numlen = g.getFontMetrics().stringWidth("" + values.get(i)); //azert kell, h kozepre tudjam igazitani a szovegeket az oszlophoz viszonyitva
                int labelLen = g.getFontMetrics().stringWidth(this.labels.get(i));
                g.setColor(Color.WHITE);
                g.drawString("" + values.get(i), xCord + this.colWidth / 2 - numlen / 2, yCord - 3);
                g.drawString(this.labels.get(i), xCord + this.colWidth / 2 - labelLen / 2, PADDING_VERTICAL / 2 + this.getGraphHeight() + 12);
            }
        }

        this.drawScale(g);
    }

}
