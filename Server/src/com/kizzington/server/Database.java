package com.kizzington.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;


public class Database {

    private Connection c;
    private String dbName;

    public Database(String dbName) {
        this.dbName = dbName;
    }

    public void connect() throws SQLException{
        String url = "jdbc:sqlite:" + dbName;
        c = DriverManager.getConnection(url);

        System.out.println("Database connected: " + dbName);
    }

    public boolean userExists(String username) {
        try {
            String sql = "SELECT userID FROM users WHERE username = ?";

            PreparedStatement statement = c.prepareStatement(sql);
            statement.setString(1, username);

            ResultSet results = statement.executeQuery();

            if (results.next()) {
                return true;
            }
            return false;
        } catch(SQLException e){
            return true;
        }
    }

    public void addUser(String username, String password, int x, int y) throws SQLException{
        String sql = "INSERT INTO Users(username, password, x, y) VALUES(?,?,?,?)";

        PreparedStatement statement = c.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setInt(3, x);
        statement.setInt(4, y);

        statement.executeUpdate();
    }

    public void updateUser(EntityPlayer player) throws SQLException {
        String sql = "UPDATE users SET x = ?, y = ? WHERE username = ?";

        PreparedStatement statement = c.prepareStatement(sql);

        statement.setInt(1, player.getX());
        statement.setInt(2, player.getY());
        statement.setString(3, player.getUsername());

        statement.executeUpdate();
    }

    public EntityPlayer getPlayer(String username, String password, ServerConnection serverConnection) {
        try {
            String sql = "SELECT x, y FROM users WHERE username = ? AND password = ?";

            PreparedStatement statement = c.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet results = statement.executeQuery();

            if (results.next()) {
                EntityPlayer player = new EntityPlayer(results.getInt("x"), results.getInt("y"), username, serverConnection);
                return player;
            }
            return null;
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
