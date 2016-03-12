package sovelluslogiikka;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Viesti {

    private final int id;
    private final String viesti;
    private final String nimimerkki;
    private final LocalDateTime pvm; 
    private final int ketjuId;
    private DateTimeFormatter formatoija;

    public Viesti(String viesti, String nimimerkki, int ketjuId, int alueId) {
        this(0, viesti, nimimerkki, null, ketjuId);
    }

    public Viesti(int id, String viesti, String nimimerkki, LocalDateTime pvm, int ketjuId) {
        this.id = id;
        this.viesti = viesti;
        this.nimimerkki = nimimerkki;
        this.pvm = pvm;
        this.ketjuId = ketjuId;
        this.formatoija = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public int getId() {
        return id;
    }

    public int getKid() {
        return ketjuId;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public String getPvm() {
        return pvm.format(formatoija);
    }

    public String getViesti() {
        return viesti;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
}
