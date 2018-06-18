package harjoitusTyo.Dao;

import harjoitusTyo.domain.Artist;
import harjoitusTyo.Database.Database;
import harjoitusTyo.domain.Kappaleet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KappaleetDao implements Dao<Kappaleet, Integer> {

    private Database database;
    private Dao<Artist, Integer> artistDao;


    public KappaleetDao(Database database, Dao<Artist, Integer> artistDao){
        this.database = database;
        this.artistDao = artistDao;
    }

    @Override
    public Kappaleet findOne(Integer key) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement st = conn.prepareStatement("SELECT * FROM Kappaleet WHERE trackArtist = ?");
        st.setObject(1, key);

        ResultSet r = st.executeQuery();
        boolean none = r.next();
        if (!none){
            return null;
        }
        ArrayList<Kappaleet> kappaleets = new ArrayList<>();


            Artist artist = this.artistDao.findOne(r.getInt("trackArtist"));
            Kappaleet k = new Kappaleet(key, artist, r.getString("nimi"), r.getInt("vuosi"),
                    r.getString("kommenti"));


        r.close();
        st.close();
        conn.close();

        return k;
    }

    @Override
    public List<Kappaleet> findAll() throws SQLException {
        List<Kappaleet> kap = new ArrayList<>();
        Connection conn = database.getConnection();
        PreparedStatement pre = conn.prepareStatement("SELECT * FROM Kappaleet");
        ResultSet res = pre.executeQuery();

        while(res.next()){
            Artist artist = this.artistDao.findOne(res.getInt("trackArtist"));
            Kappaleet k = new Kappaleet(-1, artist, res.getString("nimi"), res.getInt("vuosi"),
                    res.getString("kommenti"));

            kap.add(k);

        }
        res.close();
        conn.close();
        return kap;
    }

    @Override
    public List<Kappaleet> findAllBydId(Integer key) throws SQLException {
        List<Kappaleet> list = new ArrayList<>();
        Connection conn = database.getConnection();
        PreparedStatement st = conn.prepareStatement("SELECT * FROM Kappaleet WHERE trackArtist = ?");
        st.setObject(1, key);

        ResultSet r = st.executeQuery();

        while (r.next()) {

            Artist artist = this.artistDao.findOne(r.getInt("trackArtist"));
            Kappaleet k = new Kappaleet(key, artist, r.getString("nimi"), r.getInt("vuosi"),
                    r.getString("kommenti"));

            list.add(k);

        }
        r.close();
        st.close();
        conn.close();

        return list;


    }

    @Override
    public Kappaleet save(Kappaleet object) throws SQLException {
        Connection dbconnection = null;
        PreparedStatement preparedStatement = null;

        try {
            dbconnection = database.getConnection();
            preparedStatement = dbconnection.prepareStatement("INSERT INTO Kappaleet(trackArtist, nimi, vuosi, kommenti) VALUES (?, ?,?,?)");
            dbconnection.setAutoCommit(false);
            preparedStatement.setInt(1, object.getId());
            preparedStatement.setString(2, object.getNimi());
            preparedStatement.setInt(3, object.getVuosi());
            preparedStatement.setString(4, object.getKommenti());
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

        Connection conn = database.getConnection();
        PreparedStatement pre = conn.prepareStatement("SELECT COUNT (*) FROM Kappaleet WHERE trackArtist = ?");
        pre.setInt(1, key);
        ResultSet r = pre.executeQuery();

        int total = r.getInt(1);

        return total;
    }

    @Override
    public int countAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement pre = conn.prepareStatement("SELECT COUNT (*) FROM Kappaleet;");
        ResultSet r = pre.executeQuery();
        int total = r.getInt(1);

        return total;
    }

    @Override
    public int maxComments() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement pre = conn.prepareStatement("SELECT MAX(trackArtist) FROM Kappaleet");
        ResultSet r = pre.executeQuery();

        int tot = r.getInt(1);
        return tot;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement pre = conn.prepareStatement("DELETE FROM Kappaleet WHERE trackArtist = ?");
        pre.setInt(1, key);
        pre.executeUpdate();

        pre.close();
        conn.close();


    }
    private Kappaleet findByName(String name) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Artist, Kappaleet WHERE Artist.nimi = ? AND Kappaleet.trackArtist = Artist.id");
            stmt.setString(1, name);

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }
            Artist artist = this.artistDao.findOne(result.getInt("trackArtist"));

            Kappaleet k = new Kappaleet(-1, artist, result.getString("nimi"),result.getInt("vuosi"),
                    result.getString("kommenti"));

            return k;
        }
    }

}
