
package sovelluslogiikka;

import java.sql.SQLException;
import java.util.List;

public class Viesti implements Dao<Viesti>{
    private final int id;
    private final String viesti;
    private final String nimimerkki;
    private final String pvm; //harkintaan datetime olio/unix-timestamp?
    private final int ketjuId;

    public Viesti(int id, String viesti, String nimimerkki, String pvm, int ketjuId) {
        this.id = id;
        this.viesti = viesti;
        this.nimimerkki = nimimerkki;
        this.pvm = pvm;
        this.ketjuId = ketjuId;
    }

    public int getId() {
        return id;
    }

    public int getKetjuId() {
        return ketjuId;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public String getPvm() {
        return pvm;
    }

    public String getViesti() {
        return viesti;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Viesti key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
