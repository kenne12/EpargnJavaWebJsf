package sessions;

import entities.Caisse;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CaisseFacadeLocal {

    void create(Caisse caisse);

    void edit(Caisse caisse);

    void remove(Caisse caisse);

    Caisse find(Object id);

    List<Caisse> findAll();

    List<Caisse> findRange(int[] range);

    int count();

    Integer nextVal();
}
