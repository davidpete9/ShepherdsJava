package company;

import commons.FormComponent;
import abstracts.TableViewBuilder;
import commons.MenuButtonListeners;
import validators.StringValidator;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CompanyTableViewBuilder extends TableViewBuilder {


    public JTextField nameField;
    public JTextField addressField;
    public JTextField leaderField;
    public JTextField accountField;

    @Override
    public void addComponentsToPanel() {

        //1. Form mezok, es ezek hozzaadasa
        JPanel formPanel = new JPanel(new GridBagLayout());

        this.addBasicFormElementToPanel(formPanel, "Name*:", nameField, 0, 0);
        this.addBasicFormElementToPanel(formPanel, "Address*:", addressField, 3, 0);
        this.addBasicFormElementToPanel(formPanel, "CEO:", leaderField, 0, 1);
        this.addBasicFormElementToPanel(formPanel, "Account:", accountField, 3, 1);

        //2. Add es delete gombok hozzaadasa a formPanelhez
        this.addButtonToFormPanel(formPanel, this.addButton, 3, 2);
        this.addButtonToFormPanel(formPanel, this.deleteButton, 4, 2);

        //4. Mindezek hozzaadasa a fopanelhez
        base = new JPanel();
        base.add(this.createCenterTitle("Manage companies"));
        base.add(formPanel);
        base.add(new JScrollPane(this.table));

        //vissza a menube gomb
        JButton back = new JButton("Back");
        back.addActionListener(MenuButtonListeners.mainMenuTarget);
        base.add(back);
        base.setLayout(new BoxLayout(this.base, BoxLayout.Y_AXIS));

        this.attachController(new CompanyController(this));
    }

    @Override
    protected void initAttributeComponents() {
        this.nameField = new JTextField(20);
        this.addressField = new JTextField(20);
        this.accountField = new JTextField(20);
        this.leaderField = new JTextField(20);
    }

    @Override
    protected JTable createTable() {
        JTable t = new JTable(new CompanyTableModel());
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        t.setAutoCreateRowSorter(true);
        this.hideTableColumn(t, CompanyTableModel.ID_COL);
        return t;
    }

    @Override
    public Map<String, FormComponent> createAttributeComponentMap() {
        Map<String, FormComponent> map = new HashMap<>();
        map.put(Company.NAME, new FormComponent(nameField, new StringValidator(), "name"));
        map.put(Company.LEADER, new FormComponent(addressField, new StringValidator(), "address"));
        map.put(Company.ADDRESS, new FormComponent(leaderField, new StringValidator(true), "CEO"));
        map.put(Company.ACCOUNT, new FormComponent(accountField, new StringValidator(true), "account"));
        return map;
    }
}
