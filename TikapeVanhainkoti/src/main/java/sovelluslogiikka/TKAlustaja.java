package sovelluslogiikka;

import java.sql.*;

public class TKAlustaja {
    
    private Database database;
    private boolean debug;

    public TKAlustaja(Database database) {
        this.database = database;
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
                    + "(Id integer PRIMARY KEY,"
                    + "Nimi varchar(100) NOT NULL)");
        //taulu "Ketju" luonti
        taulunLuominen("CREATE TABLE Ketju"
                    + "(Id integer PRIMARY KEY,"
                    + "Nimi varchar(100) NOT NULL,"
                    + "AlueId integer NOT NULL UNIQUE,"
                    + "FOREIGN KEY (AlueId) REFERENCES Alue(Id))");
         //taulu "Viesti" luonti, Nimimerkin kanssa.
        taulunLuominen("CREATE TABLE Viesti"
                    + "(Id integer PRIMARY KEY,"
                    + "Viesti text NOT NULL,"
                    + "Nimimerkki varchar(50) NOT NULL, "
                    + "Pvm datetime NOT NULL,"
                    + "KetjuId integer NOT NULL UNIQUE,"
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
