package me.nathan3882.javamysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;

public class SqlQuery {

    private SqlConnection cction;
    private JavaMySQL JavaMySQL;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet = null;

    public SqlQuery(SqlConnection cction) {
        this.JavaMySQL = cction.getJavaMySQL();
        this.cction = cction;
    }

    public ResultSet executeQuery(String sql, String name) {
        if (cction.connectionEstablished(true)) {
            if (resultSet != null) {
                close();
            }
            cction.openConnection();
            try {
                this.preparedStatement = cction.getConnection().prepareStatement(
                        sql.replace("{table}", name));
                this.resultSet = preparedStatement.executeQuery();
            } catch (SQLNonTransientConnectionException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return this.resultSet;
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
            if (close) cction.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return next;
    }

    public long getLong(int column) {
        try {
            return resultSet.getLong(column);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    public String getString(int column) {
        try {
            return resultSet.getString(column);
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