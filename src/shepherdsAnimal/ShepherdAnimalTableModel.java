package shepherdsAnimal;

import abstracts.CRUDTableModel;

import java.util.List;

public class ShepherdAnimalTableModel extends CRUDTableModel {
    public final static int ID_COL = 0;
    public final static int QUANTITY_COL = 1;
    public final static int ANIMAL_COL = 2;

    private List<ShepherdAnimal> sh_animals;

    public ShepherdAnimalTableModel(List<ShepherdAnimal> sh_animals) {
        this.sh_animals = sh_animals;
    }

    @Override
    public int getRowCount() {
        return this.sh_animals.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ShepherdAnimal sh_animal = this.sh_animals.get(rowIndex);
        switch (columnIndex) {
            case ShepherdAnimalTableModel.ID_COL:
                return sh_animal.getAnimal().getId();
            case ShepherdAnimalTableModel.ANIMAL_COL:
                return sh_animal.getAnimal().getType();
            case ShepherdAnimalTableModel.QUANTITY_COL:
                return sh_animal.getQuantity();
        }
        return null;
    }

    @Override
    public String getColumnName(int ind) {
        switch (ind) {
            case ShepherdAnimalTableModel.ID_COL:
                return "ID";
            case ShepherdAnimalTableModel.ANIMAL_COL:
                return "Type";
            case ShepherdAnimalTableModel.QUANTITY_COL:
                return "Quantity";
        }
        return null;
    }

    @Override
    public Class getColumnClass(int ind) {
        if (ind == ShepherdAnimalTableModel.QUANTITY_COL) {
            return Integer.class;
        }
        return String.class;
    }


    @Override
    public boolean isCellEditable(int row, int col) {
        return col == ShepherdAnimalTableModel.QUANTITY_COL;
    }

    @Override
    public void setValueAt(Object val, int rowIndex, int columnIndex) {
        ShepherdAnimal sha = this.sh_animals.get(rowIndex);
        if (ShepherdAnimalTableModel.QUANTITY_COL == columnIndex) {
            sha.setQuantity((int) val);
        }
    }


    public void removeRow(String selectedAnimalId, int selectedRowIndex) {
        //remove from collection
        for (ShepherdAnimal sha: this.sh_animals) {
            if (sha.getAnimal().getId().equals(selectedAnimalId)) {
                this.sh_animals.remove(sha);
                break;
            }
        }
        super.fireTableRowsDeleted(selectedRowIndex, selectedRowIndex);
    }

    @Override
    public void addRow(Object a) {
        ShepherdAnimal sha = (ShepherdAnimal) a;

        for (ShepherdAnimal an: this.sh_animals) {
            if (an.getAnimal().getId().equals(sha.getAnimal().getId())) {
                an.setQuantity(an.getQuantity()+sha.getQuantity());
                super.fireTableDataChanged();
                return;
            }
        }

        this.sh_animals.add((ShepherdAnimal) a);
        super.fireTableRowsInserted(this.getRowCount() - 1, this.getRowCount() - 1);
    }

}
