
package sovelluslogiikka;

import java.time.LocalDateTime;

public class Alue {
    private final int id;
    private final String nimi;
    private final LocalDateTime viimeisinPvm;
    private final int viestienLkm;

    public Alue(int id, String nimi, LocalDateTime viimDateTime, int viestienLkm) {
        this.id = id;
        this.nimi = nimi;
        this.viimeisinPvm = viimDateTime;
        this.viestienLkm = viestienLkm;
    }

    public int getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public LocalDateTime getViimeisinPvm() {
        return viimeisinPvm;
    }
    
    public int getLkm(){
        return viestienLkm;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
