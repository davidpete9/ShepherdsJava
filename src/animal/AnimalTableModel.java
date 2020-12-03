package animal;

import abstracts.CRUDTableModel;
import commons.Storage;
import shepherd.Shepherd;
import shepherdsAnimal.ShepherdAnimal;

import java.util.List;

public class AnimalTableModel extends CRUDTableModel {

    public final static int TYPE_COL = 0;
    public final static int COST_COL = 1;
    public final static int ID_COL = 2;

    private List<Animal> animals = Storage.getInstance().getAnimals();

    @Override
    public int getRowCount() {
        return this.animals.size();
    }

    @Override
    public int getColumnCount() {
        //name, average cost, id (id el lesz rejtve)
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Animal animal = this.animals.get(rowIndex);
        switch (columnIndex) {
            case AnimalTableModel.TYPE_COL:
                return animal.getType();
            case AnimalTableModel.COST_COL:
                return animal.getAvg_cost();
            case AnimalTableModel.ID_COL:
                return animal.getId(); //el lesz rejtve, csak zonositashoz hasznalom
        }
        return null;
    }

    @Override
    public String getColumnName(int ind) {
        switch (ind) {
            case AnimalTableModel.TYPE_COL:
                return "Type";
            case AnimalTableModel.COST_COL:
                return "Cost";
            case AnimalTableModel.ID_COL:
                return "ID"; //rejtett
        }
        return null;
    }

    @Override
    public Class getColumnClass(int ind) {
        switch (ind) {
            case AnimalTableModel.TYPE_COL:
            case AnimalTableModel.ID_COL:
                return String.class;
            case AnimalTableModel.COST_COL:
                return Integer.class;
        }
        return null;
    }


    @Override
    public boolean isCellEditable(int row, int col) {
        return col == AnimalTableModel.COST_COL || col == AnimalTableModel.TYPE_COL;
    }

    @Override
    public void setValueAt(Object val, int rowIndex, int columnIndex) {
        Animal animal = this.animals.get(rowIndex);

        if (AnimalTableModel.TYPE_COL == columnIndex) {
            animal.setType((String) val);
        } else if (AnimalTableModel.COST_COL == columnIndex) {
            animal.setAvg_cost((int) val);
        }
    }

    /**
     * Az adott rekord torlesen felul torli az ooszes pasztortol is a torlolt allatot.
     * @param selectedEntityId: int
     * @param selectedRowIndex: int
     */
    public void removeRow(String selectedEntityId, int selectedRowIndex) {
        //kollekciobol valo torles
        for (Animal ani: this.animals) {
            if (ani.getId().equals(selectedEntityId)) {
                //torli az osszes pasztortol a megadott allatot
                List<Shepherd> shepherds = Storage.getInstance().getShepherds();
                for (Shepherd s: shepherds) {
                    for (ShepherdAnimal sha: s.getAnimals()) {
                        if (sha.getAnimal().getId().equals(selectedEntityId)) {
                            s.getAnimals().remove(sha);
                            break; //mert minden pasztornak egy allattal kapcsaolatban csak 1 bejegyzese van!
                        }
                    }
                }
                this.animals.remove(ani);
                break;
            }
        }
        super.fireTableRowsDeleted(selectedRowIndex, selectedRowIndex);
    }

    @Override
    public void addRow(Object a) {
        this.animals.add((Animal) a);
        super.fireTableRowsInserted(this.getRowCount() - 1, this.getRowCount() - 1);
    }

}
