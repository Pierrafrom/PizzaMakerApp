package com.pizzaMakerApp.model;

/**
 * An abstract class representing a general concept of food in the pizza maker application.
 * This class provides a common structure for various types of food items, including pizzas.
 */
public abstract class Food {
    // Unique identifier for the food item.
    protected int id;

    // A string representing the ingredients of the food item.
    protected String ingredients;

    // Name of the food item.
    protected String name;

    // Price of the food item.
    protected float price;

    /**
     * Gets the ID of the food item.
     *
     * @return The ID of the food item.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the ingredients of the food item.
     *
     * @return A string representing the ingredients of the food item.
     */
    public String getIngredients() {
        return ingredients;
    }

    /**
     * Gets the name of the food item.
     *
     * @return The name of the food item.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the food item.
     *
     * @return The price of the food item.
     */
    public float getPrice() {
        return price;
    }

    /**
     * Sets the ID of the food item.
     *
     * @param id The unique identifier to be set for this food item.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the ingredients of the food item.
     *
     * @param ingredients A string representing the ingredients to be set for this food item.
     */
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Sets the name of the food item.
     *
     * @param name The name to be set for this food item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the price of the food item.
     *
     * @param price The price to be set for this food item.
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Provides a string representation of the food item, including its name, ingredients, and price.
     *
     * @return A string representation of the food item.
     */
    @Override
    public String toString() {
        return name + '\n' + ingredients + '\n' + price + '€';
    }
}
