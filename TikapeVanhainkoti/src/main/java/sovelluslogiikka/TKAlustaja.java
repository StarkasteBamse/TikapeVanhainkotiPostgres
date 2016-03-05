package sovelluslogiikka;

import java.sql.*;

public class TKAlustaja {
    
    private Database database;
    private boolean debug = false;

    public TKAlustaja(Database database) {
        this.database = database;        
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    
    public void kokeileYhteys() {
            try{
                Connection connection = database.getConnection();
                connection.close();
            }             
            catch (SQLException e){
                if (debug) {
                    System.out.println(e);
                }
            }                       
    }
    
    public void luoTaulut() {    
        
        //taulu "Alue" luonti
        taulunLuominen("CREATE TABLE Alue"
                    + "(id integer PRIMARY KEY,"
                    + "nimi varchar(100) NOT NULL)");
        //taulu "Ketju" luonti
        taulunLuominen("CREATE TABLE Ketju"
                    + "(id integer PRIMARY KEY,"
                    + "nimi varchar(100) NOT NULL,"
                    + "alueId integer NOT NULL,"
                    + "FOREIGN KEY (AlueId) REFERENCES Alue(Id))");
         //taulu "Viesti" luonti, Nimimerkin kanssa.
        taulunLuominen("CREATE TABLE Viesti"
                    + "(id integer PRIMARY KEY,"
                    + "viesti text NOT NULL,"
                    + "nimimerkki varchar(50) NOT NULL, "
                    + "pvm datetime NOT NULL,"
                    + "ketjuId integer NOT NULL,"
                    + "FOREIGN KEY (KetjuId) REFERENCES Ketju(Id))");   
    }

    private void taulunLuominen(String s) {
        
        try {
            Connection connection = database.getConnection();
            Statement stmt = connection.createStatement();
            String sql = s;
            stmt.executeUpdate(sql);
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            if (debug) {
                System.out.println(e);
            }
        }
    }
    
}
