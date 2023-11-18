package model;

import java.util.Queue;
import java.util.LinkedList;

/**
 * The <code>Kitchen</code> class represents a kitchen in a pizzeria. It manages a queue of pizza orders
 * and a team of PizzaMakers responsible for preparing the orders.
 *
 * <p>The kitchen initializes with a team of PizzaMakers and a set of initial orders. Orders are processed
 * in a first-come-first-serve manner.</p>
 *
 * <p><b>Note:</b> This class uses a synchronized method for retrieving the next order to ensure thread safety
 * when multiple PizzaMakers are working concurrently.</p>
 *
 * @author Samuel BOIX-SEGURA
 * @version 1.0
 */
public class Kitchen {
    private Queue<Integer> orderQueue = new LinkedList<>();
    private PizzaMaker[] team;

    /**
     * Constructs a new Kitchen with the given team of PizzaMakers and initial orders.
     *
     * @param team   The team of PizzaMakers working in the kitchen.
     * @param orders An array of initial orders to be processed by the kitchen.
     */
    public Kitchen(PizzaMaker[] team, int[] orders) {
        this.team = team;
        for (int order : orders) {
            orderQueue.add(order);
        }
    }

    /**
     * Sets the team of PizzaMakers in the kitchen.
     *
     * @param team The new team of PizzaMakers.
     */
    public void setTeam(PizzaMaker[] team) {
        this.team = team;
        for (PizzaMaker maker : team) {
            maker.setKitchen(this); // Set the kitchen reference for each PizzaMaker
        }
    }

    /**
     * Retrieves and removes the next order from the queue.
     *
     * @return The next order to be processed, or null if the queue is empty.
     */
    public synchronized Integer getNextOrder() {
        return orderQueue.poll();
    }

    /**
     * Checks if there are more orders in the queue.
     *
     * @return true if there are more orders, false otherwise.
     */
    public boolean hasMoreOrders() {
        return !orderQueue.isEmpty();
    }
}
