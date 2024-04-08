package expeditors.backend.dao;

import java.util.List;

public interface BaseDAO<T> {

    boolean update(T updateObject);

    boolean delete(T student);

    T insert(T newObject);

    T findById(int id);

    List<T> findAll();

}