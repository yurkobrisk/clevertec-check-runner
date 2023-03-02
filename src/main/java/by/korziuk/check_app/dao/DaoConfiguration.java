package by.korziuk.check_app.dao;

import by.korziuk.check_app.dao.init.ConnectionPoolManager;
import by.korziuk.check_app.dao.init.ScriptUtils;
import by.korziuk.check_app.data.Data;
import by.korziuk.check_app.model.Card;
import by.korziuk.check_app.model.Product;
import org.postgresql.ds.PGConnectionPoolDataSource;

import java.math.BigDecimal;
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
                statement.executeUpdate("DROP TABLE IF EXISTS products, discount_cards");
                System.out.println("Tables dropped from DB.");
            }

            ScriptUtils.executeSqlScript(connectionSchema, "create_tables.sql");
            System.out.println("Tables created in DB.");

        } catch (SQLException e) {
            System.out.println("Can`t create table for current DB!");
            throw new RuntimeException(e);
        }
    }

    public static void insertData(Data data) {

        String productSQL = "INSERT INTO products VALUES(?,?,?,?)";
        String cardSQL = "INSERT INTO discount_cards VALUES(?,?,?,?)";

        try (Connection connectionData = CONNECTION_POOL_DATA_SOURCE.getConnection();
            PreparedStatement statement =
                    connectionData.prepareStatement(productSQL);
            PreparedStatement statementCard =
                     connectionData.prepareStatement(cardSQL)) {

            int count = 0;

            for (Product product : data.getProductCollection()) {
                statement.setInt(1, product.getId());
                statement.setString(2, product.getName());
                statement.setString(3, product.getDescription());
                statement.setBigDecimal(4, product.getPrice());

                statement.addBatch();
                count++;
                // execute every 100 rows or less
                if (count % 100 == 0 || count == data.getProductCollection().size()) {
                    statement.executeBatch();
                }
            }

            count = 0;

            for (Card card : data.getCardCollection()) {
                statementCard.setInt(1, card.getId());
                statementCard.setString(2, card.getHolderName());
                statementCard.setString(3, card.getHolderLastName());
                statementCard.setInt(4, card.getDiscount());

                statementCard.addBatch();
                count++;
                // execute every 100 rows or less
                if (count % 100 == 0 || count == data.getCardCollection().size()) {
                    statementCard.executeBatch();
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
