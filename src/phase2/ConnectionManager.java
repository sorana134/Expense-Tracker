package phase2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager implements AutoCloseable {
    private static final String JDBC_URL = "jdbc:sqlite:identifier.sqlite";
    private Connection conn;

    public ConnectionManager() {
        // Load the SQLite JDBC driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws SQLException {
        conn = DriverManager.getConnection(JDBC_URL);
        System.out.println("Connected to the database");
    }

    public void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commit() throws SQLException {
        if (conn != null) {
            conn.commit();
        }
    }

    public void rollback() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (conn != null) {
            return conn.prepareStatement(sql);
        }
        return null;
    }

    public Statement createStatement() throws SQLException {
        if (conn != null) {
            return conn.createStatement();
        }
        return null;
    }

    @Override
    public void close() {
        disconnect();
    }

    public void setAutoCommit(boolean b) {
        try {
            if (conn != null) {
                conn.setAutoCommit(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
