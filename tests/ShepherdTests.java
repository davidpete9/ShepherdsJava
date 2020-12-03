import animal.Animal;
import animal.AnimalTableViewBuilder;
import commons.Storage;
import company.Company;
import company.CompanyTableViewBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import shepherd.Shepherd;
import shepherd.ShepherdTableModel;
import shepherd.ShepherdTableViewBuilder;
import shepherdsAnimal.ShepherdAndAnimalsViewBuilder;

import java.util.ArrayList;
import java.util.List;


public class ShepherdTests {

    private ShepherdTableViewBuilder twb;

    @Before
    public void setUp() {
        twb = new ShepherdTableViewBuilder();
        twb.switchToTargetView();
        Storage.TESTING_MODE = true;
    }

    /**
     * Paaztor hozzadas minden adattal
     */
    @Test
    public void addFullShepherdTest() {

        //uj company copy
        CompanyTableViewBuilder ctwb = new CompanyTableViewBuilder();
        ctwb.switchToTargetView();
        ctwb.nameField.setText("JUnit kft");
        ctwb.addressField.setText("Teszt utca 2");
        ctwb.getAddButton().doClick();
        List<Company> comps = Storage.getInstance().getCompanies();
        Company addedComp = Storage.getInstance().getCompanies().get(comps.size() - 1);


        twb.onEntered();
        twb.nameField.setText("Pasztor Peter");
        twb.areaField.setText("Hortobagy");
        twb.bornField.setText("1977-07-27");
        twb.addressField.setText("Java utca 42");
        twb.salaryField.setText("42000");
        twb.companyPicker.setSelectedItem(addedComp);

        twb.getAddButton().doClick();
        List<Shepherd> sheps = Storage.getInstance().getShepherds();
        Shepherd last = sheps.get(sheps.size() - 1);
        Assert.assertTrue("Nem keletkezett id", last.getId() != null);
        Assert.assertTrue("Nem egyezik az nev", last.getName().equals("Pasztor Peter"));
        Assert.assertTrue("Nem egyezik a area", last.getArea().equals("Hortobagy"));
        Assert.assertTrue("Nem egyezik az address", last.getAddress().equals("Java utca 42"));
        Assert.assertTrue("Nem egyezik az salary", last.getSalary() == 42000);
        Assert.assertTrue("Nem egyezik az borndate", Storage.dateToString(last.getBorn()).equals("1977-07-27"));
        Assert.assertTrue("Nem egyezik az employer", last.getEmployer().getId().equals(addedComp.getId()));

        int rowc = twb.getTable().getRowCount();
        String id = (String) twb.getTable().getValueAt(rowc-1, ShepherdTableModel.ID_COL);
        Assert.assertTrue("Nem kerult be a tablazatba az uj sor (id nem egyezik)", last.getId().equals(id));
    }

    /**
     * Pasztor hozzaadas teszt, csak a minimalisan kotelezo adatokkal.
     */
    @Test
    public void addMinimalShepherdTest() {

        twb.nameField.setText("Pasztor Peter");
        twb.areaField.setText("Hortobagy");

        twb.getAddButton().doClick();
        List<Shepherd> sheps = Storage.getInstance().getShepherds();
        Shepherd last = sheps.get(sheps.size() - 1);
        Assert.assertTrue("Nem keletkezett id", last.getId() != null);
        Assert.assertTrue("Nem egyezik az nev", last.getName().equals("Pasztor Peter"));
        Assert.assertTrue("Nem egyezik a area", last.getArea().equals("Hortobagy"));
        Assert.assertTrue("Nem null az address", last.getAddress().equals("") || last.getAddress() == null);
        Assert.assertTrue("Nem 0 a salary", last.getSalary() == 0);
        Assert.assertEquals("Nem null az borndate", last.getBorn(), null);
        Assert.assertEquals("Nem null az employer", last.getEmployer(), null);

        int rowc = twb.getTable().getRowCount();
        String id = (String) twb.getTable().getValueAt(rowc-1, ShepherdTableModel.ID_COL);
        Assert.assertTrue("Nem kerult be a tablazatba az uj sor (id nem egyezik)", last.getId().equals(id));
    }


    /**
     * PAsztor tolres teszt
     */
    @Test
    public void deleteShepherdTest() {

        //hozzadas copy
        twb.nameField.setText("Pasztor Peter");
        twb.areaField.setText("Hortobagy");

        twb.getAddButton().doClick();
        List<Shepherd> sheps = Storage.getInstance().getShepherds();
        Shepherd last = sheps.get(sheps.size() - 1);

        int rowc = twb.getTable().getRowCount();
        String id = (String) twb.getTable().getValueAt(rowc-1, ShepherdTableModel.ID_COL);
        Assert.assertTrue("Nem kerult be a tablazatba az uj sor (id nem egyezik)", last.getId().equals(id));


        ArrayList<Shepherd> original = (ArrayList<Shepherd>) ((ArrayList<Shepherd>) Storage.getInstance().getShepherds()).clone();

        twb.getTable().setRowSelectionInterval(rowc-1, rowc-1); //Kivalasztom az utolso sort
        twb.getDeleteButton().doClick();

        Assert.assertTrue("Nem tortent meg a torles!", original.size()-1 == Storage.getInstance().getShepherds().size());
        Assert.assertEquals("Nem jo element torlt", Storage.getShepherdById(last.getId()), null);

    }

    /**
     * Pasztor modositas teszt
     */
    @Test
    public void editShepherdTest() {

        //hozzadas copy
        twb.nameField.setText("Pasztor Peter");
        twb.areaField.setText("Hortobagy");

        twb.getAddButton().doClick();
        List<Shepherd> sheps = Storage.getInstance().getShepherds();
        Shepherd last = sheps.get(sheps.size() - 1);

        int rowc = twb.getTable().getRowCount();
        String id = (String) twb.getTable().getValueAt(rowc-1, ShepherdTableModel.ID_COL);
        Assert.assertTrue("Nem kerult be a tablazatba az uj sor (id nem egyezik)", last.getId().equals(id));


        ArrayList<Shepherd> original = (ArrayList<Shepherd>) ((ArrayList<Shepherd>) Storage.getInstance().getShepherds()).clone();

        twb.getTable().setRowSelectionInterval(rowc-1, rowc-1); //Kivalasztom az utolso sort
        twb.editButton.doClick();
        //es most modositok par mezot, elvileg ilyenkorra betoltotek az adatoka az input mezokbe
        Assert.assertEquals("Nem illesztette be az adatokat a formba", twb.nameField.getText(), last.getName());
        twb.nameField.setText("Pasztor Pal");
        twb.areaField.setText("Kiskunsag");
        twb.addressField.setText("Java utca 43");
        twb.getAddButton().doClick();
        Shepherd modified = Storage.getShepherdById(last.getId());
        Assert.assertTrue("Nem modosult az nev", modified.getName().equals("Pasztor Pal"));
        Assert.assertTrue("Nem modosult a area", modified.getArea().equals("Kiskunsag"));
        Assert.assertTrue("Nem modosult az address", modified.getAddress().equals("Java utca 43"));
    }

    /**
     * Pasztor-allat teszt
     * Hozzaad egy uj pasztort, egy uj allatot majd a pasztornak adja az allatot es torli
     */
    @Test
    public void addAndThanDeleteAnimalFromShepherd() {
        //Shepherd Hozzaadas copy
        twb.nameField.setText("Pasztor Gabor");
        twb.areaField.setText("Alfold");

        twb.getAddButton().doClick();
        List<Shepherd> sheps = Storage.getInstance().getShepherds();
        Shepherd lastShepherd = sheps.get(sheps.size() - 1);
        Assert.assertTrue("Nem animal keletkezett id", lastShepherd.getId() != null);

        //Animal hozzaadas copy
        AnimalTableViewBuilder atwb = new AnimalTableViewBuilder();
        atwb.costField.setText("1000");
        atwb.typeField.setText("Kecske");
        atwb.getAddButton().doClick();
        List<Animal> afterModify = Storage.getInstance().getAnimals();
        Animal lastAnimal = afterModify.get(afterModify.size() - 1);
        Assert.assertTrue("Nem keletkezett animal id", lastAnimal.getId() != null);

        ShepherdAndAnimalsViewBuilder sawb = new ShepherdAndAnimalsViewBuilder(lastShepherd.getId());
        sawb.switchToTargetView();
        sawb.quantityField.setText("42");
        sawb.animalChoose.setSelectedItem(lastAnimal);
        sawb.getAddButton().doClick();

        Assert.assertEquals("Nem keletkezett shepherdAnimal", lastShepherd.getAnimals().size(), 1);
        Assert.assertEquals("Nem egyezik az allatid", lastShepherd.getAnimals().get(0).getAnimal().getId(), lastAnimal.getId());
        Assert.assertEquals("Nem egyezik a mennyiseg", lastShepherd.getAnimals().get(0).getQuantity(), 42);

        //Torles

        sawb.getTable().setRowSelectionInterval(0, 0); //Kivalasztom az utolso sort
        sawb.getDeleteButton().doClick();

        Assert.assertEquals("Nem tortent meg a torles!", lastShepherd.getAnimals().size(), 0);
    }
}
