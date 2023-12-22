package com.pizzaMakerApp.model;

public abstract class Food {
    protected int id;
    protected String ingredients;
    protected String name;
    protected float price;

    public String toString() {
        return name + '\n' + ingredients + '\n' + price + '€';
    }
}
