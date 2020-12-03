package animal;

import commons.FormComponent;
import abstracts.TableViewBuilder;
import commons.MenuButtonListeners;
import validators.StringValidator;
import validators.PositiveIntValidator;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AnimalTableViewBuilder extends TableViewBuilder {

    public JTextField typeField;
    public JTextField costField;

    @Override
    public void addComponentsToPanel() {
        //1. Form mezok, es ezek hozzaadasa
        JPanel formPanel = new JPanel(new GridBagLayout());

        this.addBasicFormElementToPanel(formPanel, "Type*:", typeField, 0, 0);
        this.addBasicFormElementToPanel(formPanel, "Cost* (ft/month):", costField, 2, 0);

        //2. Add es delete gombok hozzaadasa a formPanelhez
        this.addButtonToFormPanel(formPanel, this.deleteButton, 2, 2);
        this.addButtonToFormPanel(formPanel, this.addButton, 3, 2);

        //3. Mindezek hozzaadasa a fopanelhez
        base = new JPanel();
        base.add(this.createCenterTitle("Manage animals"));
        base.add(formPanel);
        base.add(new JScrollPane(this.table));

        JButton back = new JButton("Back");
        back.addActionListener(MenuButtonListeners.mainMenuTarget);
        base.add(back);
        base.setLayout(new BoxLayout(this.base, BoxLayout.Y_AXIS));

        this.attachController(new AnimalController(this));
    }

    @Override
    protected void initAttributeComponents() {
        this.costField = new JTextField(20);
        this.typeField = new JTextField(20);
    }

    @Override
    protected JTable createTable() {
        JTable t = new JTable(new AnimalTableModel());
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        t.setAutoCreateRowSorter(true);
        this.hideTableColumn(t, AnimalTableModel.ID_COL);
        return t;
    }

    @Override
    public Map<String, FormComponent> createAttributeComponentMap() {
        Map<String, FormComponent> map = new HashMap<>();
        map.put(Animal.TYPE, new FormComponent(this.typeField, new StringValidator(), "type"));
        map.put(Animal.COST, new FormComponent(this.costField, new PositiveIntValidator(), "cost"));
        return map;
    }
}
