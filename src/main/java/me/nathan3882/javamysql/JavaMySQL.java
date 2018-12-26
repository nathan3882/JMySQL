package me.nathan3882.javamysql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JavaMySQL implements SqlConnection.SqlTableName {

    public static void main(String[] args) throws SQLException {
        SqlConnection connection = new SqlConnection();

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



        SqlConnection anotherValidConnection = new SqlConnection("localhost", 3306, "anotherDatabase", "userTwo", "password");
        SqlQuery anotherQuery = new SqlQuery(anotherValidConnection);

        ResultSet secondResultSet = anotherQuery.getResultSet("SELECT aString IN {table} WHERE aNumber = 66", TABLE_ONE);
        if (secondResultSet.next()) {
            String aString = anotherQuery.getString("column");
        }
        anotherQuery.close();

        connection.closeConnection();
    }
}
