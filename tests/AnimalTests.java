import animal.Animal;
import animal.AnimalTableModel;
import animal.AnimalTableViewBuilder;
import commons.Storage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class AnimalTests {

    private AnimalTableViewBuilder atwb;

    @Before
    public void setUp() {
        atwb = new AnimalTableViewBuilder();
        atwb.switchToTargetView();
        Storage.TESTING_MODE = true;

    }


    /**
     * Allat hozzadas teszt
     */
    @Test
    public void addAnimalTest() {

        atwb.costField.setText("1000");
        atwb.typeField.setText("Kecske");
        atwb.getAddButton().doClick();
        List<Animal> afterModify = Storage.getInstance().getAnimals();
        Animal last = afterModify.get(afterModify.size() - 1);
        Assert.assertTrue("Nem keletkezett id", last.getId() != null);
        Assert.assertTrue("Nem egyezik az type", last.getType().equals("Kecske"));
        Assert.assertTrue("Nem egyezik a cost", last.getAvg_cost() == 1000);
        int rowc = atwb.getTable().getRowCount();
        String id = (String) atwb.getTable().getValueAt(rowc-1, AnimalTableModel.ID_COL);
        Assert.assertTrue("Nem kerult be a tablazatba az uj sor (id nem egyezik)", last.getId().equals(id));
    }

    /**
     * Allat torles teszt
     */
    @Test
    public void deleteAnimalTest() {

        //Hozzaadas copy
        atwb.costField.setText("1000");
        atwb.typeField.setText("Kecske");
        atwb.getAddButton().doClick();
        List<Animal> afterModify = Storage.getInstance().getAnimals();
        Animal last = afterModify.get(afterModify.size() - 1);

        int rowc = atwb.getTable().getRowCount();
        String id = (String) atwb.getTable().getValueAt(rowc-1, AnimalTableModel.ID_COL);
        Assert.assertTrue("Nem kerult be a tablazatba az uj sor (id nem egyezik)", last.getId().equals(id));


        ArrayList<Animal> original = (ArrayList<Animal>) ((ArrayList<Animal>) Storage.getInstance().getAnimals()).clone();

        atwb.getTable().setRowSelectionInterval(rowc-1, rowc-1); //Kivalasztom az utolso sort
        atwb.getDeleteButton().doClick();

        Assert.assertTrue("Nem tortent meg a torles!", original.size()-1 == Storage.getInstance().getAnimals().size());
        Assert.assertEquals("Nem jo element torlt", Storage.getAnimalById(last.getId()), null);
    }

}
