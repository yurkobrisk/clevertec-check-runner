package by.korziuk.check_app.dao;

import by.korziuk.check_app.dao.init.ConnectionPoolManager;
import by.korziuk.check_app.dao.init.ScriptUtils;
import org.postgresql.ds.PGConnectionPoolDataSource;

import java.sql.*;

public class DaoConfiguration {

    private static final PGConnectionPoolDataSource CONNECTION_POOL_DATA_SOURCE
            = ConnectionPoolManager.open();

    /**
     * Method create connection and DB. Then create new connection,
     * try to drop tables if they are exist and create new tables
     */
    public static void load () {

        //create database if it isn`t present
        try (Connection connectionData = CONNECTION_POOL_DATA_SOURCE.getConnection();
             Statement statement = connectionData
                     .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet resultSet = statement
                     .executeQuery("SELECT 1 AS result FROM pg_database WHERE datname='runnerdb'")) {

            resultSet.last();
            int row = resultSet.getRow();

            if (row == 0) {
                ScriptUtils.executeSqlScript(connectionData, "create_db.sql");
                System.out.println("Database runnerdb was created.");
            }

            CONNECTION_POOL_DATA_SOURCE.setDatabaseName("runnerdb");

        } catch (SQLException e) {
            System.out.println("Can`t create or set current DB!");
        }

        //drop and create tables if it needs
        try (Connection connectionSchema = CONNECTION_POOL_DATA_SOURCE.getConnection();
             Statement statement = connectionSchema
                .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            DatabaseMetaData metaData = connectionSchema.getMetaData();
            ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});

            tables.last();
            int row = tables.getRow();
            System.out.println("TABLES = " + row);

            if (row != 0) {
                statement.executeUpdate("DROP TABLE IF EXISTS product, discount_card");
                System.out.println("Tables dropped from DB.");
            }

            ScriptUtils.executeSqlScript(connectionSchema, "create_tables.sql");
            System.out.println("Tables created in DB.");

        } catch (SQLException e) {
            System.out.println("Can`t create table for current DB!");
            throw new RuntimeException(e);
        }
    }
}
