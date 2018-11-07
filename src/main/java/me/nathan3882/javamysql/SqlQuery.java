package main.java.me.nathan3882.javamysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlQuery {

    private final Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public SqlQuery(SqlConnection cction) {
        this.connection = cction.getConnection();
    }

    public SqlQuery executeQuery(String sql, String tableName) {
        try {
            this.preparedStatement = connection.prepareStatement(
                    sql.replace("{table}", tableName));
            this.resultSet = preparedStatement.executeQuery();
            this.preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ResultSet getResultSet() {
        return this.resultSet;
    }

    public ResultSet getResultSet(String sql, String tableName) {
        executeQuery(sql, tableName);
        return getResultSet();
    }
}