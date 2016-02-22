
package sovelluslogiikka;

import java.sql.SQLException;
import java.util.List;


public interface Dao<T> {
    
    void delete(T key) throws SQLException;
    void add(T key) throws SQLException;
    void update(T key) throws SQLException;
    List<T> getAll() throws SQLException;
}

