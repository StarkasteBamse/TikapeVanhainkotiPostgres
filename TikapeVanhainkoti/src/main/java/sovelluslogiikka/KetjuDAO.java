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
 * Hallinnoi yhteydenottoa SQL-tietokantaan ja suorittaa Ketju-olioiden
 * SQL-kyselyt.
 */
public class KetjuDAO implements Dao<Integer, Ketju> {

    private Database database;
    private Connection yhteys;

    public KetjuDAO(Database database) throws SQLException {
        this.database = database;
    }

    public void muodostaYhteys() throws SQLException {
        yhteys = database.getConnection();
    }

    @Override
    public void delete(Ketju key) throws SQLException {
        //käytetään jokaisessa metodissa omaa prepareStatementtia
        PreparedStatement stmt = yhteys.prepareStatement("");
    }

    @Override
    public void add(Ketju ketju) throws SQLException {
        muodostaYhteys();
        PreparedStatement stmt = yhteys.prepareStatement(
                "INSERT INTO Ketju(Nimi, AlueId) VALUES (?, ?);");
        stmt.setString(1, ketju.getNimi());
        stmt.setInt(2, ketju.getAlueId());
        stmt.execute();
        suljeYhteys();
    }

    @Override
    public void update(Ketju key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Ketju> getAll(Integer alueid) throws SQLException {
        muodostaYhteys();
        PreparedStatement stmt = yhteys.prepareStatement(
                "SELECT Ketju.Id, Ketju.alueid, Alue.nimi, ketju.nimi, "
                + "MAX(Viesti.pvm) AS pvm, COUNT(Viesti.id) AS maara "
                + "FROM Alue, Ketju, Viesti "
                + "WHERE Alue.Id = Ketju.AlueId "
                + "AND Ketju.Id = Viesti.KetjuId "
                + "AND Ketju.AlueId = ? "
                + "GROUP BY Ketju.Id "
                + "ORDER BY MAX(Viesti.pvm) DESC;");

        stmt.setInt(1, alueid);
        ResultSet rs = stmt.executeQuery();
        List<Ketju> ketjut = new LinkedList<>();

        while (rs.next()) {
            int id = rs.getInt("Ketju.Id");
            int alueId = rs.getInt("Ketju.alueid");
            String nimi = rs.getString("Ketju.nimi");
            String alueNimi = rs.getString("Alue.nimi");
            Timestamp timestamp = rs.getTimestamp("pvm");
            int maara = rs.getInt("maara");

            LocalDateTime pvmLCT = timestamp.toLocalDateTime();
            Ketju uusiKetju = new Ketju(id, alueId, pvmLCT, nimi, alueNimi, maara);
            ketjut.add(uusiKetju);
        }
        rs.close();
        stmt.close();
        suljeYhteys();
        return ketjut;
    }

    public void suljeYhteys() throws SQLException {
        yhteys.close();
    }

}
