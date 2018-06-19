package harjoitusTyo.Dao;

import harjoitusTyo.domain.Artist;
import harjoitusTyo.Database.Database;

import java.sql.*;
import java.util.*;

public class ArtistDao implements Dao<Artist, Integer> {

    private Database database;

    public ArtistDao(Database database){
        this.database = database;
    }


    @Override
    public Artist findOne(Integer key) throws SQLException, Exception {
        Connection conn =database.getConnection();
        PreparedStatement pre = conn.prepareStatement("SELECT * FROM Artist WHERE id = ?");
        pre.setObject(1, key);
        ResultSet r = pre.executeQuery();
        boolean none = r.next();
        if (!none) {
            return null;
        }
        Artist artist = new Artist(r.getInt("id"), r.getString("nimi"));

        pre.close();
        r.close();
        conn.close();
        return artist;
    }

    @Override
    public List<Artist> findAll() throws SQLException , Exception{
        List<Artist> list = new ArrayList<>();

        try (Connection conn = database.getConnection();
             ResultSet rs = conn.prepareStatement("SELECT id, nimi FROM Artist").executeQuery()){

            while (rs.next()){
                list.add(new Artist(rs.getInt("id"),
                        rs.getString("nimi")));
            }
        }
        return list;

    }

    @Override
    public List<Artist> findAllBydId(Integer key) throws SQLException , Exception{
        return null;
    }

    @Override
    public Artist save(Artist object) throws SQLException, Exception {
        Connection dbconnection = null;
        PreparedStatement preparedStatement = null;

        try {
            dbconnection = database.getConnection();
            preparedStatement = dbconnection.prepareStatement("INSERT INTO Artist(nimi) VALUES (?)");
            dbconnection.setAutoCommit(false);
            preparedStatement.setString(1, object.getNimi());
            preparedStatement.addBatch();

            preparedStatement.executeBatch();

            dbconnection.commit();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            dbconnection.close();
        }
        return object;
    }

    @Override
    public int count(Integer key) throws SQLException {
        return 0;

    }

    @Override
    public int countAll() throws SQLException {
        return 0;
    }

    @Override
    public int maxComments() throws SQLException {
        return 0;
    }

    @Override
    public void delete(Integer key) throws SQLException, Exception {
        Connection conn = database.getConnection();
        PreparedStatement pre = conn.prepareStatement("DELETE FROM Kappaleet WHERE trackArtist = ?; DELETE FROM Artist WHERE id = ?");
        pre.setInt(1, key);
        pre.setInt(2, key);
        pre.executeUpdate();

        pre.close();
        conn.close();

    }
    public void deleteByName(String name) throws SQLException , Exception{
        Connection conn = database.getConnection();
        PreparedStatement pre = conn.prepareStatement("DELETE FORM Artist WHERE nimi = ?");
        pre.setString(1, name);
        pre.executeUpdate();

        pre.close();
        conn.close();
    }
}
