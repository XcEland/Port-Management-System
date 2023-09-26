import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    int containerIDCount = 0;
    int shipIDCount = 0;
    static int portIDCount = 0;

    private static ArrayList<Container> containers = new ArrayList<>();
    private static ArrayList<Ship> ships = new ArrayList<>();
    private static ArrayList<Port> ports = new ArrayList<>();

    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("Ship and Port Management System");
            System.out.println("--------------------------------");
            System.out.println("Menu");
            System.out.println("--------------------------------");
            System.out.println("1. Create a port");
            System.out.println("2. Create a container");
            System.out.println("3. Create a ship");
            System.out.println("4. Load a container to a ship");
            System.out.println("5. Unload a container from a ship");
            System.out.println("6. Sail ship to another port");
            System.out.println("7. Refuel Ship");
            System.out.println("8. Exit");
            System.out.println("--------------------------------");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createPort();
                    
                    break;
                case 2:
                    createContainer();
                    break;
                case 3:
                    createShip();
                    break;
                case 4:
                    loadContainer();
                    break;
                case 5:
                    unloadContainer();
                    break;
                case 6:
                    sailShip();
                    break;
                case 7:
                    reFuelShip();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }

            System.out.println();
        } while (choice != 8);
    }

    private static void createPort() {
        System.out.print("Enter the x coordinate of the port: ");
        double xCoordinate = scanner.nextDouble();

        System.out.print("Enter the y coordinate of the port: ");
        double yCoordinate = scanner.nextDouble();

        System.out.print("Enter port number: ");
        int portID = scanner.nextInt();
        Port port = new Port(portID, xCoordinate, yCoordinate);

        ports.add(port);
        portIDCount++;

        System.out.println("Port created successfully.");

    }

    private static void createContainer() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the port where the container is currently located:");
        int portId = scanner.nextInt();

        Port port = findPortById(ports, portId);
        if (port == null) {
            System.out.println("Port not found. Please enter the port into your database");
            return;
        }

        System.out.print("Enter the container ID:");
        int id = scanner.nextInt();

        boolean isDuplicatedId =containers.stream()
                .anyMatch(container->container.getID()==id);
            
        if (isDuplicatedId){
            System.out.println("Container with the same ID already exists.");
            return;
        }

        System.out.print("Enter the container weight:");
        int weight = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        String type = "";
        if (weight >= 5000) {
            System.out.print("Is the container refrigerated or liquid? (R/L): ");
            type = scanner.nextLine();
        }

        Container container = new BasicContainer(id, weight, portId);
        if (type.equals("R")) {
            container = new RefrigeratedContainer(id, weight, portId);
        } else if (type.equals("L")) {
            container = new LiquidContainer(id, weight, portId);
        }

        containers.add(container);

        System.out.println("Container created successfully.");

    }

    private static void createShip() {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the ship ID:");
            int id = scanner.nextInt();

            boolean isDuplicatedId =ships.stream()
                    .anyMatch(ship->ship.getID()==id);
            
            if (isDuplicatedId){
                System.out.print("Ship with the same ID already exists.");
                return;
            }

            System.out.println("Enter the port where the ship is currently located:");
            int portId = scanner.nextInt();

            Port port = findPortById(ports, portId);
            if (port == null) {
                System.out.println("Port not found.Please enter the port into your database.");
                return;
            }

            // Prompt the user for input until a valid nonzero value is entered
            int totalWeightCapacity;
            do {
                System.out.print("Enter the ship's total weight capacity greater than 0: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input! Please enter an integer value: ");
                    scanner.nextLine(); // Clear the invalid input from the scanner
                }
                totalWeightCapacity = scanner.nextInt();
                scanner.nextLine(); // Clear the newline character from the scanner
                if (totalWeightCapacity <= 0) {
                    System.out.println("Invalid input! The number must be greater than 0.");
                }
            } while (totalWeightCapacity <= 0);
            
            System.out.print("Enter the ship's maximum number of all containers:");
            int maxNumberOfAllContainers = scanner.nextInt();

            System.out.print("Enter the ship's maximum number of heavy containers:");
            int maxNumberOfHeavyContainers = scanner.nextInt();

            System.out.print("Enter the ship's maximum number of refrigerated containers:");
            int maxNumberOfRefrigeratedContainers = scanner.nextInt();

            System.out.print("Enter the ship's maximum number of liquid containers:");
            int maxNumberOfLiquidContainers = scanner.nextInt();

            double fuelTankCapacity;
            do {
                System.out.print("Enter the ship's fuel tank capacity greater than 0: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input! Please enter a value: ");
                    scanner.nextLine(); // Clear the invalid input from the scanner
                }
                fuelTankCapacity = scanner.nextDouble();
                scanner.nextLine(); // Clear the newline character from the scanner
                if (fuelTankCapacity <= 0) {
                    System.out.println("Invalid input! The number must be greater than 0.");
                }
            } while (fuelTankCapacity <= 0);

            System.out.print("Enter the ship's fuel consumption per kilometer:");
            double fuelConsumptionPerKM = scanner.nextDouble();

            Ship ship = new Ship(id, port, totalWeightCapacity, maxNumberOfAllContainers, maxNumberOfHeavyContainers, maxNumberOfRefrigeratedContainers, maxNumberOfLiquidContainers, fuelConsumptionPerKM, fuelTankCapacity);
            ships.add(ship);


        System.out.println("Ship created successfully.");
    }

    private static void loadContainer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ship ID:");
        int shipId = scanner.nextInt();
        Ship ship = findShipById(ships, shipId);
        if (ship == null) {
            System.out.print("Ship not found.");
            return;
        }

        System.out.print("Enter container ID:");
        int containerId = scanner.nextInt();
        Container container = findContainerById(containers, containerId);
        if (container == null) {
            System.out.println("Container not found.");
            return;
        }

        if (!ship.load(container)) {
            System.out.println("The ship cannot load the container.");
            return;
        }


        System.out.println("Container loaded successfully.");
        loadingStatus(ship);
    }

    private static void unloadContainer() {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter ship ID:");
            int shipId = scanner.nextInt();
            Ship ship = findShipById(ships, shipId);
            if (ship == null) {
                System.out.println("Ship not found.");
                return;
            }

            //display all containers
            containersOnBoard(ship);

            System.out.print("Enter the container ID:");
            int containerId = scanner.nextInt();
            Container container = ship.findContainerById(containerId);
            if (container == null) {
                System.out.println("Container not found in the ship.");
                return;
            }
            ship.unLoad(container);
        System.out.println("Container unloaded successfully.");
    }

    private static void sailShip() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ship ID:");
        int shipId = scanner.nextInt();
        Ship ship = findShipById(ships, shipId);
        if (ship == null) {
            System.out.println("Ship not found.");
            return;
        }

        System.out.print("Enter destination port ID:");
        int destinationPortId = scanner.nextInt();
        Port destinationPort = findPortById(ports, destinationPortId);
        if (destinationPort == null) {
            System.out.println("Destination port not found.");
            return;
        }

        double fuelConsumed = calculateFuelConsumption(ship);

        System.out.println("--------------------------------");
        System.out.println("Ship Details on Current Port");
        System.out.println("--------------------------------");
        System.out.println("Ship ID: " + ship.getID());
        System.out.println("Current Port: " + ship.getCurrentPort());
        System.out.println("Destination Port: " + destinationPortId);
        System.out.println("Total Distance: " + ship.getID());
        System.out.println("Total Fuel required: " + ship.getID());
        System.out.println("Total: " + ship.getID());

        System.out.println("------ Container Summary --------");
        System.out.println("Total Ship Weight capacity: " + ship.getTotalWeightCapacity());
        System.out.println("Total Containers Weight capacity: " + ship.getTotalWeight());
        System.out.println("Total Containers: " + ship.getMaxNumberOfAllContainers()+
            ". Total Heavy Containers: "+ ship.countHeavyContainers() +
            ". Total Refrigerated Containers: " + ship.countRefrigeratedContainers()+
            ". Total Liquid Containers: " + ship.countLiquidContainers());

        // basic container??
        
        containersOnBoard(ship);

        boolean sailed = ship.sailTo(destinationPort);
        if (sailed) {
            System.out.println("Ship sailed to the destination port successfully.");
            System.out.println("--------------------------------");
            System.out.println("Ship Details on Destination Port");
            System.out.println("--------------------------------");

            // Display ship summary
            System.out.println("Ship ID: " + ship.getID());
            System.out.println("Remaining Fuel: " + ship.getFuel());
            System.out.println("Fuel consumed: " + fuelConsumed);

            System.out.println("----Containers on board----");
            for (Container container : ship.getCurrentContainers()) {
                System.out.println("Container ID: " + container.getID() + ". Container Weight: " + container.getWeight());
                
            }

            ship.unloadAllContainers();
        }else{
            System.out.println("Failed to sail to the destination port.");
            return;
        }
    }

    private static void reFuelShip() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter ship ID:");
            int shipId = scanner.nextInt();

            System.out.print("Enter amount of fuel to refuel:");
            double amountOfFuel = scanner.nextDouble();


            Ship ship = findShipById(ships, shipId);
            if (ship == null) {
                System.out.println("Ship not found.");
                return;
            }

            ship.reFuel(amountOfFuel);


        System.out.println("Ship refueled successfully.");
    }


    public static Ship findShipById(ArrayList<Ship> ships, int shipId) {
        for (Ship ship : ships) {
            if (ship.getID() == shipId) {
                return ship;
            }
        }

        return null;
    }

    public static Container findContainerById(ArrayList<Container> containers, int containerId) {
        for (Container container : containers) {
            if (container.getID() == containerId) {
                return container;
            }
        }

        return null;
    }

    public static Port findPortById(ArrayList<Port> ports, int portId) {
        for (Port port : ports) {
            if (port.getID() == portId) {
                return port;
            }
        }

        return null;
    }

    public static void containersOnBoard(Ship ship){
        System.out.println("------------------------");
        System.out.println("Containers on Board");
        System.out.println("------------------------");
        for (Container container : ship.getCurrentContainers()) {
            System.out.println("Container ID: " + container.getID() + ". Container Weight: " + container.getWeight());
        }
    }

    public static void loadingStatus(Ship ship){
        System.out.println("Loading Status: H:" + ship.heavyContainerCount + "/" + ship.getMaxNumberOfHeavyContainer() +
            ", R:" + ship.refrigeratedContainerCount+"/" + ship.getMaxNumberOfRefrigeratedContainers() +
            ", L:" + ship.liquidContainerCount+ "/" +ship.getMaxNumberOfLiquidContainers() +
            ", B:" + ship.basicContainerCount);
    }

    private static double calculateFuelConsumption(Ship ship){
        double fuelConsumed = 0.0;

        for (Container container : ship.getCurrentContainers()) {
            fuelConsumed += container.consumption();
        }
    
        return fuelConsumed;
    }
}