import java.util.ArrayList;
public class Port implements IPort {
    private  int ID;
    private double latitude;
    private double longitude;
    private ArrayList<Container> containers;
    private ArrayList<Ship> history ; //keeps track of every ship that has visited;
    private ArrayList<Ship> current ; //keeps track of the ships currently here

    public Port (int ID,double Latitude ,double longitude){
        this.ID=ID;
        this.latitude=Latitude;
        this.longitude=longitude;
        this.containers=new ArrayList<>();
        this.history=new ArrayList<>();
        this.current=new ArrayList<>();
    }
     public double getDistance(Port other){
         double latDiff = Math.toRadians(other.latitude - this.latitude);
         double lonDiff = Math.toRadians(other.longitude - this.longitude);
         double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                 + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(other.latitude))
                 * Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);
         double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
         double distance = 6371 * c; // Earth's radius in kilometers
         return distance;

    }

    public void incomingShip(Ship s){
        current.add(s);
    }

   public void outgoingShip(Ship s){
        history.add(s);
   }

   public  void getShips(Ship S){
    
   }

    public void unloadContainer(Container container) {
        containers.remove(container);
    }















    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public void setContainers(ArrayList<Container> containers) {
        this.containers = containers;
    }

    public ArrayList<Ship> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Ship> history) {
        this.history = history;
    }

    public ArrayList<Ship> getCurrent() {
        return current;
    }

    public void setCurrent(ArrayList<Ship> current) {
        this.current = current;
    }
}
