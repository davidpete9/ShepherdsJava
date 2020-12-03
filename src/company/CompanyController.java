package company;

import abstracts.AbstractCRUDTableController;
import commons.FormComponent;
import abstracts.TableViewBuilder;

import javax.swing.*;
import java.util.Map;

public class CompanyController extends AbstractCRUDTableController {

    public CompanyController(TableViewBuilder view) {
        super(view);
    }

    @Override
    protected Company createNewRecord(Map<String, FormComponent> attributes) {
        String name = this.getContentStringOf(attributes.get(Company.NAME).getElement(), Company.NAME);
        String address = this.getContentStringOf(attributes.get(Company.ADDRESS).getElement(), Company.ADDRESS);
        String leader = this.getContentStringOf(attributes.get(Company.LEADER).getElement(), Company.LEADER);
        String account = this.getContentStringOf(attributes.get(Company.ACCOUNT).getElement(), Company.ACCOUNT);
        return new Company(name, address, leader, account);
    }

    @Override
    protected boolean confirmRemoveElement(int rowIndex) {
        String name = (String) this.table.getValueAt(rowIndex, CompanyTableModel.NAME_COL);
        return this.frame.confirmAction("Are you sure about deleting this company (" + name + ")? ");
    }

    @Override
    protected String getRecordIdOfRow(int rowIndex) {
        return (String) this.table.getValueAt(rowIndex, CompanyTableModel.ID_COL);
    }

    @Override
    protected String getContentStringOf(JComponent c, String field) {
        return ((JTextField) c).getText();
    }

    @Override
    protected void setContentToEmpty(JComponent c, String field) {
        ((JTextField) c).setText("");
    }
}
