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

    Retrait find(Object id);

    List<Retrait> findAll();

    List<Retrait> findRange(int[] range);

    int count();

    Long nextLongVal();

    List<Retrait> find(Client client);

    List<Retrait> find(Client client, Date dateDebut, Date dateFin);

    List<Retrait> find(Client client, Date date);

    List<Retrait> findAllRange(boolean etat) throws Exception;

    List<Retrait> find(Client client, AnneeMois anneeMois) throws Exception;

    List<Retrait> findByIdMois(int idMois) throws Exception;

    List<Retrait> findByDate(Date date) throws Exception;
}
