package org.example;

import java.util.List;

public interface InterfaceCRUD<T> {

    public abstract void create(Object entity);
    public abstract T readById(Long id);
    public abstract void update(T entity);
    public abstract void deleteById(Long id);
    public abstract List<T> readall();
}
