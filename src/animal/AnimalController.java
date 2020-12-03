package animal;

import abstracts.AbstractCRUDTableController;
import commons.FormComponent;
import abstracts.TableViewBuilder;

import javax.swing.*;
import java.util.Map;

public class AnimalController extends AbstractCRUDTableController {

    public AnimalController(TableViewBuilder view) {
        super(view);
    }

    @Override
    protected Animal createNewRecord(Map<String, FormComponent> attributes) {
        String type = this.getContentStringOf(attributes.get(Animal.TYPE).getElement(), Animal.TYPE);
        int cost = Integer.parseInt(this.getContentStringOf(attributes.get(Animal.COST).getElement(), Animal.COST));
        return new Animal (type, cost);
    }

    @Override
    protected boolean confirmRemoveElement(int rowIndex) {
        String type = (String) this.table.getValueAt(rowIndex, AnimalTableModel.TYPE_COL);
        return this.frame.confirmAction("Are you sure about deleting that type (" + type + ")? \n It will disappear from every  related shepherd.");
    }

    @Override
    protected String getRecordIdOfRow(int rowIndex) {
       return (String) this.table.getValueAt(rowIndex, AnimalTableModel.ID_COL);

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
