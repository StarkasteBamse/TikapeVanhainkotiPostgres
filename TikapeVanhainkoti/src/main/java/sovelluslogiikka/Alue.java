
package sovelluslogiikka;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Alue {
    private final int id;
    private final String nimi;
    private final LocalDateTime viimeisinPvm;
    private final int viestienLkm;
    private DateTimeFormatter formatoija;

    public Alue(int id, String nimi, LocalDateTime viimDateTime, int viestienLkm) {
        this.id = id;
        this.nimi = nimi;
        this.viimeisinPvm = viimDateTime;
        this.viestienLkm = viestienLkm;
        this.formatoija = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public int getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public String getPvm() {
        return viimeisinPvm.format(formatoija);
    }
    
    public int getLkm(){
        return viestienLkm;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
