package sovelluslogiikka;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * Hallinnoi yhteydenottoa SQL-tietokantaan ja suorittaa Alue-olioiden
 * SQL-kyselyt.
 */
public class AlueDAO implements Dao<Integer, Alue> {
    
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
        
        PreparedStatement stmt = yhteys.prepareStatement("");
    }

    @Override
    public int add(Alue alue) throws SQLException {
        // palautetaan luodun alueen id
        muodostaYhteys();
        PreparedStatement stmt = yhteys.prepareStatement(
                "INSERT INTO Alue(Nimi) VALUES (?);");
        stmt.setString(1, alue.getNimi());
        stmt.execute();
        suljeYhteys();

        return 1;
    }

    @Override
    public void update(Alue alue) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Alue> getAll(Integer x) throws SQLException {
        muodostaYhteys();
        PreparedStatement stmt = yhteys.prepareStatement(
                "SELECT Alue.id, Alue.nimi, COUNT(Viesti.id) AS Viesteja, "
                + "MAX(viesti.pvm) AS Viimeisin "
                + "FROM Alue "
                + "LEFT JOIN Ketju "
                + "ON Alue.id = Ketju.alueid "
                + "LEFT JOIN Viesti "
                + "ON Ketju.id = Viesti.ketjuid "
                + "GROUP BY alue.id "
                + "ORDER BY Alue.Nimi ASC;");

        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new LinkedList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            int viestienLkm = rs.getInt("Viesteja");

            Timestamp timestamp = new Timestamp(rs.getLong("Viimeisin"));
            LocalDateTime pvm = timestamp.toLocalDateTime();

            alueet.add(new Alue(id, nimi, pvm, viestienLkm));

        }
        rs.close();
        stmt.close();
        suljeYhteys();
        return alueet;
    }

    @Override
    public Alue getOne(Integer aid) throws SQLException {
        muodostaYhteys();
        PreparedStatement stmt = yhteys.prepareStatement(
                "SELECT Alue.id, Alue.nimi, COUNT(Viesti.id) AS Viesteja, "
                + "MAX(viesti.pvm) AS Viimeisin "
                + "FROM Alue "
                + "LEFT JOIN Ketju "
                + "ON Alue.id = Ketju.alueid "
                + "LEFT JOIN Viesti "
                + "ON Ketju.id = Viesti.ketjuid "
                + "WHERE Alue.id = ? "
                + "GROUP BY ketju.alueid "
                + "ORDER BY Alue.Nimi ASC;");

        stmt.setInt(1, aid);
        ResultSet rs = stmt.executeQuery();
        Alue alue = new Alue(0, "", null, 0);

        while (rs.next()) {
            int id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            int viestienLkm = rs.getInt("Viesteja");

            Timestamp timestamp = new Timestamp(rs.getLong("Viimeisin"));
            LocalDateTime pvm = timestamp.toLocalDateTime();

            alue = new Alue(id, nimi, pvm, viestienLkm);

        }
        rs.close();
        stmt.close();
        suljeYhteys();
        return alue;
    }

    public void suljeYhteys() throws SQLException {
        yhteys.close();
    }

    @Override
    public int getAmount(Integer alueId) throws SQLException {
        muodostaYhteys();

        PreparedStatement stmt = yhteys.prepareStatement(
                "SELECT COUNT(ketju.id) AS Maara FROM Alue, Ketju "
              + "WHERE alue.id = ketju.alueid "
              + "AND alue.id = ?;");  
        stmt.setInt(1, alueId);
        ResultSet rs = stmt.executeQuery();
        int lkm = rs.getInt("Maara");
        
        rs.close();
        suljeYhteys();
        
        return lkm;
    }
}
