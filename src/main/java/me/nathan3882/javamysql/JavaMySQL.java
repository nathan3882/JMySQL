package me.nathan3882.javamysql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JavaMySQL implements SqlConnection.SqlTableName {

    private static JavaMySQL instance = new JavaMySQL();
    private SqlConnection sql;

    public static void main(String[] args) throws SQLException {
        JavaMySQL instance = JavaMySQL.get();
                SqlConnection connection = new SqlConnection(instance);
        instance.sql = connection;

        connection.openConnection();

        /**
         * Note a query's ResultSet value can be assigned indirectly
         */
        SqlQuery query = new SqlQuery(connection);
        query.executeQuery("SELECT aNumber IN {table} WHERE aString = 'foobar'", TABLE_ONE);
        ResultSet firstResultSet = query.getResultSet();
        if (query.next(false)) {
            int aNumber = query.getInt(1);
        }
        query.close();



        SqlConnection anotherValidConnection = new SqlConnection(instance, "localhost", 3306, "anotherDatabase", "userTwo", "password");
        SqlQuery anotherQuery = new SqlQuery(anotherValidConnection);

        ResultSet secondResultSet = anotherQuery.getResultSet("SELECT aString IN {table} WHERE aNumber = 66", TABLE_ONE);
        if (secondResultSet.next()) {
            String aString = anotherQuery.getString(1);
        }
        anotherQuery.close();

        connection.closeConnection();
    }

    private static JavaMySQL get() {
        return instance;
    }

    public SqlConnection getSqlConnection() {
        return sql;
    }
}
