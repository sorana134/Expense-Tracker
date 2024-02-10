package phase2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static phase2.DataBaseOperation.*;

public class PasswordDB {
    //add a password to the database
    public void addPassword(String password, String username) {

        try (ConnectionManager connectionManager = getConnectionManager()) {
            connectionManager.connect();

            try (PreparedStatement ps = connectionManager.prepareStatement("INSERT INTO pass (user_name, password) VALUES (?, ?)")) {
                ps.setString(1, username); //set the username;
                ps.setString(2, password); //set the password;

                ps.executeUpdate();
            } catch (SQLException e) {
                connectionManager.rollback();
                System.err.println("[ERROR] add password: " + e.getMessage());
            }

            connectionManager.commit();
        } catch (SQLException e) {
            System.err.println("[ERROR] add password: " + e.getMessage());
        } finally {
            System.out.println("Expense added");
        }
    }
    //update the password in the database by the username
    public void updatePassword(String password, String username) {
        try (ConnectionManager connectionManager = getConnectionManager()) {
            connectionManager.connect();

            try (PreparedStatement ps = connectionManager.prepareStatement("UPDATE pass SET password = ? WHERE user_name = ?")) {
                ps.setString(1, password); //set the password;
                ps.setString(2, username); //set the username;
                ps.executeUpdate();
            } catch (SQLException e) {
                connectionManager.rollback();
                System.err.println("[ERROR] update password: " + e.getMessage());
            }

            connectionManager.commit();
        } catch (SQLException e) {
            System.err.println("[ERROR] update password: " + e.getMessage());
        } finally {
            System.out.println("Password updated");
        }
    }
    //get  the password from the database by the username
    public String getPassword(String username) {
        String password = "";

        try (ConnectionManager connectionManager = getConnectionManager()) {
            connectionManager.connect();

            try (PreparedStatement ps = connectionManager.prepareStatement("SELECT password FROM pass WHERE user_name = ?")) {
                ps.setString(1, username);

                // Use executeQuery() for SELECT statements
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Retrieve the password from the result set
                        password = rs.getString("password");
                    } else {
                        System.out.println("User not found");
                    }
                }
            } catch (SQLException e) {
                connectionManager.rollback();
                System.err.println("[ERROR] get password: " + e.getMessage());
            }

            connectionManager.commit();
        } catch (SQLException e) {
            System.err.println("[ERROR] get password: " + e.getMessage());
        } finally {
            System.out.println("Password retrieved");
        }

        System.out.println("Password: " + password + " username " + username + '\n');
        return password;
    }
    //get the usernames from the database
    public ObservableList<String> getUserNames() {
        ObservableList<String> userNames = FXCollections.observableArrayList();

        try (ConnectionManager connectionManager = getConnectionManager()) {
            connectionManager.connect();

            try (PreparedStatement ps = connectionManager.prepareStatement("SELECT user_name FROM pass");
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    userNames.add(rs.getString("user_name"));
                }

            } catch (SQLException e) {
                connectionManager.rollback();
                // Log the error instead of printing to the console
                // Logger.getLogger(YourClass.class.getName()).log(Level.SEVERE, "Error fetching usernames", e);
                System.err.println("[ERROR] get usernames: " + e.getMessage());
            }

            connectionManager.commit();
        } catch (SQLException e) {
            // Log the error instead of printing to the console
            // Logger.getLogger(YourClass.class.getName()).log(Level.SEVERE, "Error connecting to database", e);
            System.err.println("[ERROR] get usernames: " + e.getMessage());
        } finally {
            // You might not need this print statement, especially in a production environment
            System.out.println("Usernames retrieved");
        }

        return userNames;
    }


}
