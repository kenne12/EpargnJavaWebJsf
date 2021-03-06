package sessions;

import entities.Annee;
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

    List<AnneeMois> findByAnneeEtat(Annee paramAnnee, boolean paramBoolean) throws Exception;

    List<AnneeMois> findByEtat(Boolean paramBoolean) throws Exception;

    AnneeMois findByDate(Date paramDate) throws Exception;

    List<AnneeMois> findByAnnee(Annee paramAnnee) throws Exception;
}
