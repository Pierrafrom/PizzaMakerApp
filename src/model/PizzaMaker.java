package model;

public class PizzaMaker extends Thread {
    private Level level;
    private int idPM;
    private String nomPM;
    private String prenomPM;
    private boolean available = true;
    private Kitchen kitchen; // Reference to the Kitchen

    // Constructor
    public PizzaMaker(int idPM, String nomPM, String prenomPM, Level level) {
        this.idPM = idPM;
        this.nomPM = nomPM;
        this.prenomPM = prenomPM;
        this.level = level;
    }

    // Set the kitchen reference
    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    // Thread's run method
    public void run() {
        while (kitchen.hasMoreOrders()) {
            if (available) {
                Integer order = kitchen.getNextOrder();
                if (order != null) {
                    prepareOrder(order);
                }
            }
            // Optionally, you can add a sleep here to simulate time between checking for orders
        }
    }

    // Method to handle order preparation
    public synchronized void prepareOrder(int order) {
        try {
            setAvailable(false);
            System.out.println("Pizza Maker " + idPM + " takes order " + order);
            // Simulate order preparation time based on the pizza maker's level
            Thread.sleep(level.preparationTime * 1000);
            System.out.println("Pizza Maker " + idPM + " completed order " + order);
            setAvailable(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Setters and Getters for available
    public synchronized void setAvailable(boolean available) {
        this.available = available;
    }

    public synchronized boolean isAvailable() {
        return available;
    }

    // Enum for different levels of pizza makers
    public enum Level {
        BEGINNER(12), INTERMEDIATE(10), PRO(7);

        private final int preparationTime;

        Level(int preparationTime) {
            this.preparationTime = preparationTime;
        }

        public int getPreparationTime() {
            return preparationTime;
        }
    }

    // Additional getters and setters for other fields, if necessary
    // ...
}
