import java.util.ArrayList;
public class Ship implements IShip {
    private int ID;
    private double fuel;
    private Port currentPort;
    private int totalWeightCapacity;
    private int maxNumberOfAllContainers;
    private int maxNumberOfHeavyContainer;
    private int maxNumberOfRefrigeratedContainers;
    private int maxNumberOfLiquidContainers;
    private int maxNumberOfBasicContainers;
    private double fuelConsumptionPerKM;
    double fuelTankCapacity;
    private ArrayList<Container> containers;
    int heavyContainerCount = 0;
    int liquidContainerCount = 0;
	int refrigeratedContainerCount = 0;
    int basicContainerCount = 0;
    int totalContainersCount = 0;

    public Ship(int ID, Port p, int totalWeightCapacity, int maxNumberOfAllContainers, int maxNumberOfHeavyContainers,
                int maxNumberOfRefrigeratedContainers, int maxNumberOfLiquidContainers, double fuelConsumptionPerKM, double fuelTankCapacity) {
        this.ID = ID;
        this.currentPort = p;
        this.fuel = 0.0;
        this.fuelTankCapacity = fuelTankCapacity;
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

    public int maxNumberOfBasicContainers(){
        return maxNumberOfAllContainers - (maxNumberOfHeavyContainer + maxNumberOfLiquidContainers + maxNumberOfRefrigeratedContainers);
    }

    @Override
    public boolean sailTo(Port destinationPort) {
        double fuelConsumption = fuelRequired(destinationPort);
    
        if (fuel >= fuelConsumption) {
            fuel -= fuelConsumption;
            currentPort.outgoingShip(this);
            currentPort = destinationPort;
            destinationPort.incomingShip(this);
    
            // Update the port for each container on the ship
            for (Container container : containers) {
                container.setPortID(destinationPort);
            }
            return true;
        } else {
            return false;
        }
    }

    public double fuelRequired(Port destinationPort){
        double distance = currentPort.getDistance(destinationPort);
        double fuelConsumption = fuelConsumptionPerKM * distance;
        return fuelConsumption;
    }

    @Override
    public void reFuel(double newFuel) {
        fuel += newFuel;
    }

    @Override
    public boolean load(Container cont) {
        // Check if the ship has the capacity to load the container
    
        if (containers.size() >= maxNumberOfAllContainers) {
            // Return false if the maximum number of all containers is reached
            System.out.println("maximum number of all containers is reached");
            return false; // Loading not possible
        }
    
        if (cont instanceof RefrigeratedContainer && countRefrigeratedContainers() >= maxNumberOfRefrigeratedContainers) {
            // Return false if the maximum number of refrigerated containers is reached
            System.out.println("maximum number of refrigerated containers is reached");
            return false; // Loading not possible
        }
    
        if (cont instanceof LiquidContainer && countLiquidContainers() >= maxNumberOfLiquidContainers) {
            // Return false if the maximum number of liquid containers is reached
            System.out.println("maximum number of liquid containers is reached");
            return false; // Loading not possible
        }

        if (cont instanceof BasicContainer && countBasicContainers() >= maxNumberOfBasicContainers()) {
            // Return false if the maximum number of basic containers is reached
            System.out.println("maximum number of liquid containers is reached");
            return false; // Loading not possible
        }

        if (cont instanceof HeavyContainer && countHeavyContainers() >= maxNumberOfHeavyContainer) {
            // Return false if the maximum number of heavy containers is reached
            System.out.println("maximum number of heavy containers is reached");
            return false; // Loading not possible
        }
    
        if (getTotalWeight() + cont.getWeight() > totalWeightCapacity) {
            // Return false if the total weight capacity is exceeded
            System.out.println("total weight capacity is exceeded");
            return false; // Loading not possible
        }
    
        // If none of the conditions are met, loading is possible
        containers.add(cont);
        incrementContainerCounters(cont);
        return true; // Container loaded successfully
    }

    public int countHeavyContainers() {
        for (Container container : containers) {
            if (container instanceof HeavyContainer && (!(container instanceof RefrigeratedContainer)) && (!(container instanceof LiquidContainer)) ){
                heavyContainerCount++;
                totalContainersCount ++;
            }
        }
        return heavyContainerCount;
    }
    
    public int countRefrigeratedContainers() {
        for (Container container : containers) {
            if (container instanceof RefrigeratedContainer) {
                refrigeratedContainerCount++;
                totalContainersCount ++;
            }
        }
        return refrigeratedContainerCount;
    }
    
    public int countLiquidContainers() {
        for (Container container : containers) {
            if (container instanceof LiquidContainer) {
                liquidContainerCount++;
                totalContainersCount ++;
            }
        }
        return liquidContainerCount;
    }

    public int countBasicContainers() {
        for (Container container : containers) {
            if (container instanceof BasicContainer) {
                basicContainerCount++;
                totalContainersCount ++;
            }
        }
        return basicContainerCount;
    }

    @Override
    public boolean unLoad(Container cont) {
        if (!containers.contains(cont)) {
            // Container is not present in the ship
            System.out.println("Container not found on the ship");
            return false; // Unloading not possible
        }
    
        // Remove the container from the ship
        containers.remove(cont);
        decrementContainerCounters(cont);
        return true; // Container unloaded successfully
    }

    public void unloadAllContainers() {
        for (Container container : containers) {
            currentPort.unloadContainer(container);
        }
        containers.clear();
        heavyContainerCount = 0;
        liquidContainerCount = 0;
	    refrigeratedContainerCount = 0;
        basicContainerCount = 0;
        totalContainersCount = 0;
        System.out.println("All containers successfully unloaded.");
    }

    
    private void decrementContainerCounters(Container cont) {
        if (cont instanceof BasicContainer ) {
            // Decrement the counter for basic containers
            basicContainerCount--;
        } else if (cont instanceof RefrigeratedContainer) {
            // Decrement the counter for refrigerated containers
            refrigeratedContainerCount--;
        } else if (cont instanceof LiquidContainer) {
            // Decrement the counter for liquid containers
            liquidContainerCount--;
        } else {
            // Decrement the counter for heavy containers
            heavyContainerCount--;
        }
        totalContainersCount--;
    }

    public int getTotalWeight() {
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

    public int getMaxNumberOfBasicContainers() {
        return maxNumberOfBasicContainers;
    }

    public void setMaxNumberOfBasicContainers(int maxNumberOfBasicContainers) {
        this.maxNumberOfBasicContainers = maxNumberOfBasicContainers;
    }

    public double getFuelConsumptionPerKM() {
        return fuelConsumptionPerKM;
    }

    public void setFuelConsumptionPerKM(double fuelConsumptionPerKM) {
        this.fuelConsumptionPerKM = fuelConsumptionPerKM;
    }

    public double getfuelTankCapacity() {
        return fuelTankCapacity;
    }

    public void setfuelTankCapacity(double fuelTankCapacity) {
        this.fuelTankCapacity = fuelTankCapacity;
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
    
    private void incrementContainerCounters(Container cont) {
        if (cont instanceof BasicContainer) {
            // Increment the counter for basic containers
            basicContainerCount++;
        } else if (cont instanceof RefrigeratedContainer) {
            // Increment the counter for refrigerated containers
            refrigeratedContainerCount++;
        } else if (cont instanceof LiquidContainer) {
            // Increment the counter for liquid containers
            liquidContainerCount++;
        } else {
            // Increment the counter for heavy containers
            heavyContainerCount++;
        }
        totalContainersCount ++;
    }
}