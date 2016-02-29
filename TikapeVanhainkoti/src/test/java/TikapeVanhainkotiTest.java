
import java.io.IOException;
import java.nio.file.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sovelluslogiikka.*;

public class TikapeVanhainkotiTest {

    Sovelluslogiikka sovelluslogiikka;

    public TikapeVanhainkotiTest() {

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.sovelluslogiikka = new Sovelluslogiikka("test.db");
    }

    @After
    public void tearDown() {
        
        
        
        Path test = Paths.get("test.db");
        try {
            Files.delete(test);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", test);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", test);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }

   
    @Test
    public void luotietokannan() {
        sovelluslogiikka.kaynnista();
        
    }
}
