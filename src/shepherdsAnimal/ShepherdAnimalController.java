package shepherdsAnimal;

import abstracts.AbstractCRUDTableController;
import commons.FormComponent;
import abstracts.TableViewBuilder;
import animal.Animal;

import javax.swing.*;
import java.util.Map;

public class ShepherdAnimalController extends AbstractCRUDTableController {

    public ShepherdAnimalController(TableViewBuilder view) {
        super(view);
    }

    @Override
    protected ShepherdAnimal createNewRecord(Map<String, FormComponent> attributes) {
        Animal a = (Animal)((JComboBox<Animal>) attributes.get(ShepherdAnimal.ANIMAL).getElement()).getSelectedItem();
        int q = Integer.parseInt(this.getContentStringOf(attributes.get(ShepherdAnimal.QUANTITY).getElement(), ShepherdAnimal.QUANTITY));
        return new ShepherdAnimal (a, q);
    }

    @Override
    protected boolean confirmRemoveElement(int rowIndex) {
        String name = (String) this.table.getValueAt(rowIndex, ShepherdAnimalTableModel.ANIMAL_COL);
        return this.frame.confirmAction("Are you sure about deleting this animal (" + name + ") from the shepherd? ");
    }

    @Override
    protected String getRecordIdOfRow(int rowIndex) {
        return (String) this.table.getValueAt(rowIndex, ShepherdAnimalTableModel.ID_COL);
    }

    @Override
    protected String getContentStringOf(JComponent c, String field) {
        if (field.equals(ShepherdAnimal.ANIMAL)) {
            Object selected = ((JComboBox<Animal>) c).getSelectedItem();
            if (selected == null || selected.equals("")) {
                return null;
            }
            return ((Animal) selected).getId();
        }
        return ((JTextField) c).getText();
    }

    @Override
    protected void setContentToEmpty(JComponent c, String field) {
        if (field.equals(ShepherdAnimal.ANIMAL)) {
            ((JComboBox<Animal>) c).setSelectedIndex(0);
        }
        else {
            ((JTextField) c).setText("");
        }

    }
}
