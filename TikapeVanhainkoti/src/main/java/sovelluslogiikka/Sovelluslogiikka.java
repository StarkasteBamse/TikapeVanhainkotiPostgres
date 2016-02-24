package sovelluslogiikka;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Sovelluslogiikka {

    private AlueDAO alueDao;
    private KetjuDAO ketjuDao;
    private ViestiDAO viestiDao;
    private String tietokannanNimi;

    public Sovelluslogiikka(String tietokannanNimi) {
        this.tietokannanNimi = tietokannanNimi;
    }

    public boolean kaynnista() {
        try {
            this.alueDao = new AlueDAO(tietokannanNimi);
            this.ketjuDao = new KetjuDAO(tietokannanNimi);
            this.viestiDao = new ViestiDAO(tietokannanNimi);
        } catch (SQLException se) {
            return false;
        }
        return true;
    }

    /*
     Metodit:
    
     List<Alue> haeAlueet();
     List<Ketju> haeKetjut(int alue_id);
     List<Viesti> haeViestit(int ketju_id);
     void luoTietokanta() {
    
     boolean luoAlue(String nimi);
     boolean luoKetju(String otsikko, int alue_id);
     boolean luoViesti(String viesti, String nimimerkki, int ketju_id);
    
     Jos vastuut ja metodit pysyy vähäisinä, niin pitäisikö Sovelluslogiikan
     adoptoida DAOiden toiminnallisuus?
    
     Käsittelee myös DAO:n heittämät SQLExceptionit.
     */
    public void luoTietokanta() {

        Connection connection = null;
        //tietokannan avaus ja luonti, jos tietokantaa ei ole olemassa vielä. Tällä hetkellä TiKaPeVanhainkoti kansion rootiin viittaus.
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + tietokannanNimi);
            System.out.println("Opened database successfully");
            System.out.println("****************************");
        } catch (Exception e) {
            System.out.println("sum thing no worki wit mah DATABASE!! >_<");
        }
        //taulun "Alue" luonti
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE Alue"
                    + "(Id integer PRIMARY KEY,"
                    + "Nimi varchar(100) NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("Table \"Alue\" created successfully");
        } catch (Exception e) {
            System.out.println("Table \"Alue\" exist already");
        }
        //taulu "Ketju" luonti
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE Ketju"
                    + "(Id integer PRIMARY KEY,"
                    + "Nimi varchar(100) NOT NULL,"
                    + "AlueId integer NOT NULL UNIQUE,"
                    + "FOREIGN KEY (AlueId) REFERENCES Alue(Id))";
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("Table \"Ketju\" created successfully");
        } catch (Exception e) {
            System.out.println("Table \"Ketju\" exist already");
        }
        //taulu "Viesti" luonti, Nimimerkin kanssa.
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE Viesti"
                    + "(Id integer PRIMARY KEY,"
                    + "Viesti text NOT NULL,"
                    + "Nimimerkki varchar(50) NOT NULL, "
                    + "Pvm datetime NOT NULL,"
                    + "KetjuId integer NOT NULL UNIQUE,"
                    + "FOREIGN KEY (KetjuId) REFERENCES Ketju(Id))";
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("Table \"Viesti\" created successfully");
        } catch (Exception e) {
            System.out.println("Table \"Viesti\" exist already");
        }
        try {
            connection.close();
        } catch (Exception e) {
        }
    }

    public List<Alue> haeAlueet() {
        List<Alue> alueet = new ArrayList<>();
        return alueet;
    }

    public List<Ketju> haeKetjut(int alue_id) {
        List<Ketju> ketjut = new ArrayList<>();
        return ketjut;
    }

    public List<Viesti> haeViestit(int ketju_id) {
        List<Viesti> viestit = new ArrayList<>();
        return viestit;
    }

    public boolean luoAlue(String nimi) {
        return false;
    }

    public boolean luoKetju(String otsikko, int alue_id) {
        return false;
    }

    public boolean luoViesti(String viesti, String nimimerkki, int ketju_id) {
        return false;
    }

}
