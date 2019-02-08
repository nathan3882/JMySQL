package me.nathan3882.javamysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {

    private static long latestOpeningMilis;
    private JavaMySQL javaMySQL;

    private String host = "51.77.194.49";
    private String databaseName = "javaMySQL";
    private int port = 3307;
    private String username = "nathan";
    private String password = "password";
    private Connection connection;

    public SqlConnection(JavaMySQL JavaMySQL) {
        this.javaMySQL = JavaMySQL;
        openConnection();
    }

    public SqlConnection(JavaMySQL JavaMySQL, String host, int port, String databaseName, String username, String password) {
        this.javaMySQL = JavaMySQL;
        this.host = host;
        this.databaseName = databaseName;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public static boolean isClosed(Connection connection) {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * executeQuery("SELECT * FROM users WHERE databaseName = 'Nathan'");
     *
     * @return success or not
     */
    public void openConnection() {
        if (!connectionEstablished(false)) {
            try {
                establishConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Connection establishConnection() {
        if (connection != null) close(connection);
        Connection ans;
        try {
            ans = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?useSSL=false&allowMultiQueries=true", username, password);
            latestOpeningMilis = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        this.connection = ans;
        return ans;
    }

    public JavaMySQL getJavaMySQL() {
        return javaMySQL;
    }

    private boolean hasConnection() {
        return this.connection != null;
    }

    public boolean connectionEstablished(boolean openIfFalse) {
        if (!hasConnection() && openIfFalse) {
            establishConnection();
        } else if (hasConnection() && isClosed(connection) && openIfFalse) {
            establishConnection();
        }
        return !isClosed(connection);
    }

    public Connection getConnection() {
        return connection;
    }

    private void close(AutoCloseable resource) {
        if (resource == null) return;
        try {
            resource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isClosed() {
        try {
            return this.connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isOpen() {
        return !isClosed();
    }

    public void closeConnection() {
        close(getConnection());
    }

    public interface SqlTableName {
        String TABLE_ONE = "one";
        String TABLE_TWO = "two";
    }
}
