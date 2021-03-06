package sessions;

import entities.Mouchard;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MouchardFacadeLocal {

    void create(Mouchard paramMouchard);

    void edit(Mouchard paramMouchard);

    void remove(Mouchard paramMouchard);

    Mouchard find(Object paramObject);

    List<Mouchard> findAll();

    List<Mouchard> findRange(int[] paramArrayOfint);

    int count();

    Long nextVal();
}
