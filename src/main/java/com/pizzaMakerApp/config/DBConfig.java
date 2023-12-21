package com.pizzaMakerApp.config;

public interface DBConfig {
    // Database configuration

    String URL = "jdbc:mariadb://pierrafrom.ddns.net:41632/PIZZERIA";
    String USER = "pierre";
    String PASSWORD = "0KT@tX55LK3.hMLQ";
    /*
    String URL = "jdbc:mariadb://localhost/PIZZERIA";
    String USER = "root";
    String PASSWORD = "root";
    */
    String DRIVER = "org.mariadb.jdbc.Driver";
}