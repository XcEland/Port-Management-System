
abstract class Container {
    private int ID;
    private int weight;
    private int portId;
    private Port portID;


    public Container(int ID, int weight, int portId) {
        this.ID = ID;
        this.weight = weight;
        this.portId = portId;
    }
//+263784358898c
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

    public void setPortID(int portId) {
        this.portId = portId;
    }

    public int getPortID() {
        return portId;
    }

    public void setPortID(Port portId) {
        this.portID = portId;
    }

    public Port getPoRtID() {
        return portID;
    }

}


