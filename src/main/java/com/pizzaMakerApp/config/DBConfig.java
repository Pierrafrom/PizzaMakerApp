package com.pizzaMakerApp.config;

/**
 * Interface representing the configuration for connecting to the Pizza Maker application database.
 * Defines constants for the database URL, user credentials, and driver class.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * String url = DBConfig.URL;
 * String user = DBConfig.USER;
 * String password = DBConfig.PASSWORD;
 * String driver = DBConfig.DRIVER;
 * }
 * </pre>
 * </p>
 * <p>
 * The interface includes the following constants:
 * <ul>
 *   <li>{@link #URL}: The URL for connecting to the database.</li>
 *   <li>{@link #USER}: The username for authenticating with the database.</li>
 *   <li>{@link #PASSWORD}: The password for authenticating with the database.</li>
 *   <li>{@link #DRIVER}: The JDBC driver class for the database.</li>
 * </ul>
 * </p>
 * @see java.sql.Connection
 */
public interface DBConfig {
    /**
     * The URL for connecting to the database.
     */
    String URL = "jdbc:mariadb://pierrafrom.ddns.net:41632/PIZZERIA";

    /**
     * The username for authenticating with the database.
     */
    String USER = "pierre";

    /**
     * The password for authenticating with the database.
     */
    String PASSWORD = "0KT@tX55LK3.hMLQ";

    /**
     * The JDBC driver class for the database.
     */
    String DRIVER = "org.mariadb.jdbc.Driver";
}
