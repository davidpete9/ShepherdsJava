package shepherd;

import commons.FormComponent;
import abstracts.TableViewBuilder;
import commons.MenuButtonListeners;
import commons.Storage;
import company.Company;
import validators.DateValidator;
import validators.PositiveIntValidator;
import validators.StringValidator;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShepherdTableViewBuilder extends TableViewBuilder {

    public JTextField nameField;
    public JTextField areaField;
    public JTextField addressField;
    public JTextField salaryField;
    public JTextField bornField;
    public JComboBox<Company> companyPicker;

    public JButton shepherdDetailsBtn = new JButton("View animals of the selected shepherd");
    public JButton editButton = new JButton("Edit selected shepherd");

    @Override
    protected void initAttributeComponents() {
        this.nameField = new JTextField(20);
        this.areaField = new JTextField(20);
        this.addressField = new JTextField(20);
        this.salaryField = new JTextField(20);
        this.bornField = new JTextField(20);
        this.companyPicker = new JComboBox<Company>();
    }

    @Override
    protected JTable createTable() {
        JTable t = new JTable(new ShepherdTableModel());
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        t.setAutoCreateRowSorter(true);
        this.hideTableColumn(t, ShepherdTableModel.ID_COL);
        return t;
    }

    @Override
    protected Map<String, FormComponent> createAttributeComponentMap() {
        Map<String, FormComponent> map = new HashMap<>();
        map.put(Shepherd.NAME, new FormComponent(this.nameField, new StringValidator(), "name"));
        map.put(Shepherd.AREA, new FormComponent(this.areaField, new StringValidator(), "area"));
        map.put(Shepherd.ADDRESS, new FormComponent(this.addressField, new StringValidator(true), "address"));
        map.put(Shepherd.SALARY, new FormComponent(this.salaryField, new PositiveIntValidator(true), "salary"));
        map.put(Shepherd.BORN, new FormComponent(this.bornField, new DateValidator(true), "born date"));
        map.put(Shepherd.EMPLOYER, new FormComponent(this.companyPicker, new StringValidator(true), "employer"));
        return map;
    }

    @Override
    public void addComponentsToPanel() {
        //1. Form mezok, es ezek hozzaadasa
        JPanel formPanel = new JPanel(new GridBagLayout());

        this.addBasicFormElementToPanel(formPanel, "Name*:", nameField, 0, 0);
        this.addBasicFormElementToPanel(formPanel, "Area*:", areaField, 3,0);
        this.addBasicFormElementToPanel(formPanel, "Address:", addressField, 0, 1);
        this.addBasicFormElementToPanel(formPanel, "Born:", bornField, 3, 1);
        this.addBasicFormElementToPanel(formPanel, "Salary:", salaryField, 0, 2);
        this.addBasicFormElementToPanel(formPanel, "Employer:", companyPicker, 3, 2);



        //2. Add es delete gombok hozzaadasa a formPanelhez
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 3;
        this.gbc.gridwidth = 2;
        formPanel.add(this.editButton, this.gbc);


        this.addButtonToFormPanel(formPanel, this.deleteButton, 3, 3);
        this.addButtonToFormPanel(formPanel, this.addButton, 4, 3);

        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 4;
        this.gbc.gridwidth = 5;
        formPanel.add(this.shepherdDetailsBtn, this.gbc);


        //3. Mindezek hozzaadasa a fopanelhez
        base = new JPanel();
        base.add(this.createCenterTitle("Manage shepherds"));
        base.add(formPanel);
        base.add(new JScrollPane(this.table));

        //vissza a menube gomb
        JButton back = new JButton("Back");
        back.addActionListener(MenuButtonListeners.mainMenuTarget);
        base.add(back);
        base.setLayout(new BoxLayout(this.base, BoxLayout.Y_AXIS));

        this.attachController(new ShepherdController(this, this.shepherdDetailsBtn, this.editButton));
    }

    /**
     * Mindig, mikor menyitjuk ezt, frissiteni kell az ceglistat a CombBox-ban, mert leet, hogy modosultak kozben az adatok
     */
    @Override
    public void onEntered() {
        List<Company> list = Storage.getInstance().getCompanies();
        this.companyPicker.setModel(new DefaultComboBoxModel<Company>(list.toArray(new Company[0])));
        this.companyPicker.insertItemAt(null, 0);
        this.companyPicker.setSelectedIndex(0);
    }
}
