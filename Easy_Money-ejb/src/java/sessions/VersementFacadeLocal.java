package sessions;

import entities.AnneeMois;
import entities.Client;
import entities.Versement;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface VersementFacadeLocal {

    void create(Versement versement);

    void edit(Versement versement);

    void remove(Versement versement);

    Versement find(Object id);

    List<Versement> findAll();

    List<Versement> findRange(int[] range);

    int count();

    Long nextVal();

    List<Versement> find(Client client);

    List<Versement> find(Client client, Date paramDate1, Date paramDate2);

    List<Versement> find(Client client, Date paramDate);

    List<Versement> find(Client paramClient, AnneeMois paramAnneeMois) throws Exception;

    List<Versement> findAllRange();
}
