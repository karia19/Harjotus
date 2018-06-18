package harjoitusTyo.Dao;

import java.sql.*;
import java.util.*;

public interface Dao<T, K> {
    T findOne(K key) throws SQLException, Exception;
    List<T> findAll() throws SQLException, Exception;
    List<T> findAllBydId(K key) throws SQLException, Exception;
    T save(T object) throws SQLException, Exception;
    int count(K key) throws SQLException, Exception;
    int countAll() throws SQLException, Exception;
    int maxComments() throws SQLException, Exception;
    void delete(K key) throws SQLException, Exception;
}
