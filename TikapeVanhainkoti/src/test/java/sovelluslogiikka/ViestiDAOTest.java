/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author saaville
 */
public class ViestiDAOTest {
    private Database database;
    private String testiOsoite;
    private KetjuDAO kDao;
    private Connection yhteys;
    private Statement lausunto;
    private long testiaika;
    
    public ViestiDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        testiOsoite = "jdbc:sqlite:ViestiDAOTest.db";
        try {
            database = new Database(testiOsoite);
            kDao = new KetjuDAO(database);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        TKAlustaja tkalustaja = new TKAlustaja(database, true);
        tkalustaja.luoTaulut();
        luoAlueet();
    }
    
    public void poistaTietokanta() {
        try {
            File testiTietokanta = new File("ViestiDAOTest.db");
            testiTietokanta.delete();
        } catch (Exception se) {
            System.out.println(se.getMessage());
        }
    }
    
     public void luoAlueet() {
        luoYhteys();
        String alue1 = "Ohjelmoinnin alkeet";
        int id1 = 1;
        int id2 = 2;
        String alue2 = "Ohjelmoinnin eliitti";
        try {
            lausunto.executeUpdate("INSERT INTO Alue(Id, Nimi) VALUES (" + id1 + ", '"+ alue1 +"');");
            lausunto.executeUpdate("INSERT INTO Alue(Nimi) VALUES (" + id2 + ", '"+ alue2 +"');");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        katkaiseYhteys();
    }
    
    public void luoTestiKetjutJaViestit() {
        testiaika = System.currentTimeMillis();
        long aika1 = testiaika;
        long aika2 = testiaika - 10000;
        long aika3 = testiaika - 20000;
        long aika4 = testiaika - 30000;
        String ketju1 = "Ketju1";
        String ketju2 = "Ketju2";
        String ketju3 = "Ketju3";
        String ketju4 = "Ketju4";
        luoYhteys();
        int ketjuid1 = 11;
        int ketjuid2 = 22;
        int ketjuid3 = 33;
        int ketjuid4 = 44;
        int alueId = 1;
        try {
            lausunto.executeUpdate("INSERT INTO Ketju(id, nimi, alueid) VALUES ("
                                                    + "" + ketjuid1 + ", "
                                                    + "'" + ketju1 + "', "
                                                    + "" + alueId + ");");
            lausunto.executeUpdate("INSERT INTO Ketju(id, nimi, alueid) VALUES ("
                                                    + "" + ketjuid2 + ", "
                                                    + "'" + ketju2 + "', "
                                                    + "" + alueId + ");");
            lausunto.executeUpdate("INSERT INTO Ketju(id, nimi, alueid) VALUES ("
                                                    + "" + ketjuid3 + ", "
                                                    + "'" + ketju3 + "', "
                                                    + "" + alueId + ");");
            lausunto.executeUpdate("INSERT INTO Ketju(id, nimi, alueid) VALUES ("
                                                    + "" + ketjuid4 + ", "
                                                    + "'" + ketju4 + "', "
                                                    + "" + alueId + ");");
        } catch (SQLException se) {
            assertTrue(se.getMessage(), false);
        }
        try {
            lausunto.executeUpdate("INSERT INTO Viesti(viesti, ketjuid, pvm, nimimerkki) "
                                                    + "VALUES ("
                                                    + "'" + "I love bacon 55" + "', "
                                                    + "" + ketjuid1 + ", "
                                                    + "" + aika1 + ", "
                                                    + "'" + "Vegetarian 9" + "');");
            lausunto.executeUpdate("INSERT INTO Viesti(viesti, ketjuid, pvm, nimimerkki) "
                                                    + "VALUES ("
                                                    + "'" + "I love bacon 2" + "', "
                                                    + "" + ketjuid2 + ", "
                                                    + "" + aika2 + ", "
                                                    + "'" + "Vegetarian 2" + "');");
            lausunto.executeUpdate("INSERT INTO Viesti(viesti, ketjuid, pvm, nimimerkki) "
                                                    + "VALUES ("
                                                    + "'" + "I love bacon 3" + "', "
                                                    + "" + ketjuid3 + ", "
                                                    + "" + aika3 + ", "
                                                    + "'" + "Vegetarian 3" + "');");
            lausunto.executeUpdate("INSERT INTO Viesti(viesti, ketjuid, pvm, nimimerkki) "
                                                    + "VALUES ("
                                                    + "'" + "I love bacon 4" + "', "
                                                    + "" + ketjuid4 + ", "
                                                    + "" + aika4 + ", "
                                                    + "'" + "Vegetarian 4" + "');");
            lausunto.executeUpdate("INSERT INTO Viesti(viesti, ketjuid, pvm, nimimerkki) "
                                                    + "VALUES ("
                                                    + "'" + "I also love Avocado" + "', "
                                                    + "" + ketjuid4 + ", "
                                                    + "" + (aika4 - 10000) + ", "
                                                    + "'" + "Vegetarian 4" + "');");
            lausunto.executeUpdate("INSERT INTO Viesti(viesti, ketjuid, pvm, nimimerkki) "
                                                    + "VALUES ("
                                                    + "'" + "I also love your sister" + "', "
                                                    + "" + ketjuid4 + ", "
                                                    + "" + (aika4 - 20000) + ", "
                                                    + "'" + "Vegetarian 4" + "');");
            lausunto.executeUpdate("INSERT INTO Viesti(viesti, ketjuid, pvm, nimimerkki) "
                                                    + "VALUES ("
                                                    + "'" + "Im in love with Kevin Bacon" + "', "
                                                    + "" + ketjuid4 + ", "
                                                    + "" + (aika4 - 35000) + ", "
                                                    + "'" + "Vegetarian 55" + "');");
        } catch (SQLException ex) {
            assertTrue(ex.getMessage(), false);;
        }
        katkaiseYhteys();
    }
    
    public void poistaKetjutJaViestit() {
        poistaTietokanta();
        TKAlustaja tkalustaja = new TKAlustaja(database, true);
        tkalustaja.luoTaulut();
        luoAlueet();
    }
    
    public void luoYhteys() {
        try {
            yhteys = database.getConnection();
            lausunto = yhteys.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(KetjuDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void katkaiseYhteys() {
        try {
            if (lausunto != null) lausunto.close();
            if (yhteys != null) yhteys.close();
        } catch (SQLException se) {
            System.out.println();
        }
    }
    
    
    @After
    public void tearDown() {
        poistaKetjutJaViestit();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
