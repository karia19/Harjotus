package harjoitusTyo.Database;

import java.sql.*;

public class Database {

    private String datbaseAdress;

    public Database(String datbaseAdress) throws ClassNotFoundException
    {
        this.datbaseAdress = datbaseAdress;
    }

    public Connection getConnection() throws SQLException
    {
        try {
            Class.forName("org.sqlite.JDBC");
        }catch (ClassNotFoundException e){
            System.out.println("Ei löydy");
        }
        return DriverManager.getConnection(datbaseAdress);
    }
}

