package shepherd;

import abstracts.CRUDTableModel;
import commons.Storage;

import java.util.List;

public class ShepherdTableModel extends CRUDTableModel {
    public final static int ID_COL = 0;
    public final static int NAME_COL = 1;
    public final static int AREA_COL = 2;
    public final static int EMPLOYER_COL = 3;
    public final static int ANIMALS_COL_ = 4;


    private List<Shepherd> shepherds = Storage.getInstance().getShepherds();

    @Override
    public int getRowCount() {
        return this.shepherds.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Shepherd s = this.shepherds.get(rowIndex);
        switch (columnIndex) {
            case ShepherdTableModel.ID_COL:
                return s.getId();
            case ShepherdTableModel.NAME_COL:
                return s.getName();
            case ShepherdTableModel.AREA_COL:
                return s.getArea();
            case ShepherdTableModel.EMPLOYER_COL:
                return s.getEmployer() == null ? "-" : s.getEmployer().getName();
            case ShepherdTableModel.ANIMALS_COL_:
               return String.join(",", s.getAnimals().stream().map(a -> a.getAnimal().getType()).toArray(si -> new String[si]));
        }
        return null;
    }

    @Override
    public String getColumnName(int ind) {
        switch (ind) {
            case ShepherdTableModel.ID_COL:
                return "ID";
            case ShepherdTableModel.NAME_COL:
                return "Name";
            case ShepherdTableModel.AREA_COL:
                return "Area";
            case ShepherdTableModel.EMPLOYER_COL:
                return "Employer";
            case ShepherdTableModel.ANIMALS_COL_:
                return "Animals";
        }
        return null;
    }

    @Override
    public Class getColumnClass(int ind) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false; //kulon form lesz ra
    }

    @Override
    public void setValueAt(Object val, int rowIndex, int columnIndex) {
        //Itt nem lehet.
    }


    public void removeRow(String selectedEntityId, int selectedRowIndex) {
        //remove from collection
        for (Shepherd sh: this.shepherds) {
            if (sh.getId().equals(selectedEntityId)) {
                this.shepherds.remove(sh);
                break;
            }
        }
        super.fireTableRowsDeleted(selectedRowIndex, selectedRowIndex);
    }

    @Override
    public void addRow(Object a) {
        this.shepherds.add((Shepherd) a);
        super.fireTableRowsInserted(this.getRowCount() - 1, this.getRowCount() - 1);
    }

}
