
package sovelluslogiikka;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


/**
 *Hallinnoi yhteydenottoa SQL-tietokantaan ja suorittaa
 *Alue-olioiden SQL-kyselyt.
 */
public class AlueDAO implements Dao<Ketju, Alue> {

    private Database database;
    private Connection yhteys;

    public AlueDAO(Database database) throws SQLException {
        this.database = database;
    }
    
    public void muodostaYhteys() throws SQLException {
        yhteys = database.getConnection();
    }
    
    @Override
    public void delete(Alue alue) throws SQLException {
        //käytetään jokaisessa metodissa omaa prepareStatementtia
        PreparedStatement stmt = yhteys.prepareStatement("");
    }

    @Override
    public void add(Alue alue) throws SQLException {
        muodostaYhteys();
        PreparedStatement stmt = yhteys.prepareStatement(
            "INSERT INTO Alue(Nimi) VALUES (?);");
        stmt.setString(1, alue.getNimi());
        stmt.execute();
        suljeYhteys();
    }

    @Override
    public void update(Alue alue) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override //tämä hakee vain jos löytyy Alue, ketju ja viesti. Eli ei löydä pelkkää aluetta
    public List<Alue> getAll(Ketju kkey) throws SQLException {
        muodostaYhteys();
        PreparedStatement stmt = yhteys.prepareStatement(
            "SELECT Alue.id, Alue.nimi, COUNT(Viesti.id) AS Viesteja, MAX(viesti.pvm) AS Viimeisin "
            + "FROM Alue "
            + "JOIN Ketju "
            + "ON Alue.id = Ketju.alueid "
            + "JOIN Viesti "
            + "ON Ketju.id = Viesti.ketjuid "
            + "GROUP BY ketju.alueid "
            + "ORDER BY Alue.Nimi ASC;");
        
        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new LinkedList<>();
        
        while (rs.next()) {
            int id = rs.getInt("id");
            String nimi = rs.getString("nimi");
//            Timestamp timestamp = rs.getTimestamp("Viimeisin"); //tämän kanssa ongelmia, Error parsing time stamp
            int viestienLkm = rs.getInt("Viesteja");
//           LocalDateTime pvmLCT = timestamp.toLocalDateTime(); //tämän kanssa ongelmia, Error parsing time stamp
            Alue uusiAlue = new Alue(id, nimi, null, viestienLkm);
            alueet.add(uusiAlue);
        }
        rs.close();
        stmt.close();
        suljeYhteys();
        return alueet;
    }
    
    public void suljeYhteys() throws SQLException {
        yhteys.close();
    }
}
