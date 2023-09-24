import java.util.*;
abstract class Container {
    private int ID;
    private int weight;


    public Container(int ID, int weight) {
        this.ID = ID;
        this.weight = weight;
    }

    abstract double consumption();

    boolean equals(Container other) {
        return this.getClass() == other.getClass() && this.ID == other.ID && this.weight == other.weight;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}


