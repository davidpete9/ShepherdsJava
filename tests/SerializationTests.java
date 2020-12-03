import animal.Animal;
import animal.AnimalTableViewBuilder;
import commons.*;
import company.Company;
import company.CompanyTableViewBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import shepherd.Shepherd;
import shepherd.ShepherdTableViewBuilder;
import shepherdsAnimal.ShepherdAndAnimalsViewBuilder;

import java.util.List;

public class SerializationTests {

    MainFrame frame;
    AnimalTableViewBuilder atwb;
    ShepherdTableViewBuilder stwb;
    CompanyTableViewBuilder ctwb;

    @Before
    public void setUp() {

        Storage.TESTING_MODE = true;
        Storage.getInstance().reset();

        frame = MainFrame.getInstance();
        atwb = new AnimalTableViewBuilder();
        atwb.switchToTargetView();
        stwb = new ShepherdTableViewBuilder();
        stwb.switchToTargetView();;
        ctwb = new CompanyTableViewBuilder();
        ctwb.switchToTargetView();
        AnimalRepository.FILEPATH = "animals_test.json";
        ShepherdRepository.FILEPATH = "shepherds_test.json";
        CompanyRepository.FILEPATH = "companies_test.json";


    }

    @After
    public void cleanUp() {
        Storage.getInstance().reset();
        Storage.getInstance().saveAllToFile();
        AnimalRepository.FILEPATH = "animals.json";
        ShepherdRepository.FILEPATH = "shepherds.json";
        CompanyRepository.FILEPATH = "companies.json";
        Storage.getInstance().reset();
        Storage.getInstance().loadAllFromFile();
    }

    /**
     * Allat hozzadas-mentes-betoltes teszt
     */
    @Test
    public void AnimalSerializationTest() {
        //hozzaadas copy
        atwb.costField.setText("1000");
        atwb.typeField.setText("Kecske");
        atwb.getAddButton().doClick();
        List<Animal> afterModify = Storage.getInstance().getAnimals();
        Animal last = afterModify.get(afterModify.size() - 1);
        Assert.assertTrue("Nem keletkezett id", last.getId() != null);

        //mentes es betoltes
        Storage.getInstance().saveAllToFile();
        Storage.getInstance().loadAllFromFile();

        Animal saved = Storage.getAnimalById(last.getId());
        Assert.assertTrue("Nincs meg amit elmentettem",  saved != null);
        Assert.assertTrue("Nem egyezik az id",  saved.getId().equals(last.getId()));
        Assert.assertTrue("Nem egyezik a type",  saved.getType().equals(last.getType()));
        Assert.assertTrue("Nem egyezik a cost",  saved.getAvg_cost() == last.getAvg_cost());

        //cleanup
        atwb.getTable().setRowSelectionInterval(0, 0); //Kivalasztom az egyetlen sort
        atwb.getDeleteButton().doClick();
    }

    /**
     * Ceg hozzadas-mentes-betoltes teszt
     */
    @Test
    public void CompanySerializationTest() {
        ctwb.nameField.setText("JUnit kft");
        ctwb.leaderField.setText("Teszt Tamas");
        ctwb.accountField.setText("123456");
        ctwb.addressField.setText("Java utca 42");
        ctwb.getAddButton().doClick();
        List<Company> afterModify = Storage.getInstance().getCompanies();
        Company last = afterModify.get(afterModify.size() - 1);

        //mentes es betoltes
        Storage.getInstance().saveAllToFile();
        Storage.getInstance().loadAllFromFile();

        Company saved = Storage.getCompanyById(last.getId());
        Assert.assertTrue("Nincs meg amit elmentettem",  saved != null);
        Assert.assertTrue("Nem egyezik az id",  saved.getId().equals(last.getId()));
        Assert.assertTrue("Nem egyezik az nev", last.getName().equals(saved.getName()));
        Assert.assertTrue("Nem egyezik a leader", last.getLeader().equals(saved.getLeader()));
        Assert.assertTrue("Nem egyezik az account", last.getAccount().equals(saved.getAccount()));
        Assert.assertTrue("Nem egyezik az address", last.getAddress().equals(saved.getAddress()));

        //cleanup
        ctwb.getTable().setRowSelectionInterval(0, 0); //Kivalasztom az egyetlen sort
        ctwb.getDeleteButton().doClick();
    }

    /**
     * Pasztor hozzadas-mentes-betoltes teszt
     * Mindent tesztel, vagyis keszul a pasztorhoz employer es keszulnek allatok.
     */
    @Test
    public void ShepherdSerializationTest() {
        //uj shepherd mindennel copy

        //uj company copy

        ctwb.switchToTargetView();
        ctwb.nameField.setText("JUnit kft");
        ctwb.addressField.setText("Teszt utca 2");
        ctwb.getAddButton().doClick();
        List<Company> comps = Storage.getInstance().getCompanies();
        Company addedComp = Storage.getInstance().getCompanies().get(comps.size() - 1);

        //allat hozzaadas

        atwb.costField.setText("1000");
        atwb.typeField.setText("Kecske");
        atwb.getAddButton().doClick();
        List<Animal> afterModify = Storage.getInstance().getAnimals();
        Animal lastAnimal = afterModify.get(afterModify.size() - 1);

        //shepherd hozzadas
        stwb.onEntered();
        stwb.nameField.setText("Pasztor Peter");
        stwb.areaField.setText("Hortobagy");
        stwb.bornField.setText("1977-07-27");
        stwb.addressField.setText("Java utca 42");
        stwb.salaryField.setText("42000");
        stwb.companyPicker.setSelectedItem(addedComp);

        stwb.getAddButton().doClick();
        List<Shepherd> sheps = Storage.getInstance().getShepherds();
        Shepherd lastShepherd = sheps.get(sheps.size() - 1);

        //shepherd-animal hozzadas
        ShepherdAndAnimalsViewBuilder sawb = new ShepherdAndAnimalsViewBuilder(lastShepherd.getId());
        sawb.switchToTargetView();
        sawb.quantityField.setText("42");
        sawb.animalChoose.setSelectedItem(lastAnimal);
        sawb.getAddButton().doClick();

        //mentes es betoltes
        Storage.getInstance().saveAllToFile();
        Storage.getInstance().loadAllFromFile();
        Shepherd saved = Storage.getShepherdById(lastShepherd.getId());

        Assert.assertTrue("Nincs meg amit elmentettem",  saved != null);
        Assert.assertTrue("Nem egyezik az id", saved.getId().equals(lastShepherd.getId()));
        Assert.assertTrue("Nem egyezik az nev", saved.getName().equals(lastShepherd.getName()));
        Assert.assertTrue("Nem egyezik a area", saved.getArea().equals(lastShepherd.getArea()));
        Assert.assertTrue("Nem egyezik az address", saved.getAddress().equals(lastShepherd.getAddress()));
        Assert.assertTrue("Nem egyezik az salary", saved.getSalary() == lastShepherd.getSalary());
        Assert.assertTrue("Nem egyezik az borndate", Storage.dateToString(saved.getBorn()).equals(Storage.dateToString(lastShepherd.getBorn())));
        Assert.assertTrue("Nem egyezik az employer", saved.getEmployer().getId().equals(lastShepherd.getEmployer().getId()));
        Assert.assertEquals("Nem keletkezett shepherdAnimal", saved.getAnimals().size(), 1);
        Assert.assertEquals("Nem egyezik az allatid", saved.getAnimals().get(0).getAnimal().getId(), lastShepherd.getAnimals().get(0).getAnimal().getId());
        Assert.assertEquals("Nem egyezik a mennyiseg", lastShepherd.getAnimals().get(0).getQuantity(), lastShepherd.getAnimals().get(0).getQuantity());

        //cleanup
        atwb.getTable().setRowSelectionInterval(0, 0);
        atwb.getDeleteButton().doClick();

        ctwb.getTable().setRowSelectionInterval(0, 0);
        ctwb.getDeleteButton().doClick();

        stwb.getTable().setRowSelectionInterval(0, 0);
        stwb.getDeleteButton().doClick();

    }


}
