
package sovelluslogiikka;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 *Hallinnoi yhteydenottoa SQL-tietokantaan ja suorittaa
 *Viesti-olioiden SQL-kyselyt.
 */
public class ViestiDAO implements Dao<Integer, Viesti> {
    private Database database;
    private Connection yhteys;
    
    public ViestiDAO(Database database) throws SQLException {
        this.database = database;
    }

    public void muodostaYhteys() throws SQLException {
        yhteys = database.getConnection();
    }
    
    public void suljeYhteys() throws SQLException {
        yhteys.close();
    }
    
    @Override
    public void delete(Viesti key) throws SQLException {
        PreparedStatement stmt = yhteys.prepareStatement("");
    }

    @Override
    public void add(Viesti viesti) throws SQLException {
        muodostaYhteys();
        PreparedStatement stmt = yhteys.prepareStatement(
                "INSERT INTO Viesti(Viesti, Nimimerkki, Pvm, KetjuId) "
                                            + "VALUES (?, ?, ?, ?);");
        stmt.setString(1, viesti.getViesti());
        stmt.setString(2, viesti.getNimimerkki());
        
        // Viestioliota luodessa jätä pvm null-arvoon
        long pvm = System.currentTimeMillis();
        stmt.setLong(3, pvm);
        stmt.setInt(4, viesti.getKid());
        stmt.execute();
        stmt.close();
        suljeYhteys();
    }

    @Override
    public void update(Viesti key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Viesti> getAll(Integer ketjuId) throws SQLException {
        muodostaYhteys();
        PreparedStatement stmt = yhteys.prepareStatement(
                "SELECT * FROM Viesti WHERE KetjuId = ?;");
        stmt.setInt(1, ketjuId);
        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new LinkedList<>();
        
        while (rs.next()) {
            int id = rs.getInt("Id");
            String viesti = rs.getString("viesti");
            String nimimerkki = rs.getString("nimimerkki");
            
            Timestamp pvmTimestamp = new Timestamp(rs.getLong("pvm"));
            LocalDateTime pvm = pvmTimestamp.toLocalDateTime();
            Viesti uusiViesti = new Viesti(id, viesti, nimimerkki, pvm, ketjuId);
            viestit.add(uusiViesti);
        }
        rs.close();
        stmt.close();
        suljeYhteys();
        return viestit;
    }
    /**
     * Palauttaa pyydetyn sivunumeron (monesko sivu halutaan) mukaiset viestit.
     * Viestien lukumäärä/sivu annetaan myös parametrina.
     * @param ketjuId minkä ketjun viestejä halutaan listata
     * @param lkmPerSivu kuinka monta viestiä per sivu
     * @param sivuNumero monesko sivu halutaan
     * @return
     * @throws SQLException 
     */
    
    public List<Viesti> getOnePage(Integer ketjuId, int lkmPerSivu, int sivuNumero) throws SQLException {
        muodostaYhteys();
        
        PreparedStatement stmt = yhteys.prepareStatement(
                "SELECT * FROM Viesti WHERE KetjuId = ? "
                    + "ORDER BY pvm ASC LIMIT " + lkmPerSivu + " "
                    + "OFFSET " + (lkmPerSivu * (sivuNumero - 1)) + ";");
        stmt.setInt(1, ketjuId);
        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new LinkedList<>();
        
        while (rs.next()) {
            int id = rs.getInt("Id");
            String viesti = rs.getString("viesti");
            String nimimerkki = rs.getString("nimimerkki");
            
            Timestamp pvmTimestamp = new Timestamp(rs.getLong("pvm"));
            LocalDateTime pvm = pvmTimestamp.toLocalDateTime();
            Viesti uusiViesti = new Viesti(id, viesti, nimimerkki, pvm, ketjuId);
            viestit.add(uusiViesti);
        }
        rs.close();
        stmt.close();
        suljeYhteys();
        return viestit;
    }
    
}
