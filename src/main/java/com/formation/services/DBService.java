package com.formation.services;

import java.sql.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DBService {

    // Singleton
    private static DBService instance;

    private static String ipAdress = "192.168.1.12";
    private Connection connection;
    
    
    private static final Logger logger = LogManager.getLogger("DBService");
   
    public static void configure(String ip){        
    	ipAdress= ip;
    	logger.info("l'adresse de la bdd est fixé à " + ipAdress);
    	}

    public static DBService getInstance() {
        if (instance == null) {
            instance = new DBService();
        }
        return instance;
    }

    private DBService() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:mysql://192.168.1.12/ecommerce?user=root&password=formation&useSSL=false");
        } catch (ClassNotFoundException e) {
            System.err.println("Impossible de trouver le driver jdbc : " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Impossible de se connecter à la base : " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    public PreparedStatement prepareStatement(String requete) throws SQLException {
        return connection.prepareStatement(requete);
    }

    public ResultSet executeSelect(String requete) throws SQLException {
        return createStatement().executeQuery(requete);
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO
            }
        }
    }
}
