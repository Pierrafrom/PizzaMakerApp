package model;

import java.util.Queue;
import java.util.LinkedList;

public class Kitchen {
    private Queue<Integer> orderQueue = new LinkedList<>();
    private PizzaMaker[] team;

    public Kitchen(PizzaMaker[] team, int[] orders) {
        this.team = team;
        for (int order : orders) {
            orderQueue.add(order);
        }
    }

    // Method to set the team of PizzaMakers
    public void setTeam(PizzaMaker[] team) {
        this.team = team;
        for (PizzaMaker maker : team) {
            maker.setKitchen(this); // Set the kitchen reference for each PizzaMaker
        }
    }

    public synchronized Integer getNextOrder() {
        return orderQueue.poll(); // Retrieves and removes the head of the queue, or returns null if the queue is empty.
    }

    public boolean hasMoreOrders() {
        return !orderQueue.isEmpty();
    }

}
