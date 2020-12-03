package shepherdsAnimal;

import animal.Animal;

public class ShepherdAnimal {

    public static final String ANIMAL = "animal";
    public static final String QUANTITY = "quantity";

    private Animal animal;
    private int quantity;

    public ShepherdAnimal(Animal animal, int quantity) {
        this.animal = animal;
        this.quantity = quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Animal getAnimal() {
        return animal;
    }

    public int getQuantity() {
        return quantity;
    }
}
