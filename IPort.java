public interface IPort {
    void incomingShip(Ship s); // should add this ship to current ArrayList.
    void outgoingShip(Ship s); //should add this ship to history ArrayList.
}
