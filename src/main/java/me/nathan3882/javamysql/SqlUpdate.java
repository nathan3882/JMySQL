package me.nathan3882.javamysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlUpdate {

    private final Connection connection;
    private final JavaMySQL javaMySQL;

    public SqlUpdate(SqlConnection sqlConnection) {
        this.javaMySQL = sqlConnection.getJavaMySQL();
        sqlConnection.openConnection();
        this.connection = sqlConnection.getConnection();
        try {
            if (connection.isClosed()) sqlConnection.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * used for insert, delete and update
     * executeUpdate("INSERT INTO table (UserName) VALUES (5)");
     *
     * @return success or not
     */
    public boolean executeUpdate(String sql, String name) {
        if (javaMySQL.getSqlConnection().connectionEstablished(true)) {
            PreparedStatement preparedStatement;
            try {
                connection.setAutoCommit(true);
                preparedStatement = connection.prepareStatement(
                        sql.replace("{table}", name),
                        Statement.RETURN_GENERATED_KEYS);
                preparedStatement.executeUpdate();
                close(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else return false;
        return true;
    }

    private void close(AutoCloseable resource) {
        try {
            resource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
