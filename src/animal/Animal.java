package animal;

import java.util.UUID;

public class Animal {

    public static final String COST = "cost";
    public static final String TYPE = "type";

    public String getId() {
        return id;
    }

    public int getAvg_cost() {
        return avg_cost;
    }

    private String id;

    private String type;
    private int avg_cost;

    public Animal(String type, int average_cost) {
        this(UUID.randomUUID().toString().replace("-", ""), type, average_cost);
    }

    public Animal(String id, String type, int average_cost) {
        this.id = id;
        this.type = type;
        this.avg_cost = average_cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAvg_cost(int avg_cost) {
        this.avg_cost = avg_cost;
    }

    public String toString() {
        return this.getType();
    }

}
