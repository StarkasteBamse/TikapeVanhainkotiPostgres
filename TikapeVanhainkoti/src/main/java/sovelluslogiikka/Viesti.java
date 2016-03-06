package sovelluslogiikka;

import java.time.LocalDateTime;
import java.util.List;

public class Viesti {

    private final int id;
    private final String viesti;
    private final String nimimerkki;
    private final LocalDateTime pvm; //harkintaan datetime olio/unix-timestamp?
    private final int ketjuId;
    private final String knimi;
    private final int aid;
    private final String animi;

    public Viesti(String viesti, String nimimerkki, int ketjuId, int alueId) {
        this(0, viesti, nimimerkki, null, ketjuId, "", alueId, "");
    }

    public Viesti(int id, String viesti, String nimimerkki, LocalDateTime pvm, int ketjuId, String knimi, int aid, String animi) {
        this.id = id;
        this.viesti = viesti;
        this.nimimerkki = nimimerkki;
        this.pvm = pvm;
        this.ketjuId = ketjuId;
        this.aid = aid;
        this.knimi = knimi;
        this.animi = animi;
    }

    public int getId() {
        return id;
    }

    public int getKid() {
        return ketjuId;
    }

    public String getKnimi() {
        return knimi;
    }

    public int getAid() {
        return aid;
    }

    public String getAnimi() {
        return animi;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public LocalDateTime getPvm() {
        return pvm;
    }

    public String getViesti() {
        return viesti;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
}
