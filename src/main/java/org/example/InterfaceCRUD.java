package org.example;

import java.util.List;

public interface InterfaceCRUD<T> {

    void create(Object entity);
    T readById(Long id);
    void update(T entity);
    void deleteById(Long id);
    List<T> readall();
}
