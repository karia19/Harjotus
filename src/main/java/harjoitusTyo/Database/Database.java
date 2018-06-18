package harjoitusTyo.Database;

import java.sql.*;

public class Database {

    private String datbaseAdress;

    public Database(String datbaseAdress) throws ClassNotFoundException, Exception
    {
        this.datbaseAdress = datbaseAdress;
    }
    /*

    public Connection getConnection() throws SQLException
    {
        try {
            Class.forName("org.sqlite.JDBC");
        }catch (ClassNotFoundException e){
            System.out.println("Ei löydy");
        }
        return DriverManager.getConnection(datbaseAdress);
    }
    */
    public  Connection getConnection() throws Exception {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }

        return DriverManager.getConnection("jdbc:sqlite:HarjoitusTyö.db");
    }

}

