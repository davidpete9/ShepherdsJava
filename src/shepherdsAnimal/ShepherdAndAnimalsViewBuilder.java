package shepherdsAnimal;

import commons.FormComponent;
import abstracts.TableViewBuilder;
import animal.Animal;
import commons.MenuButtonListeners;
import commons.Storage;
import shepherd.Shepherd;
import validators.PositiveIntValidator;
import validators.StringValidator;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShepherdAndAnimalsViewBuilder extends TableViewBuilder {

    public Shepherd shepherd;

    public JComboBox<Animal> animalChoose;
    public JTextField quantityField;

    private String allCostSumStr;

    /**
     * Itt az a kulonbseg, hogy a konstruktorvan var egy pasztorId-t, hogy azonositas, hogy melyik pasztor allatait szeretnem
     * kezelni.
     * @param shepherdId
     */
    public ShepherdAndAnimalsViewBuilder(String shepherdId) {
        super();
        this.shepherd = Storage.getShepherdById(shepherdId);
    }

    @Override
    protected void initAttributeComponents() {
        this.quantityField = new JTextField(20);
        this.animalChoose = new JComboBox<Animal>();
    }

    /**
     * Mindig, mikor menyitjuk ezt, frissiteni kell az allatlistat a CombBox-ban, mert leet, hogy modosultak kozben az adatok
     */
    @Override
    public void onEntered() {
        List<Animal> list = Storage.getInstance().getAnimals();
        this.animalChoose.setModel(new DefaultComboBoxModel<Animal>(list.toArray(new Animal[0])));
        this.animalChoose.setSelectedIndex(0);
    }


    @Override
    protected JTable createTable() {
        JTable t = new JTable();
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        t.setAutoCreateRowSorter(true);
        return t;
    }

    @Override
    protected Map<String, FormComponent> createAttributeComponentMap() {
        Map<String, FormComponent> map = new HashMap<>();
        map.put(ShepherdAnimal.QUANTITY, new FormComponent(this.quantityField, new PositiveIntValidator(), "quantity"));
        map.put(ShepherdAnimal.ANIMAL, new FormComponent(this.animalChoose, new StringValidator(), "type"));
        return map;
    }

    @Override
    public void addComponentsToPanel() {
        this.table.setModel(new ShepherdAnimalTableModel(this.shepherd.getAnimals()));
        this.hideTableColumn(this.table, ShepherdAnimalTableModel.ID_COL);
        base = new JPanel();
        base.add(this.createCenterTitle(this.shepherd.getName() + "'s details and animals"));
        JPanel shepherdDetails = new JPanel(new GridBagLayout());
        String employername = this.shepherd.getEmployer() == null ? null : this.shepherd.getEmployer().getName();
        this.printAttribute(shepherdDetails, "Name:", this.shepherd.getName(), 0, 0);
        this.printAttribute(shepherdDetails, "Area:", this.shepherd.getArea(), 2, 0);
        this.printAttribute(shepherdDetails, "Address:", this.shepherd.getAddress(), 0, 1);
        this.printAttribute(shepherdDetails, "Born:", Storage.dateToString(this.shepherd.getBorn()), 2, 1);
        this.printAttribute(shepherdDetails, "Employer:", employername , 0, 3);
        this.printAttribute(shepherdDetails, "Salary:", this.shepherd.getSalary()+" Ft/month" , 2, 3);
        this.allCostSumStr = this.shepherd.sumAllAnimalCost()+" Ft/month";
        this.printAttribute(shepherdDetails, "Sum of all animals cost:", allCostSumStr , 0, 4);


        base.add(shepherdDetails);
        base.add(this.createCenterTitle("Manage animals"));

        JPanel formPanel = new JPanel(new GridBagLayout());
        this.addBasicFormElementToPanel(formPanel, "Type:",this.animalChoose, 0, 0);
        this.addBasicFormElementToPanel(formPanel, "Quantity:",this.quantityField, 2, 0);

        this.addButtonToFormPanel(formPanel, this.addButton, 2, 1);
        this.addButtonToFormPanel(formPanel, this.deleteButton, 3, 1);

        base.add(formPanel);
        base.add(new JScrollPane(this.table));

        //vissza gombok
        JButton backToMain = new JButton("Back to main menu");
        backToMain.addActionListener(MenuButtonListeners.mainMenuTarget);
        JButton backToShepherds = new JButton("Back to shepherd list");
        backToShepherds.addActionListener(MenuButtonListeners.shepherdListTarget);
        JPanel bottombuttons = new JPanel();
        bottombuttons.add(backToMain);
        bottombuttons.add(backToShepherds);
        base.add(bottombuttons);
        base.setLayout(new BoxLayout(this.base, BoxLayout.Y_AXIS));

        this.attachController(new ShepherdAnimalController(this));
    }

    /**
     *Segedfgv. Egy Attributum=ertek kiirasat segiti JLabelekkel.
     */
    private void printAttribute(JPanel panel, String attr, String val, int gridx, int gridy) {

        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = gridx;
        this.gbc.gridy = gridy;
        this.gbc.insets = new Insets(5, 5, 5, 5);

        JLabel attrLabel = new JLabel(attr);
        attrLabel.setFont(new Font("Arial", Font.BOLD, 13));
        attrLabel.setForeground(new Color(0, 46, 212));
        panel.add(attrLabel,this.gbc);

        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = gridx+1;
        this.gbc.gridy = gridy;
        JLabel valLabel = new JLabel(val == null ? "empty" : val);
        valLabel.setFont(new Font("Arial", Font.ITALIC, 13));
        valLabel.setForeground(new Color(141, 155, 212));
        panel.add(valLabel, this.gbc);
    }

}
