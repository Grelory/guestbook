package com.codecool.guestbook.dao;

import java.sql.SQLException;
import java.util.Collection;

public interface Dao<T> {

    Collection<T> getAll();
    boolean save(T t);


}
