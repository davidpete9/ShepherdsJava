package abstracts;

import javax.swing.table.AbstractTableModel;

public abstract class CRUDTableModel extends AbstractTableModel {

    /**
     * A megadott id-val rendelkezo rekordot torli kell a fo kollekciobol es utana frissitane a tablazatot.
     * @param selectedEntityId: int
     * @param selectedRowIndex: int
     */
    public abstract void removeRow(String selectedEntityId, int selectedRowIndex);

    /**
     * Egy uj rekord kerul hozzaadasra a fo kollekciohoz. A kopott Object parameternek
     * kasztolhanonak kell lenni az aktualis tipushoz.
     * Frissitenie kell majd a tablazatot a hozzaadas utan.
     * @param o: Object
     */
    public abstract void addRow(Object o);
}
