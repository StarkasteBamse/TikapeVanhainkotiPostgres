
import sovelluslogiikka.Alue;


public class Main {
    public static void main(String[] args) {
        Alue alue1 = new Alue(1, "Ohjelmointi");
        System.out.println(alue1.getId() + ", " + alue1.getNimi());
    }
}
