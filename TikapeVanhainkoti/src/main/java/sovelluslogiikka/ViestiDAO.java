/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sovelluslogiikka;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *Hallinnoi yhteydenottoa SQL-tietokantaan ja suorittaa
 *Viesti-olioiden SQL-kyselyt.
 */
public class ViestiDAO implements Dao<Viesti> {
    private Connection yhteys;

    public ViestiDAO(String tietokannanNimi) throws SQLException {
        muodostaYhteys(tietokannanNimi);
    }

    public void muodostaYhteys(String tietokannanNimi) throws SQLException {
        yhteys = DriverManager.getConnection(tietokannanNimi);
    }
    
    @Override
    public void delete(Viesti key) throws SQLException {
        PreparedStatement stmt = yhteys.prepareStatement("");
    }

    @Override
    public void add(Viesti key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Viesti key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Viesti> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
