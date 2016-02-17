
package sovelluslogiikka;

public class Alue {
    private final int id;
    private final String nimi;

    public Alue(int id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }

    public int getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
