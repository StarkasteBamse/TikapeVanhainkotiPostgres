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
    public int add(Ketju ketju) throws SQLException {
        //palautetaan luodun ketjun id

        muodostaYhteys();
        PreparedStatement stmt = yhteys.prepareStatement(
                "INSERT INTO Ketju(Nimi, AlueId) VALUES (?, ?);");
        stmt.setString(1, ketju.getNimi());
        stmt.setInt(2, ketju.getAid());
        stmt.execute();
        stmt.close();
        suljeYhteys();

        System.out.println("alueid, ketju" + ketju.getAid() + " " + ketju.getNimi());

//
// Taisinkin hampaita pestessä keksiä mikä ongelma on 
//  - Haemme ketjun Id:n tuolla MAX funktiolla ja talletamme sen sitten IsoinIDksi
//     mutta myöhemmin etsimme rs.getInt("id")
//


        muodostaYhteys();
        PreparedStatement stmt2 = yhteys.prepareStatement("SELECT MAX(id) AS IsoinID FROM Ketju "
                + "WHERE AlueId = ? "
                // ja yllä olevan kommentin perusteella nimen poiskommentointi (ja yhteyden sulkemiset) oli turhaa
                                                    + "AND Nimi = ? "
                + ";");

//        stmt = yhteys.prepareStatement("SELECT * FROM Ketju "
//                                    + "LEFT JOIN Viesti "
//                                    + "ON Ketju.id = Viesti.KetjuId "
//                                    + "WHERE Ketju.AlueId = ? "
//                                    + "AND Ketju.Nimi = ? "
//                                    + "AND Viesti.id IS NULL;");
        stmt2.setInt(1, ketju.getAid());
//        stmt2.setString(2, ketju.getNimi());
        ResultSet rs = stmt2.executeQuery();

        int ketjuId = rs.getInt("id");

        // Tämä sout ei tulostu, miksi?
        System.out.println("ketjuid " + ketjuId);

        rs.close();
        stmt2.close();
        suljeYhteys();
        return ketjuId;
    }

    @Override
    public void update(Ketju key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Ketju> getAll(Integer alueId) throws SQLException {
        muodostaYhteys();
        List<Ketju> ketjut = new LinkedList<>();
        // LEFT JOIN kunnes saadaan yhdistettyä sisältö ketjuun luonnissa
        PreparedStatement stmt = yhteys.prepareStatement(
                "SELECT Ketju.Id AS kid, Ketju.alueid AS kaid, "
                + "Alue.nimi AS an, Ketju.nimi AS kn, "
                + "MAX(Viesti.pvm) AS pvm, COUNT(Viesti.id) AS maara "
                + "FROM Ketju LEFT JOIN Viesti "
                + "ON Ketju.Id = Viesti.KetjuId "
                + "LEFT JOIN Alue "
                + "ON Alue.Id = Ketju.AlueId "
                + "WHERE Ketju.AlueId = ? "
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

            ketjut.add(new Ketju(id, alueId, pvm, nimi, alueNimi, maara, 0));

        }
        rs.close();
        stmt.close();
        suljeYhteys();
        return ketjut;
    }

    @Override
    public Ketju getOne(Integer kid) throws SQLException {
        muodostaYhteys();

        PreparedStatement stmt = yhteys.prepareStatement(
                "SELECT Ketju.Id AS kid, Ketju.alueid AS kaid, "
                + "Alue.nimi AS an, Ketju.nimi AS kn, "
                + "MAX(Viesti.pvm) AS pvm, COUNT(Viesti.id) AS maara "
                + "FROM Ketju LEFT JOIN Viesti "
                + "ON Ketju.Id = Viesti.KetjuId "
                + "LEFT JOIN Alue "
                + "ON Alue.Id = Ketju.AlueId "
                + "WHERE Ketju.Id = ? "
                + "GROUP BY Ketju.Id "
                + "ORDER BY MAX(Viesti.pvm) DESC;");

        stmt.setInt(1, kid);
        ResultSet rs = stmt.executeQuery();

        Ketju ketju = new Ketju(0, 0, null, "", "", 0, 0);

        while (rs.next()) {
            int id = rs.getInt("kid");
            int aid = rs.getInt("kaid");
            String nimi = rs.getString("kn");
            String alueNimi = rs.getString("an");
            int maara = rs.getInt("maara");

            Timestamp timestamp = new Timestamp(rs.getLong("pvm"));
            LocalDateTime pvm = timestamp.toLocalDateTime();

            ketju = new Ketju(id, aid, pvm, nimi, alueNimi, maara, 0);

        }
        rs.close();
        stmt.close();
        suljeYhteys();
        return ketju;
    }

    public void suljeYhteys() throws SQLException {
        yhteys.close();
    }

}
