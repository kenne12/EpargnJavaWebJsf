package sessions;

import entities.AnneeMois;
import entities.Client;
import entities.FraisCarnet;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface FraisCarnetFacadeLocal {

    void create(FraisCarnet paramFraisCarnet);

    void edit(FraisCarnet paramFraisCarnet);

    void remove(FraisCarnet paramFraisCarnet);

    FraisCarnet find(Object paramObject);

    List<FraisCarnet> findAll();

    List<FraisCarnet> findRange(int[] paramArrayOfint);

    int count();

    Long nextVal();

    List<FraisCarnet> find(Client paramClient);

    List<FraisCarnet> find(Client paramClient, Date paramDate1, Date paramDate2);

    List<FraisCarnet> find(Client paramClient, Date paramDate);

    List<FraisCarnet> find(Client paramClient, AnneeMois paramAnneeMois) throws Exception;

    List<FraisCarnet> findAllRange();
}
