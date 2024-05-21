package adoption.dao;

import java.util.List;

public interface BaseDAO<T> {

    T insert(T newObject);

    boolean delete(int id);

    boolean update(T updateObject);

    T findById(int id);

    List<T> findAll();

}