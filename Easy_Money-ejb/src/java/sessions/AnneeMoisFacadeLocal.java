package sessions;

import entities.AnneeMois;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface AnneeMoisFacadeLocal {

    void create(AnneeMois anneeMois);

    void edit(AnneeMois anneeMois);

    void remove(AnneeMois anneeMois);

    AnneeMois find(Object id);

    List<AnneeMois> findAll();

    List<AnneeMois> findRange(int[] range);

    int count();

    Integer nextVal();

    List<AnneeMois> findByAnneeEtat(int idAnnee, boolean etat);

    List<AnneeMois> findByEtat(boolean etat);

    AnneeMois findByDate(Date date);

    List<AnneeMois> findByAnnee(int idAnnee);

    List<AnneeMois> findByRange();
}
