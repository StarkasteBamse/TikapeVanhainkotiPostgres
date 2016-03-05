package sovelluslogiikka;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sovelluslogiikka {

    private AlueDAO alueDao;
    private KetjuDAO ketjuDao;
    private ViestiDAO viestiDao;
    private Database database;
    private String tietokannanNimi;
    private TKAlustaja tkAlustaja;

    public Sovelluslogiikka(String tietokannanNimi) {
        this.tietokannanNimi = tietokannanNimi;        
    }

    public boolean kaynnista() {
        try {
            this.database = new Database(tietokannanNimi);
            this.tkAlustaja = new TKAlustaja(database);
            tkAlustaja.kokeileYhteys();
            tkAlustaja.luoTaulut();
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
        List<Alue> alueet = new LinkedList<>();
        try {
            alueet = alueDao.getAll(null);
        } catch (SQLException ex) {
            System.out.println(ex);}
        return alueet;
    }

    public List<Ketju> haeKetjut(int alue_id) {
        System.out.println("hui");
        List<Ketju> ketjut = new LinkedList<>();
        try {
            ketjut = ketjuDao.getAll(alue_id);
        } catch (SQLException ex) {
        }
        return ketjut;
    }

    public List<Viesti> haeViestit(int ketju_id) {
        List<Viesti> viestit = new LinkedList<>();
        try {
            viestit = viestiDao.getAll(ketju_id);
        } catch (SQLException ex) {
        }
        return viestit;
    }

    public boolean luoAlue(String nimi) {
        Alue luotavaAlue = new Alue(0, nimi, null , 0);
        try {
            alueDao.add(luotavaAlue);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean luoKetju(String otsikko, int alue_id) {
        Ketju luotavaKetju = new Ketju(0, alue_id, null, otsikko, null, 0);
        try {
            ketjuDao.add(luotavaKetju);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean luoViesti(String viesti, String nimimerkki, int ketju_id) {
        Viesti luotavaViesti = new Viesti(0, viesti, nimimerkki, null, ketju_id);
        try {
            viestiDao.add(luotavaViesti);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

}
