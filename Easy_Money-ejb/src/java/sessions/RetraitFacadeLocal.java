package sessions;

import entities.AnneeMois;
import entities.Client;
import entities.Retrait;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface RetraitFacadeLocal {

    void create(Retrait retrait);

    void edit(Retrait retrait);

    void remove(Retrait retrait);

    Retrait find(Object retrait);

    List<Retrait> findAll();

    List<Retrait> findRange(int[] range);

    int count();

    Long nextLongVal();

    List<Retrait> find(Client paramClient);

    List<Retrait> find(Client paramClient, Date paramDate1, Date paramDate2);

    List<Retrait> find(Client paramClient, Date paramDate);

    List<Retrait> findAllRange(Boolean paramBoolean) throws Exception;

    List<Retrait> find(Client paramClient, AnneeMois paramAnneeMois) throws Exception;
}
