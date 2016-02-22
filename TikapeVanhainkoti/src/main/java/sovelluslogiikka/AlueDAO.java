
package sovelluslogiikka;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


/**
 *Hallinnoi yhteydenottoa SQL-tietokantaan ja suorittaa
 *Alue-olioiden SQL-kyselyt.
 */
public class AlueDAO implements Dao<Alue> {
    private Connection yhteys;
    
    public AlueDAO(String tietokannanNimi) throws SQLException {
        muodostaYhteys(tietokannanNimi);
    }
    
    
    @Override
    public void delete(Alue alue) throws SQLException {
        PreparedStatement stmt = yhteys.prepareStatement("");
    }

    @Override
    public void add(Alue alue) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Alue alue) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Alue> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void muodostaYhteys(String tietokannanNimi) throws SQLException {
        yhteys = DriverManager.getConnection(tietokannanNimi);
    }
}
