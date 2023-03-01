package by.korziuk.check_app.dao;

import java.util.ArrayList;

public interface DAO<Entity, Key> {

    Entity getEntity(Key key);
    Key addEntity(Entity entity);
    ArrayList<Entity> getEntities();
}
