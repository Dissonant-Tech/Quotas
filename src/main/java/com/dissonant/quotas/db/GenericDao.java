package com.dissonant.quotas.db;

import java.util.List;

public interface GenericDao<E, K> {
    void add(E entity);
    void remove(E entity);

    K update(E entity);

    E get(K key);
    List<E> list();
}
