package abstracts;

import commons.FormComponent;
import commons.MainFrame;
import commons.Storage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * A controller ososztaly.
 */
public abstract class AbstractCRUDTableController implements ActionListener {

    protected TableViewBuilder view;
    protected JTable table;
    protected CRUDTableModel tableModel;
    protected Map<String, FormComponent> components;
    protected MainFrame frame = MainFrame.getInstance();

    /**
     *
     * @param view
     */
    public AbstractCRUDTableController(TableViewBuilder view) {
        this.view = view;
        this.table = view.getTable();
        this.tableModel = (CRUDTableModel) this.table.getModel();
        this.components = view.getAttributeComponents();
        this.view.getAddButton().addActionListener(this);
        this.view.getDeleteButton().addActionListener(this);
    }

    /**
     * A JComponentekbol kiolvassa az ertekeket, majd eloallitja beloluk az uj rekordot
     * Az adatok ide mar validalva erkeznek.Ennek a visszateresi erteke kasztolhato lesz.
     * @param attributes: Map<String, FormComponent>
     * @return Object
     */
    protected abstract Object createNewRecord(Map<String, FormComponent> attributes);

    /**
     * A torkes megerositest keri, az adott rekordra. El kell majd hoznia majd egy JOptionPane-t.
     * @param rowIndex: int
     */
    protected abstract boolean confirmRemoveElement(int rowIndex);

    /**
     * A tablazatbol kikeresi, hogy az adott idexu sorban levo elemnek mi az id-ja.
     * @param rowIndex:int
     * @return String
     */
    protected abstract String getRecordIdOfRow(int rowIndex);

    /**
     * A kaptt JComponensbol (JTextField vagy JComboBox) kiolvassa a tartalmat
     * A field azonositja azt, hogy melyik mezorol van is szo.
     * @param c: JComponent
     * @param field: String
     * @return String
     */
    protected abstract String getContentStringOf(JComponent c, String field);

    /**
     * Uresere allitja a JComponens (JTextField vagy JComboBox) tartalmat.
     *  Hozzadas utan fut le.
     *  A field azonositja azt, hogy melyik mezorol van is szo.
     * @param c: JComponent
     * @param field: String
     */
    protected abstract void setContentToEmpty(JComponent c, String field);


    /**
     * Elvegzi a torlest
     * Ha minden mezo valid, akkor hozzaad egy uj sor a tablahoz.
     */
    protected void handleAdditionEvent() {
        if (this.validateEverything()) {
            this.tableModel.addRow(this.createNewRecord(this.components));
            this.setEverythingToEmpty();
        }
    }

    /**
     * Validal mindent. Ugy hogy az osszes FormComponentnek meghivja a validatorat.
     * @return Boolean
     */
    protected boolean validateEverything() {
        boolean everythingIsValid = true;
        for (Map.Entry<String, FormComponent> entry : this.components.entrySet()) {
            FormComponent c = entry.getValue();
            if (!c.getValidator().isContentValid(this.getContentStringOf(c.getElement(), entry.getKey()), c.getFieldName())) {
                everythingIsValid = false;
                break;
            }
        }
        return everythingIsValid;
    }

    /**
     * Mindent uresre allit. Hozzaadas utan fut le (Pasztor eseten modositas utan)
     * */
    protected void setEverythingToEmpty() {
        for (Map.Entry<String, FormComponent> entry : this.components.entrySet()) {
            this.setContentToEmpty(entry.getValue().getElement(), entry.getKey());
        }
    }

    /**
     * A torlest viszi veghez, ugy, hogy eloszor megerositest ker.
     */
    protected void handleDeletionEvent() {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow != -1) {
            String selectedId = this.getRecordIdOfRow(selectedRow);
            if (Storage.TESTING_MODE || this.confirmRemoveElement(selectedRow)) {
                this.tableModel.removeRow(selectedId, selectedRow);
            }
        }
    }


    /**
     * Alapvetoen ezert csinaltam ilyen Controller osztalyt, ami a minden tablazatan kozos create-update-delete muveleteket vegrehajtja
     * Itt a mindenhol jelen levo, hozos 'Hozzaadas' es 'Torles' gombok eventjeit rendezi le.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.view.getAddButton()) {
            this.handleAdditionEvent();
        } else if (e.getSource() == this.view.getDeleteButton()) {
            this.handleDeletionEvent();
        }
    }
}
