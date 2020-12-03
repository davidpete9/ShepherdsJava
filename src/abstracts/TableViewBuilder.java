package abstracts;

import commons.FormComponent;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Alapvetoen a tablazatos neveteztekek a felepiteseert felel.
 */
public abstract class TableViewBuilder extends ViewBuilder {

    protected JButton addButton = new JButton("Add");
    protected JButton deleteButton = new JButton("Delete selected row");
    protected Map<String, FormComponent> attributeComponents;
    protected AbstractCRUDTableController controller;
    protected JTable table;
    protected GridBagConstraints gbc = new GridBagConstraints();

    public TableViewBuilder() {
        this.initAttributeComponents();
        this.attributeComponents = this.createAttributeComponentMap();
        this.table = this.createTable();
    }

    /**
     * Itt kell majd peldanyositani a leszarmazottak kulonbozo urlap-elemeiket
     * (jellemzoen a JTextFieldeket és JComboBoxokat)
     */
    protected abstract void initAttributeComponents();

    /**
     * A JTable-t, ami majd az adatokat megjeleniti itt kell letrehozni.
     * @return
     */
    protected abstract JTable createTable();

    /**
     * Az osszes urlap elemet tartalmazo FormComponent map-ot itt kell feltolteni.
     * @return
     */
    protected abstract Map<String, FormComponent> createAttributeComponentMap();

    /**
     * Gyakolatilag csak egy setter. Azert igy csinaltam, hogy 'feltunobb'
     * legyen a kodban a controller.
     * @param ctrl
     */
    public void attachController(AbstractCRUDTableController ctrl) {
        this.controller = ctrl;
    }

    public JTable getTable() {
        return this.table;
    }

    public JButton getAddButton() {
        return this.addButton;
    }

    public JButton getDeleteButton() {
        return this.deleteButton;
    }

    public Map<String, FormComponent> getAttributeComponents() {
        return this.attributeComponents;
    }

    /**
     * A tablazatbol eltuntet egy oszlopat (az id-t), ugy hogy 0-ra allitja a szelesseget.
     * (azt csinalom a leszarmazottakban, hogy az id-t is megjelenitem a tablazatban, hogy egyertelmuen tudjam azonositani a
     * kijelolt sort)
     * @param table
     * @param colId
     */
    protected void hideTableColumn(JTable table, int colId) {
        //id oszlop elrejtese.
        table.getColumnModel().getColumn(colId).setMinWidth(0);
        table.getColumnModel().getColumn(colId).setMaxWidth(0);
        table.getColumnModel().getColumn(colId).setWidth(0);
    }

    /**
     * Segedfgv, lehelyez egy urapelepment a GridBadLayout szerint egy labellel egyutt.
     */
    protected void addBasicFormElementToPanel(JPanel formPanel, String label, JComponent comp, int gridx, int gridy) {
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = gridx;
        this.gbc.gridy = gridy;
        this.gbc.insets = new Insets(5, 5, 5, 5);
        formPanel.add(new JLabel(label), this.gbc);

        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = gridx + 1;
        this.gbc.gridy = gridy;
        formPanel.add(comp, this.gbc);
    }

    /**
     * Segedfgv egy gomb lehetelyezesehez a GridBagLayout szerin
     */
    protected void addButtonToFormPanel(JPanel panel, JButton button, int gridx, int gridy) {
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = gridx;
        this.gbc.gridy = gridy;
        this.gbc.gridwidth = 1;
        panel.add(button, this.gbc);
    }

}


