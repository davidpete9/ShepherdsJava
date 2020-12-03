import commons.Storage;
import company.Company;
import company.CompanyTableModel;
import company.CompanyTableViewBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class CompanyTests {

    private CompanyTableViewBuilder twb;

    @Before
    public void setUp() {
        twb = new CompanyTableViewBuilder();
        twb.switchToTargetView();
        Storage.TESTING_MODE = true;

    }

    /**
     * Ceg hozzadas teszt
     */
    @Test
    public void addCompanyTest() {

        twb.nameField.setText("JUnit kft");
        twb.leaderField.setText("Teszt Tamas");
        twb.accountField.setText("123456");
        twb.addressField.setText("Java utca 42");
        twb.getAddButton().doClick();
        List<Company> afterModify = Storage.getInstance().getCompanies();
        Company last = afterModify.get(afterModify.size() - 1);
        Assert.assertTrue("Nem keletkezett id", last.getId() != null);
        Assert.assertTrue("Nem egyezik az nev", last.getName().equals("JUnit kft"));
        Assert.assertTrue("Nem egyezik a leader", last.getLeader().equals("Teszt Tamas"));
        Assert.assertTrue("Nem egyezik az account", last.getAccount().equals("123456"));
        Assert.assertTrue("Nem egyezik az address", last.getAddress().equals("Java utca 42"));

        int rowc = twb.getTable().getRowCount();
        String id = (String) twb.getTable().getValueAt(rowc-1, CompanyTableModel.ID_COL);
        Assert.assertTrue("Nem kerult be a tablazatba az uj sor (id nem egyezik)", last.getId().equals(id));
    }

    /**
     * Ceg torles teszt
     */
    @Test
    public void deleteCompanyTest() {

        //Hozzaadas copy
        twb.nameField.setText("JUnit kft");
        twb.leaderField.setText("Teszt Tamas");
        twb.accountField.setText("123456");
        twb.addressField.setText("Java utca 42");
        twb.getAddButton().doClick();
        List<Company> afterModify = Storage.getInstance().getCompanies();
        Company last = afterModify.get(afterModify.size() - 1);

        int rowc = twb.getTable().getRowCount();
        String id = (String) twb.getTable().getValueAt(rowc-1, CompanyTableModel.ID_COL);
        Assert.assertTrue("Nem kerult be a tablazatba az uj sor (id nem egyezik)", last.getId().equals(id));


        ArrayList<Company> original = (ArrayList<Company>) ((ArrayList<Company>) Storage.getInstance().getCompanies()).clone();

        twb.getTable().setRowSelectionInterval(rowc-1, rowc-1); //Kivalasztom az utolso sort
        twb.getDeleteButton().doClick();

        Assert.assertTrue("Nem tortent meg a torles!", original.size()-1 == Storage.getInstance().getCompanies().size());
        Assert.assertEquals("Nem jo element torlt", Storage.getCompanyById(last.getId()), null);
    }
}
