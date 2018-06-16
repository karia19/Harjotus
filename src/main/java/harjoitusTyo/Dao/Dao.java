package harjoitusTyo.Dao;

import java.sql.*;
import java.util.*;

public interface Dao<T, K> {
    T findOne(K key) throws SQLException;
    List<T> findAll() throws SQLException;
    List<T> findAllBydId(K key) throws SQLException;
    T save(T object) throws SQLException;
    int count(K key) throws SQLException;
    int countAll() throws SQLException;
    int maxComments() throws SQLException;
    void delete(K key) throws SQLException;
}
