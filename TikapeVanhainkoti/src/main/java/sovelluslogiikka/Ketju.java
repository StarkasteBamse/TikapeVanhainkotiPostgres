
package sovelluslogiikka;

import java.time.LocalDateTime;


public class Ketju {
    private final int id;
    private final int alueId;
    private final LocalDateTime viimeisinPvm;
    private final String nimi;
    private final String alueNimi;
    private int viestienMaara;
    private int sivu;

    public Ketju(int id, int alueId, LocalDateTime viimeisinPvm, String nimi, String alueNimi, int viestienMaara, int sivu) {
        this.alueId = alueId;
        this.id = id;
        this.nimi = nimi;
        this.viimeisinPvm = viimeisinPvm;
        this.alueNimi = alueNimi;
        this.viestienMaara = viestienMaara;
        this.sivu = sivu;
    }

    public int getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public int getAid() {
        return alueId;
    }

    public String getAnimi() {
        return alueNimi;
    }

    public LocalDateTime getPvm() {
        return viimeisinPvm;
    }

    public int getLkm(){
        return viestienMaara;
    }
    
    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

  
    
    
    
}
