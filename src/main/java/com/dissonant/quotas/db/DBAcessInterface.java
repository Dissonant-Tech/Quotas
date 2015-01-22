package com.dissonant.quotas.db;

import java.util.List;

public interface DBAcessInterface<T> {
    void add(T t);
    void del(T t);

    T get(int id);
    List<T> getAll();

    int update(T t);
}