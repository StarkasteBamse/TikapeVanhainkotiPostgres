package sovelluslogiikka;

import java.sql.*;

public class TKAlustaja {

    private Database database;
    private boolean debug = false;
    private String alustus;

    public TKAlustaja(Database database, Boolean sqlite) {
        this.database = database;
        if (sqlite) {
            alustus = "integer";
        } else {
            alustus = "SERIAL";
        }
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean kokeileYhteys() {
        try {
            Connection connection = database.getConnection();
            connection.close();
        } catch (SQLException e) {
            if (debug) {
                System.out.println(e);
            }
            return false;
        }
        return true;
    }

    public void luoTaulut() {

        //taulu "Alue" luonti
        taulunLuominen("CREATE TABLE Alue "
                + "(id " + alustus + " PRIMARY KEY AUTOINCREMENT, "
                + "nimi varchar(100) NOT NULL UNIQUE)");
        //taulu "Ketju" luonti
        taulunLuominen("CREATE TABLE Ketju "
                + "(id " + alustus + " PRIMARY KEY AUTOINCREMENT, "
                + "nimi varchar(100) NOT NULL, "
                + "alueId integer NOT NULL, "
                + "FOREIGN KEY (AlueId) REFERENCES Alue(Id))");
        //taulu "Viesti" luonti, Nimimerkin kanssa.
        taulunLuominen("CREATE TABLE Viesti "
                + "(id " + alustus + " PRIMARY KEY AUTOINCREMENT, "
                + "viesti text NOT NULL, "
                + "nimimerkki varchar(50) NOT NULL, "
                + "pvm datetime NOT NULL, "
                + "ketjuId integer NOT NULL, "
                + "FOREIGN KEY (KetjuId) REFERENCES Ketju(Id))");
        taulunLuominen("CREATE INDEX idx_ketjuId ON Ketju (id), "
                + "idx_viestiId ON Viesti (id)");

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
