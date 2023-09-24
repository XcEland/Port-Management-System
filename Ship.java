import java.util.*;
public class Ship implements IShip {
    private int ID;
    private double fuel;
    private Port currentPort;
    private int totalWeightCapacity;
    private int maxNumberOfAllContainers;
    private int maxNumberOfHeavyContainer;
    private int maxNumberOfRefrigeratedContainers;
    private int maxNumberOfLiquidContainers;
    private double fuelConsumptionPerKM;
    private ArrayList<Container> containers;

    public Ship(int ID, Port p, int totalWeightCapacity, int maxNumberOfAllContainers, int maxNumberOfHeavyContainers,
                int maxNumberOfRefrigeratedContainers, int maxNumberOfLiquidContainers, double fuelConsumptionPerKM) {
        this.ID = ID;
        this.currentPort = p;
        this.fuel = 0.0;
        this.totalWeightCapacity = totalWeightCapacity;
        this.maxNumberOfAllContainers = maxNumberOfAllContainers;
        this.maxNumberOfHeavyContainer = maxNumberOfHeavyContainers;
        this.maxNumberOfLiquidContainers = maxNumberOfLiquidContainers;
        this.maxNumberOfRefrigeratedContainers = maxNumberOfRefrigeratedContainers;
        this.fuelConsumptionPerKM = fuelConsumptionPerKM;
        this.containers = new ArrayList<>();
    }

    public ArrayList<Container> getCurrentContainers() {
        // Return the list of containers currently in the ship sorted by ID
        ArrayList<Container> sortedContainers = new ArrayList<>(containers);
        sortedContainers.sort((c1, c2) -> Integer.compare(c1.getID(), c2.getID()));
        return sortedContainers;
    }

    @Override
    public boolean sailTo(Port destinationPort) {
        double distance = currentPort.getDistance(destinationPort);
        double fuelConsumption = fuelConsumptionPerKM * distance;

        if (fuel >= fuelConsumption) {
            fuel -= fuelConsumption;
            currentPort.outgoingShip(this);
            currentPort = destinationPort;
            destinationPort.incomingShip(this);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void reFuel(double newFuel) {
        fuel += newFuel;
    }

    @Override
    public boolean load(Container cont) {
        // Check if the ship has the capacity to load the container
        if (containers.size() >= maxNumberOfAllContainers ||
                (cont instanceof HeavyConytainer && containers.size() >= maxNumberOfHeavyContainer) ||
                (cont instanceof RefrigratedContainer && containers.size() >= maxNumberOfRefrigeratedContainers) ||
                (cont instanceof LiquidContainer && containers.size() >= maxNumberOfLiquidContainers) ||
                getTotalWeight() + cont.getWeight() > totalWeightCapacity) {
            return false; // Loading not possible
        }

        containers.add(cont);
        return true; // Container loaded successfully
    }

    @Override
    public boolean unLoad(Container cont) {
        if (containers.contains(cont)) {
            containers.remove(cont);
            //   currentPort.addContainerToStorage(cont);
            return true; // Container unloaded successfully
        } else {
            return false; // Container not found on the ship
        }
    }

    private int getTotalWeight() {
        int totalWeight = 0;
        for (Container cont : containers) {
            totalWeight += cont.getWeight();
        }
        return totalWeight;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }

    public int getTotalWeightCapacity() {
        return totalWeightCapacity;
    }

    public void setTotalWeightCapacity(int totalWeightCapacity) {
        this.totalWeightCapacity = totalWeightCapacity;
    }

    public int getMaxNumberOfAllContainers() {
        return maxNumberOfAllContainers;
    }

    public void setMaxNumberOfAllContainers(int maxNumberOfAllContainers) {
        this.maxNumberOfAllContainers = maxNumberOfAllContainers;
    }

    public int getMaxNumberOfHeavyContainer() {
        return maxNumberOfHeavyContainer;
    }

    public void setMaxNumberOfHeavyContainer(int maxNumberOfHeavyContainer) {
        this.maxNumberOfHeavyContainer = maxNumberOfHeavyContainer;
    }

    public int getMaxNumberOfRefrigeratedContainers() {
        return maxNumberOfRefrigeratedContainers;
    }

    public void setMaxNumberOfRefrigeratedContainers(int maxNumberOfRefrigeratedContainers) {
        this.maxNumberOfRefrigeratedContainers = maxNumberOfRefrigeratedContainers;
    }

    public int getMaxNumberOfLiquidContainers() {
        return maxNumberOfLiquidContainers;
    }

    public void setMaxNumberOfLiquidContainers(int maxNumberOfLiquidContainers) {
        this.maxNumberOfLiquidContainers = maxNumberOfLiquidContainers;
    }

    public double getFuelConsumptionPerKM() {
        return fuelConsumptionPerKM;
    }

    public void setFuelConsumptionPerKM(double fuelConsumptionPerKM) {
        this.fuelConsumptionPerKM = fuelConsumptionPerKM;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public void setContainers(ArrayList<Container> containers) {
        this.containers = containers;
    }

    public Container findContainerById(int containerId) {
        for (Container container : containers) {
            if (container.getID() == containerId) {
                return container;
            }
        }
        return null;
    }
}




















