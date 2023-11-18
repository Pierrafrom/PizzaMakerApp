package model;

/**
 * The <code>PizzaMaker</code> class represents a pizza maker in a pizzeria. Each pizza maker is a
 * separate thread capable of preparing pizza orders based on their assigned level.
 *
 * <p>The class provides methods for setting the kitchen reference, checking and updating the availability
 * of the pizza maker, and handling the preparation of pizza orders.</p>
 *
 * <p><b>Note:</b> This class extends the Thread class, and each instance runs in a separate thread to
 * simulate concurrent pizza preparation.</p>
 *
 * @author Samuel BOIX-SEGURA
 * @version 1.0
 */
public class PizzaMaker extends Thread {
    private Level level;
    private int idPM;
    private String namePM;
    private String firstnamePM;
    private boolean available = true;
    private Kitchen kitchen; // Reference to the Kitchen

    /**
     * Constructs a new PizzaMaker with the specified parameters.
     *
     * @param idPM           The unique identifier for the pizza maker.
     * @param namePM         The last name of the pizza maker.
     * @param firstnamePM    The first name of the pizza maker.
     * @param level          The skill level of the pizza maker.
     */
    public PizzaMaker(int idPM, String namePM, String firstnamePM, Level level) {
        this.idPM = idPM;
        this.namePM = namePM;
        this.firstnamePM = firstnamePM;
        this.level = level;
    }

    /**
     * Sets the reference to the Kitchen where the pizza maker works.
     *
     * @param kitchen The Kitchen instance to set as the reference.
     */
    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    /**
     * The run method of the thread. It continuously checks for new orders in the kitchen
     * and prepares them if the pizza maker is available.
     */
    public void run() {
        while (kitchen.hasMoreOrders()) {
            if (available) {
                Integer order = kitchen.getNextOrder();
                if (order != null) {
                    prepareOrder(order);
                }
            }
            // Optionally we can add a sleep here to simulate time between checking for orders
        }
    }

    /**
     * Handles the preparation of a pizza order. Simulates the time it takes to prepare the order
     * based on the pizza maker's skill level.
     *
     * @param order The order number to be prepared.
     */
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

    /**
     * Sets the availability status of the pizza maker.
     *
     * @param available true if the pizza maker is available, false otherwise.
     */
    public synchronized void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Checks if the pizza maker is available to take a new order.
     *
     * @return true if the pizza maker is available, false otherwise.
     */
    public synchronized boolean isAvailable() {
        return available;
    }

    /**
     * Enum representing different skill levels of pizza makers, each with a corresponding
     * preparation time for pizza orders.
     */
    public enum Level {
        BEGINNER(120), INTERMEDIATE(110), PRO(100);

        private final int preparationTime;

        Level(int preparationTime) {
            this.preparationTime = preparationTime;
        }

        /**
         * Gets the preparation time required for the pizza maker's skill level.
         *
         * @return The preparation time in seconds.
         */
        public int getPreparationTime() {
            return preparationTime;
        }
    }
}
