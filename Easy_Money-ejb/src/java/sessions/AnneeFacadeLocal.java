package sessions;

import entities.Annee;
import java.util.List;
import javax.ejb.Local;

@Local
public interface AnneeFacadeLocal {

    void create(Annee annee);

    void edit(Annee annee);

    void remove(Annee annee);

    Annee find(Object id);

    List<Annee> findAll();

    List<Annee> findRange(int[] range);

    int count();

    List<Annee> findByEtat(boolean etat) throws Exception;
}
