package sovelluslogiikka;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Sovelluslogiikka {

    private AlueDAO alueDao;
    private KetjuDAO ketjuDao;
    private ViestiDAO viestiDao;
    private Database database;
    private String tietokannanNimi;

    public Sovelluslogiikka(String tietokannanNimi) {
        this.tietokannanNimi = tietokannanNimi;
        
    }

    public boolean kaynnista() {
        try {
            this.database = new Database(tietokannanNimi);
            this.alueDao = new AlueDAO(database);
            this.ketjuDao = new KetjuDAO(database);
            this.viestiDao = new ViestiDAO(database);
        } catch (SQLException se) {
            return false;
        } catch (ClassNotFoundException e) {
            
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
