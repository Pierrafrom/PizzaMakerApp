package com.pizzaMakerApp.main;

import com.pizzaMakerApp.model.MPizzaMakerApp;
import com.pizzaMakerApp.view.VPizzaMakerApp;

public class Main {
    public static void main(String[] args) {
        MPizzaMakerApp model = new MPizzaMakerApp();
        VPizzaMakerApp view = new VPizzaMakerApp();
        CPizzaMakerApp controller = new CPizzaMakerApp(model, view);
        controller.startApplication();
    }

}