package commons;

import animal.Animal;
import company.Company;
import shepherd.Shepherd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * singleton
 * A fo kollekciokat tartalmazo osztaly
 */
public class Storage {

    public static boolean TESTING_MODE = false; //Ha true akkor nem mutat torleskor megerosito JOPTIONPANE-t mert azt a tesztben nem tudom bezarni.

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private List<Shepherd> shepherds;
    private List<Company> companies;
    private List<Animal> animals;

    private static Storage SINGLE_INSTANCE = null;

    private List<BaseRepository> repositories;

    private Storage() {
        this.shepherds = new ArrayList<Shepherd>();
        this.companies = new ArrayList<Company>();
        this.animals = new ArrayList<Animal>();

        this.repositories = new ArrayList<BaseRepository>();

        //Fontos a sorrend, mivel, mikor a pasztorokat toltom be mar bent kell lennie a
        //vallalatoknak es az allatoknak is.
        this.repositories.add(new CompanyRepository());
        this.repositories.add(new AnimalRepository());
        this.repositories.add(new ShepherdRepository());
    }


    /**
     * teszelesi celkora
     */
    public void reset() {
        this.shepherds = new ArrayList<Shepherd>();
        this.companies = new ArrayList<Company>();
        this.animals = new ArrayList<Animal>();
    }

    public static Storage getInstance() {
        if (SINGLE_INSTANCE == null)
            SINGLE_INSTANCE = new Storage();

        return SINGLE_INSTANCE;
    }

    /**
     * A betoltest haj(tatt)ja vegre.
     * Minden repositorynak meghivja a beolvaso fuggvenet, melyek ennek az osztalyoknak a kollekcioiba irnak.
     * Ablak menyitasakor fut le.
     */
    public void loadAllFromFile() {
        for (BaseRepository r : this.repositories) {
            r.readFromJSONFile();
        }
    }

    /**
     * Mentes vegrehajtasa.
     * Minden repositorynak meghivja a kiiro fuggvenet. Az ablak bezarasakor fut le.
     */
    public void saveAllToFile() {
        for (BaseRepository r : this.repositories) {
            r.writeToJSONFile();
        }
    }

    public List<Shepherd> getShepherds() {
        return shepherds;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    /**
     * Segedfgv, String-Date konverzio
     * Ures szoveg eseten null-al ter vissza
     * @param dateStr: String
     * @return Date
     */
    public static Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.equals("")) {
            return null;
        }
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Date cant be parsed.. ");
        }
        return null;
    }

    /**
     * Segedfgv, Date-String konverzio.
     * Null datum eseten csak ures szoveggel ter vissza.
     * @param d: Date
     * @return String
     */
    public static String dateToString(Date d) {
        if (d == null) {
            return "";
        }
        DateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(d);
    }

    /**
     * Segedfgv, visszaadja azt a pasztort, aki a megadott id-val rendelkezik
     * @param id: String
     * @return Shepherd
     */
    public static Shepherd getShepherdById(String id) {
        List<Shepherd> shepherds = Storage.getInstance().getShepherds();
        for (Shepherd s : shepherds) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Segedfgv, visszaadja azt az allatot, aki a megadott id-val rendelkezik
     * @param id: String
     * @return Animal
     */
    public static Animal getAnimalById(String id) {
        for (Animal a : Storage.getInstance().getAnimals()) {
            if (a.getId().equals(id)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Segedfgv, visszaadja azt a ceget, aki a megadott id-val rendelkezik
     * @param id: String
     * @return Company
     */
    public static Company getCompanyById(String id) {
        for (Company c : Storage.getInstance().getCompanies()) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

}

interface JSONSerialization {
    /**
     * Meg kell valositania JSON fajlbol valo beolvasat, es az adatok atkonvertalasat Java Objectekre.
     */
    void readFromJSONFile();

    /**
     * A kollekciok tartalmabol szabvanyos JSON-t kell eloallitani, es azt beleirni fajlkba.
     */
    void writeToJSONFile();
}

abstract class BaseRepository implements JSONSerialization {


    /**
     * Beolvasat a megadott fajlbol egyetlen String-be.
     * @param path: String
     * @return String
     */
    protected String readFileToString(String path) {
        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get(path)));
        } catch (FileNotFoundException e) {
            System.out.println("A fajl nem talalhato!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    /**
     * A kapott Stringet beirja a fajlba. Ugy, hogy lecsereli a fajl tartalmat vele.
     * @param path: String
     * @param content: String
     */
    protected void saveStringToFile(String path, String content) {
        File f = new File(path);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(f);
            fw.write(content);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public abstract void readFromJSONFile();

    @Override
    public abstract void writeToJSONFile();
}

