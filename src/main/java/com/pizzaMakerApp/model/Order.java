package com.pizzaMakerApp.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class Order {
    int id;
    ArrayList<Food> items;
    ArrayList<Integer> quantity;
    Date dateTime;
    String nameClient;

    public Order(int orderId){

    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String preview(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM HH:mm");
        String strDate = formatter.format(dateTime);
        return id + " " + strDate + " " + nameClient;
    }
}
