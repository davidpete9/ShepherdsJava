package shepherd;

import abstracts.AbstractCRUDTableController;
import commons.FormComponent;
import abstracts.TableViewBuilder;
import commons.Storage;
import company.Company;
import shepherdsAnimal.ShepherdAndAnimalsViewBuilder;
import shepherdsAnimal.ShepherdAnimal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ShepherdController extends AbstractCRUDTableController {

    private JButton shepherdDetails;
    private JButton editButton;
    private Shepherd editingRecord = null;

    public ShepherdController(TableViewBuilder view, JButton shepherdDetails, JButton editButton) {
        super(view);
        this.shepherdDetails = shepherdDetails;
        this.editButton = editButton;

        this.editButton.addActionListener(this);
        this.shepherdDetails.addActionListener(this);
    }

    @Override
    protected Shepherd createNewRecord(Map<String, FormComponent> attributes) {
        String name = this.getContentStringOf(attributes.get(Shepherd.NAME).getElement(), Shepherd.NAME);
        String address = this.getContentStringOf(attributes.get(Shepherd.ADDRESS).getElement(), Shepherd.ADDRESS);

        String salaryContent = this.getContentStringOf(attributes.get(Shepherd.SALARY).getElement(), Shepherd.SALARY);
        //ha nem ures akkor mar validalva is van, tehat nem fog semmikeppesen sem kivetelt dobni a parseint.
        int salary = salaryContent == null || salaryContent.equals("") ? 0 : Integer.parseInt(salaryContent);

        Date born = Storage.parseDate(this.getContentStringOf(attributes.get(Shepherd.BORN).getElement(), Shepherd.BORN));
        String area = this.getContentStringOf(attributes.get(Shepherd.AREA).getElement(), Shepherd.AREA);

        Company employer = (Company)((JComboBox<Company>) attributes.get(Shepherd.EMPLOYER).getElement()).getSelectedItem();

        return new Shepherd(name, area, new ArrayList<ShepherdAnimal>(), born, address, employer, salary);
    }

    @Override
    protected boolean confirmRemoveElement(int rowIndex) {
        String name = (String) this.table.getValueAt(rowIndex, ShepherdTableModel.NAME_COL);
        return this.frame.confirmAction("Biztos szeretnéd törölni ezt a pásztort (" + name + ")? ");
    }

    @Override
    protected String getRecordIdOfRow(int rowIndex) {
        return (String) this.table.getValueAt(rowIndex, ShepherdTableModel.ID_COL);
    }

    @Override
    protected String getContentStringOf(JComponent c, String field) {
        if (field.equals(Shepherd.EMPLOYER)) {
           Object selected = ((JComboBox<Company>) c).getSelectedItem();
           if (selected == null || selected.equals("")) {
               return null;
           }
           return ((Company) selected).getId();
        }
        return ((JTextField) c).getText();
    }

    @Override
    protected void setContentToEmpty(JComponent c, String field) {
        if (field.equals(Shepherd.EMPLOYER)) {
            ((JComboBox<Company>) c).setSelectedIndex(-1);
        }
        else {
            ((JTextField) c).setText("");
        }
    }

    /**
     * Elteres az ososztalyhoz kepest itt az, hogy a modisitast is kezeli itt, a hozzadast es a torlest az ososztaly fuggvenyivel.
     * Vagyis az editButtonra kattintva betolti az urlap-elemekbe az adott rekordot, ekkor a hozzaado gombra kattintva a modositas tortenik meg.
     * (es a gomb szovege azval 'Edit'-re.)
     * @param e: ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.shepherdDetails) {
            int selectedRow = this.table.getSelectedRow();
            if (selectedRow != -1) {
                ShepherdAndAnimalsViewBuilder avb = new ShepherdAndAnimalsViewBuilder(this.getRecordIdOfRow(selectedRow));
                avb.switchToTargetView();
            }
        }
        else if (e.getSource() == this.editButton) {
            int selectedRow = this.table.getSelectedRow();
            if (selectedRow != -1) { //beallitom a mezoket a kivalasztottra
                String selectedId = this.getRecordIdOfRow(selectedRow);
                Shepherd selectedShep = Storage.getShepherdById(selectedId);
                if (selectedShep == null) //elvileg soha sem tortenhet meg, de biztos ami biztos...
                        return;
                ((JTextField) this.components.get(Shepherd.AREA).getElement()).setText(selectedShep.getArea());
                ((JTextField) this.components.get(Shepherd.SALARY).getElement()).setText(""+selectedShep.getSalary());
                ((JTextField) this.components.get(Shepherd.BORN).getElement()).setText(Storage.dateToString(selectedShep.getBorn()));
                ((JTextField) this.components.get(Shepherd.ADDRESS).getElement()).setText(selectedShep.getAddress());
                ((JTextField) this.components.get(Shepherd.NAME).getElement()).setText(selectedShep.getName());
                ((JComboBox<Company>) this.components.get(Shepherd.EMPLOYER).getElement()).setSelectedItem(selectedShep.getEmployer());

                this.editingRecord = selectedShep;
                this.view.getAddButton().setText("Edit");
            }
        }
        else if (e.getSource() == this.view.getAddButton()) {
            if (this.editingRecord == null) {
                this.handleAdditionEvent(); //default
            }
            else {
                if (this.validateEverything()) {
                    this.commitEdition();
                    this.tableModel.fireTableDataChanged();
                    this.setEverythingToEmpty();
                    this.editingRecord = null;
                    this.view.getAddButton().setText("Add");
                }
            }
        }
        else if (e.getSource() == this.view.getDeleteButton()) {
            this.handleDeletionEvent();
        }
    }

    /**
     * A modositashoz vegrehajtasahoz a createNewRecorddal keszit egy ideiglenes masolatot,
     * majd annak adatait atmasolja a meglevo rekordba (nyilvan az id-kivetelevel)
     */
    private void commitEdition() {
        if (this.editingRecord != null) {
            Shepherd temp = this.createNewRecord(this.components);
            this.editingRecord.setEmployer(temp.getEmployer());
            this.editingRecord.setAddress(temp.getAddress());
            this.editingRecord.setArea(temp.getArea());
            this.editingRecord.setName(temp.getName());
            this.editingRecord.setBorn(temp.getBorn());
            this.editingRecord.setSalary(temp.getSalary());
        }
    }
}
