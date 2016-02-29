
package sovelluslogiikka;


public class Ketju {
    private final int id;
    private final int alueId;
    private final String nimi;

    public Ketju(int id, int alueId, String nimi) {
        this.alueId = alueId;
        this.id = id;
        this.nimi = nimi;
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
