package com.example.javaweblabs.dao.abstraction;

import java.util.List;
import java.util.Optional;

public interface CrudDao<K, T> {
    boolean create(T entity);

    Optional<T> findById(K id);

    List<T> findAll();

    boolean update(T entity);

    boolean delete(K key);
}
