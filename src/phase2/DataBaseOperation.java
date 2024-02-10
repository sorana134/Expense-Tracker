package phase2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import phase1.Expense;
import phase2.ConnectionManager;

import java.sql.*;

public class DataBaseOperation {
    private static final String JDBC_URL = "jdbc:sqlite:identifier.sqlite";

    public static ConnectionManager getConnectionManager() {
        return new ConnectionManager();
    }

    public void addExpense(Expense expense) {
        try (ConnectionManager connectionManager = getConnectionManager()) {
            connectionManager.connect();

            try (PreparedStatement ps = connectionManager.prepareStatement("INSERT INTO expense (date, expense_name, expense_value, budget) VALUES (?, ?, ?, ?)")) {
                ps.setDate(1, Date.valueOf(expense.getDate()));
                ps.setString(2, expense.getExpense());
                ps.setDouble(3, expense.getCost());
                ps.setDouble(4, expense.getBudget());
                ps.executeUpdate();
            } catch (SQLException e) {
                connectionManager.rollback();
                System.err.println("[ERROR] addExpense: " + e.getMessage());
            }

            connectionManager.commit();
        } catch (SQLException e) {
            System.err.println("[ERROR] addExpense: " + e.getMessage());
        } finally {
            System.out.println("Expense added");
        }
    }

    public void deleteExpense(int id) {
        try (ConnectionManager connectionManager = getConnectionManager()) {
            connectionManager.connect();
            connectionManager.setAutoCommit(false);

            try (PreparedStatement ps = connectionManager.prepareStatement("DELETE FROM expense WHERE id = ?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                connectionManager.rollback();
                System.err.println("[ERROR] deleteExpense: " + e.getMessage());
            }

            connectionManager.commit();
        } catch (SQLException e) {
            System.err.println("[ERROR] deleteExpense: " + e.getMessage());
        } finally {
            System.out.println("Expense deleted");
        }
    }

    public void updateExpense(int id, String expense, double cost) {
        try (ConnectionManager connectionManager = getConnectionManager()) {
            connectionManager.connect();
            connectionManager.setAutoCommit(false);

            try (PreparedStatement ps = connectionManager.prepareStatement("UPDATE expense SET expense_name = ?, expense_value = ? WHERE id = ?")) {
                ps.setString(1, expense);
                ps.setDouble(2, cost);
                ps.setInt(3, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                connectionManager.rollback();
                System.err.println("[ERROR] updateExpense: " + e.getMessage());
            }

            connectionManager.commit();
        } catch (SQLException e) {
            System.err.println("[ERROR] updateExpense: " + e.getMessage());
        } finally {
            System.out.println("Expense updated");
        }
    }

    public ObservableList<Expense> fetchExpensesFromDatabase() {
        ObservableList<Expense> expenses = FXCollections.observableArrayList();

        try (ConnectionManager connectionManager = getConnectionManager()) {
            connectionManager.connect();

            try (Statement statement = connectionManager.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT date, expense_name, expense_value, budget FROM expense")) {

                while (resultSet.next()) {
                    Date date = resultSet.getDate("date");
                    String expenseName = resultSet.getString("expense_name");
                    double cost = resultSet.getDouble("expense_value");
                    double budget = resultSet.getDouble("budget");
                    expenses.add(new Expense(date.toLocalDate(), expenseName, cost, budget));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses;
    }
}
