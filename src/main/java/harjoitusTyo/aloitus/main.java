package harjoitusTyo.aloitus;

import harjoitusTyo.Dao.ArtistDao;
import harjoitusTyo.Dao.KappaleetDao;
import harjoitusTyo.Database.Database;
import harjoitusTyo.domain.Artist;
import harjoitusTyo.domain.Kappaleet;
import spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.sql.PreparedStatement;
import spark.ModelAndView;

import static spark.Spark.port;

public class main {

    /// Database ///

    public static Connection getConnection() throws Exception {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }

        return DriverManager.getConnection("jdbc:sqlite:huonekalut.db");
    }



    public static void main(String[] args) throws ClassNotFoundException, SQLException {



        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

        Database database = new Database("jdbc:sqlite:Songs.db ");
        ArtistDao artistDao = new ArtistDao(database);
        KappaleetDao kappaleetDao = new KappaleetDao(database , artistDao);

        /// Eka Sivu ////

        Spark.get("/sivu", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("artist", artistDao.findAll());


            map.put("kpl", kappaleetDao.countAll());
            //map.put("Max", kappaleetDao.maxComments());
            map.put("MaxArtist", artistDao.findOne(kappaleetDao.maxComments()));

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());


        /// Toka sivu lisää artisti ///


        Spark.get("/sivu/artisti", (reg, res) -> {

            HashMap ma = new HashMap();
            ma.put("artist", artistDao.findAll());

            return new ModelAndView(ma, "artisti");
        }, new ThymeleafTemplateEngine());



        Spark.post("/sivu/artisti", (Request req, Response res) -> {
            HashMap map1 = new HashMap<>();
            String id = req.queryParams("remove");
            //artistDao.delete(Integer.parseInt(id));
            //kappaleetDao.delete(Integer.parseInt(id));

            String name = req.queryParams("name");
            if (name.isEmpty()){
                map1.put("uusiArtisti", "....You miss something....");
                artistDao.delete(Integer.parseInt(id));

            } else {
                Artist artist = new Artist(-1, req.queryParams("name"));
                artistDao.save(artist);
                map1.put("artist", artistDao.findAll());

            }
            return new ModelAndView(map1, "artisti");
        }, new ThymeleafTemplateEngine());



        /// Lisää kappale ja muut ////

        Spark.get("/sivu/songs",(reg, res) -> {
            HashMap m = new HashMap();
            m.put("artist", artistDao.findAll());

            return new ModelAndView(m, "songs");
        }, new ThymeleafTemplateEngine());

        Spark.post("/sivu/songs", (req, res) -> {
            HashMap map = new HashMap();
            int id = Integer.parseInt(req.queryParams("artistii"));
            System.out.println(id);
            //String artist = req.queryParams("artist");
            String kappale = req.queryParams("name");
            Integer vuosi = Integer.parseInt(req.queryParams("year"));
            String tarina = req.queryParams("story");

            if (id == 0 || kappale.isEmpty() || vuosi == null || tarina.isEmpty()){
                map.put("virhe", "....Something is missing....");
            } else {
                Artist artist = new Artist(1,"mee");
                Kappaleet kappaleet = new Kappaleet(id, artist, kappale, vuosi, tarina);
                kappaleetDao.save(kappaleet);

                System.out.println(id + kappale + vuosi + tarina);
            }

            return new ModelAndView(map, "songs");
        }, new ThymeleafTemplateEngine());


        /// Stats metodit ////

        Spark.get("/sivu/stats", (reg, res) ->{
            HashMap m = new HashMap();
            m.put("artist", artistDao.findAll());

            return new ModelAndView(m, "stats");
        }, new ThymeleafTemplateEngine());

        Spark.post("/sivu/stats", (req, res) -> {
            String idd = req.queryParams("artistii");

            HashMap m = new HashMap();
            m.put("kappale", kappaleetDao.findAllBydId(Integer.parseInt(idd)));
            m.put("artist", artistDao.findAll());

            return new ModelAndView(m, "stats");
        }, new ThymeleafTemplateEngine());


    }

}
