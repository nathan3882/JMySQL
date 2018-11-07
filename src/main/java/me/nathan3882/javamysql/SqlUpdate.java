package main.java.me.nathan3882.javamysql;

import java.sql.*;

public class SqlUpdate {

    private final Connection connection;

    public SqlUpdate(SqlConnection sqlConnection) {
        this.connection = sqlConnection.getConnection();
    }

    /**
     * used for insert, delete and update
     * executeUpdate("INSERT INTO table (UserName) VALUES ('User533252')");
     *
     * @return success or not
     */
    public boolean executeUpdate(String sql, SqlConnection.SqlTableName name) {
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(true);
            preparedStatement = connection.prepareStatement(
                    sql.replace("{table}", name.toString()),
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            close(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
