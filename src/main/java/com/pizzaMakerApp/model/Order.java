package com.pizzaMakerApp.model;

import java.util.Date;
import java.util.ArrayList;

public class Order {
    int id;
    ArrayList<Food> items;
    ArrayList<Integer> quantity;
    Date dateTime;
    String nameClient;

    public Order(int orderId){
        // TODO: appeler la base de données pour recuperer chaque infos de la commande
        // appeler chaque constructeur de food et le mettre dans la liste et mettre ka quantité dans la iste quantité
    }
}
