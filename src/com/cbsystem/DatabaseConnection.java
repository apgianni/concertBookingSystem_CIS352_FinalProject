//Classpath: set CLASSPATH=.;/Users/gap/Desktop/foodShop

package com.cbsystem;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    String driver;
    String dbName;
    String connectionURL;

    String username;
    String password;

    //driver definitions
    public DatabaseConnection() {
        driver = "com.mysql.jdbc.Driver";
        connectionURL = "jdbc:mysql://localhost:3306/";
        dbName = "ConcertBookingSystem/Reservations";
        username = "root";
        password = "990315AG";
    }

    // gets a connection for the database.
    public Connection getConnection() throws Exception {

        Class.forName(driver);

        Connection connection = DriverManager.getConnection(connectionURL, username, password);

        return connection;
    }

    // test function
    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection();
        try {
            Connection conn = db.getConnection();
            System.out.println("Database successfully connected!");
            conn.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}