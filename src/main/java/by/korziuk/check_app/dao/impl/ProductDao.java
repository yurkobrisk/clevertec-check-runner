package by.korziuk.check_app.dao.impl;

import by.korziuk.check_app.dao.DAO;
import by.korziuk.check_app.model.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductDao implements DAO<Product, Integer> {

    private static ProductDao productDao;
    private static Connection connection;

    public static ProductDao getInstance(Connection connection) {
        if (productDao == null) {
            productDao = new ProductDao();
        }
        ProductDao.connection = connection;
        return productDao;
    }

    private ProductDao() {
    }

    @Override
    public Product getEntity(Integer id) {
        return null;
    }

    @Override
    public Integer addEntity(Product product) {
        return 0;
    }

    @Override
    public ArrayList<Product> getEntities() {
        return null;
    }
}
