package me.nathan3882.javamysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {

    private boolean open;
    private String host = "localhost";
    private String databaseName = "userdata";
    private int port = 3306;
    private String username = "root";
    private String password = "";
    private Connection connection;

    public SqlConnection() {
        this.connection = newCon();
    }

    public SqlConnection(String host, int port, String databaseName, String username, String password) {
        this.host = host;
        this.databaseName = databaseName;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    /**
     * executeQuery("SELECT * FROM users WHERE databaseName = 'Nathan'");
     *
     * @return success or not
     */
    public void openConnection() {
        if (!connectionEstablished()) {
            newCon();
        }
        if (connectionEstablished()) {
            try {
                if (open) return;
                Class.forName("com.mysql.jdbc.Driver");
                this.connection = newCon();
                open = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Connection newCon() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?useSSL=false&allowMultiQueries=true", username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeConnection() {
        if (open) {
            close(connection);
            open = false;
        }
    }

    public boolean connectionEstablished() {
        if (this.connection == null) openConnection();
        return this.connection != null;
    }

    public Connection getConnection() {
        return connection;
    }

    private void close(AutoCloseable resource) {
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

    public interface SqlTableName {
        String TABLE_ONE = "tableOneName";
    }
}
