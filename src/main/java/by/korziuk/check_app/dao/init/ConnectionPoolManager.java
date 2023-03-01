package by.korziuk.check_app.dao.init;

import org.postgresql.ds.PGConnectionPoolDataSource;

public final class ConnectionPoolManager {

    private static final String URL_KEY = "datasource.url";
    private static final String USERNAME_KEY = "datasource.username";
    private static final String PASSWORD_KEY = "datasource.password";
    private static final String DRIVER_KEY = "datasource.driver-class-name";

    static {
        loadDriver();
    }

    private ConnectionPoolManager() {
    }

    public static PGConnectionPoolDataSource open() {
        try {
            PGConnectionPoolDataSource connectionPool =
                    new PGConnectionPoolDataSource();

            connectionPool.setURL(PropertiesUtil.get(URL_KEY));
            connectionPool.setUser(PropertiesUtil.get(USERNAME_KEY));
            connectionPool.setPassword(PropertiesUtil.get(PASSWORD_KEY));

            System.out.println("Connected to the database!");
            return connectionPool;
        } catch (Exception e) {
            System.out.println("Failed to make connection!");
            throw new RuntimeException(e);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName(PropertiesUtil.get(DRIVER_KEY));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
