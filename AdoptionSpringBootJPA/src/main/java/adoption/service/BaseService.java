package adoption.service;

import java.util.List;

public interface BaseService<T> {

    T add(T newObject);

    boolean delete(int id);

    boolean update(T updateObject);

    T findById(int id);

    List<T> findAll();

}
