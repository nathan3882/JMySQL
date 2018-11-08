package me.nathan3882.javamysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlQuery {

    private final Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet = null;

    public SqlQuery(SqlConnection cction) {
        this.connection = cction.getConnection();
    }

    public SqlQuery executeQuery(String sql, String name) {
        if (resultSet != null) {
            close();
        }
        try {
            this.preparedStatement = connection.prepareStatement(
                    sql.replace("{table}", name));

            this.resultSet = preparedStatement.executeQuery();
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

    public boolean next(boolean close) {
        boolean next = false;
        try {
            next = resultSet.next();
            if (close) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return next;
    }

    public String getString(String columnName) {
        try {
            return resultSet.getString(columnName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getString(int columnIndex) {
        try {
            return resultSet.getString(columnIndex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getInt(int i) {
        try {
            return resultSet.getInt(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}