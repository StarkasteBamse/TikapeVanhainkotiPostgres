
package sovelluslogiikka;

import java.time.LocalDateTime;


public class Ketju {
    private final int id;
    private final int alueId;
    private final LocalDateTime viimeisinPvm;
    private final String nimi;

    public Ketju(int id, int alueId, LocalDateTime viimeisinPvm, String nimi) {
        this.alueId = alueId;
        this.id = id;
        this.nimi = nimi;
        this.viimeisinPvm = viimeisinPvm;
    }

    public int getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public int getAlueId() {
        return alueId;
    }
    
    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

  
    
    
    
}
