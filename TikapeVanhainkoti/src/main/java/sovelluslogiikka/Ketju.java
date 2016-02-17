
package sovelluslogiikka;


public class Ketju {
    private final int id;
    private final int alueId;
    private final String otsikko;

    public Ketju(int id, int alueId, String otsikko) {
        this.alueId = alueId;
        this.id = id;
        this.otsikko = otsikko;
    }

    public int getId() {
        return id;
    }

    public String getNimi() {
        return otsikko;
    }

    public int getAlueId() {
        return alueId;
    }
    
    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

  
    
    
    
}
