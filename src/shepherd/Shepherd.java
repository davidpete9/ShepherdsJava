package shepherd;

import company.Company;
import shepherdsAnimal.ShepherdAnimal;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Shepherd {

    public static final String NAME = "name";
    public static final String AREA = "area";
    public static final String ADDRESS = "address";
    public static final String BORN = "born";
    public static final String EMPLOYER = "employer";
    public static final String SALARY = "salary";

    private String id;
    private String name;
    private String area;
    private List<ShepherdAnimal> animals;
    private Date born;
    private String address;
    private int salary;
    private Company employer;

    public int sumAllAnimalCost() {
        return this.getAnimals().stream().mapToInt(i -> i.getAnimal().getAvg_cost()*i.getQuantity()).sum();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArea() {
        return area;
    }

    public List<ShepherdAnimal> getAnimals() {
        return animals;
    }

    public Date getBorn() {
        return born;
    }

    public String getAddress() {
        return address;
    }

    public Company getEmployer() {
        return employer;
    }

    public int getSalary() {
        return salary;
    }

    public Shepherd(String name, String area, List<ShepherdAnimal> animals, Date born, String address, Company employer, int salary) {
        this(UUID.randomUUID().toString().replace("-", ""), name, area, animals, born, address, employer, salary);
    }

    public Shepherd(String id, String name, String area, List<ShepherdAnimal> animals, Date born, String address, Company employer, int salary) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.animals = animals;
        this.born = born;
        this.address = address;
        this.employer = employer;
        this.salary = salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setBorn(Date born) {
        this.born = born;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setEmployer(Company employer) {
        this.employer = employer;
    }

    public String toString() {
        return this.getName();
    }
}

