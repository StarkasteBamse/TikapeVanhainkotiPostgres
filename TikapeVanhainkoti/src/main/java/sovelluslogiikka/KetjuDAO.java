
package sovelluslogiikka;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
/**
 *Hallinnoi yhteydenottoa SQL-tietokantaan ja suorittaa
 *Ketju-olioiden SQL-kyselyt.
 */
public class KetjuDAO implements Dao<Alue, Ketju> {
    private Database database;
    private Connection yhteys;
    
    public KetjuDAO(Database database) throws SQLException {
        this.database = database;
    }
    
    public void muodostaYhteys(String tietokannanNimi) throws SQLException {
        yhteys = DriverManager.getConnection(tietokannanNimi);
    }
    
    @Override
    public void delete(Ketju key) throws SQLException {
        //käytetään jokaisessa metodissa omaa prepareStatementtia
        PreparedStatement stmt = yhteys.prepareStatement("");
        }

    @Override
    public void add(Ketju ketju) throws SQLException {
    
        PreparedStatement stmt = yhteys.prepareStatement(
                "INSERT INTO Ketju(Nimi, AlueId) VALUES (?, ?);");
        stmt.setString(1, ketju.getNimi());
        stmt.setInt(2, ketju.getAlueId());
        stmt.execute();
    }

    @Override
    public void update(Ketju key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Ketju> getAll(Alue kkey) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void suljeYhteys() throws SQLException {
        yhteys.close();
    }
    
    
}
