package sovelluslogiikka;

import java.sql.*;
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
    public List<Ketju> getAll(Integer alueId) throws SQLException {
        muodostaYhteys();
        List<Ketju> ketjut = new LinkedList<>();
        PreparedStatement stmt = yhteys.prepareStatement(
                "SELECT Ketju.Id AS kid, Ketju.alueid AS kaid, "
                + "Alue.nimi AS an, Ketju.nimi AS kn, "
                + "MAX(Viesti.pvm) AS pvm, COUNT(Viesti.id) AS maara "
                + "FROM Alue, Ketju, Viesti "
                + "WHERE Alue.Id = Ketju.AlueId "
                + "AND Ketju.Id = Viesti.KetjuId "
                + "AND Ketju.AlueId = ? "
                + "GROUP BY Ketju.Id "
                + "ORDER BY MAX(Viesti.pvm) DESC;");

//                PreparedStatement stmt = yhteys.prepareStatement(
//                "SELECT Ketju.Id, Ketju.alueid, Alue.nimi, Ketju.nimi, "
//                + "MAX(Viesti.pvm) AS pvm, COUNT(Viesti.id) AS maara "
//                + "FROM Viesti JOIN Ketju "
//                + "ON Ketju.Id = Viesti.KetjuId "
//                + "JOIN Alue "
//                + "ON Alue.Id = Ketju.AlueId "
//                + "WHERE Ketju.AlueId = ? "
//                + "GROUP BY Ketju.Id "
//                + "ORDER BY MAX(Viesti.pvm) DESC;");
        stmt.setInt(1, alueId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("kid");
            String nimi = rs.getString("kn");
            String alueNimi = rs.getString("an");
            int maara = rs.getInt("maara");

            Timestamp timestamp = new Timestamp(rs.getLong("pvm"));
            LocalDateTime pvm = timestamp.toLocalDateTime();

            ketjut.add(new Ketju(id, alueId, pvm, nimi, alueNimi, maara));

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
