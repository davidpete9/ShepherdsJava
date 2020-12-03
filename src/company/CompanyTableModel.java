package company;

import abstracts.CRUDTableModel;
import commons.Storage;
import shepherd.Shepherd;

import java.util.List;

public class CompanyTableModel extends CRUDTableModel {
    public final static int ID_COL = 0;
    public final static int NAME_COL = 1;
    public final static int LEADER_COL = 2;
    public final static int ADDRESS_COL = 3;
    public final static int ACCOUNT_COL = 4;


    private List<Company> companies = Storage.getInstance().getCompanies();

    @Override
    public int getRowCount() {
        return this.companies.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Company Company = this.companies.get(rowIndex);
        switch (columnIndex) {
            case CompanyTableModel.ID_COL:
                return Company.getId();
            case CompanyTableModel.NAME_COL:
                return Company.getName();
            case CompanyTableModel.LEADER_COL:
                return Company.getLeader();
            case CompanyTableModel.ADDRESS_COL:
                return Company.getAddress();
            case CompanyTableModel.ACCOUNT_COL:
                return Company.getAccount();
        }
        return null;
    }

    @Override
    public String getColumnName(int ind) {
        switch (ind) {
            case CompanyTableModel.ID_COL:
                return "ID";
            case CompanyTableModel.NAME_COL:
                return "Name";
            case CompanyTableModel.LEADER_COL:
                return "CEO";
            case CompanyTableModel.ADDRESS_COL:
                return "Address";
            case CompanyTableModel.ACCOUNT_COL:
                return "Account";
        }
        return null;
    }

    @Override
    public Class getColumnClass(int ind) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col != CompanyTableModel.ID_COL;
    }

    @Override
    public void setValueAt(Object val, int rowIndex, int columnIndex) {
        Company comp = this.companies.get(rowIndex);
        String value = (String) val;
        switch (columnIndex) {
            case CompanyTableModel.NAME_COL:
                comp.setName(value);
                break;
            case CompanyTableModel.LEADER_COL:
                comp.setLeader(value);
                break;
            case CompanyTableModel.ADDRESS_COL:
                comp.setAddress(value);
                break;
            case CompanyTableModel.ACCOUNT_COL:
                comp.setAccount(value);
               break;
        }

    }

    /**
     * Troli a ceget az osszes ot foglalkoztato psztortol is .
     * @param selectedEntityId: int
     * @param selectedRowIndex: int
     */
    public void removeRow(String selectedEntityId, int selectedRowIndex) {

        List<Shepherd> shepherds = Storage.getInstance().getShepherds();

        //remove from collection
        for (Company c: this.companies) {
            if (c.getId().equals(selectedEntityId)) {
                for (Shepherd s: shepherds) { //torlom az osszes pasztortol, akinek ez a ceg a munkaltatoja
                    if (s.getEmployer() != null && s.getEmployer().getId().equals(c.getId())) {
                        s.setEmployer(null);
                    }
                }
                this.companies.remove(c);
                break;
            }
        }
        super.fireTableRowsDeleted(selectedRowIndex, selectedRowIndex);
    }

    @Override
    public void addRow(Object a) {
        this.companies.add((Company) a);
        super.fireTableRowsInserted(this.getRowCount() - 1, this.getRowCount() - 1);
    }

}
