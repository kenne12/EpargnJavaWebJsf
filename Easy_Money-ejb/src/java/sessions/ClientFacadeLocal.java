package sessions;

import entities.Client;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ClientFacadeLocal {

    void create(Client client);

    void edit(Client client);

    void remove(Client client);

    Client find(Object id);

    List<Client> findAll();

    List<Client> findRange(int[] paramArrayOfint);

    int count();

    List<Client> findAll1();

    Integer nextVal();

    Client findByCni(String paramString);

    Client findByNumeroCarnet(Integer paramInteger);

    List<Client> findAllRange();

    List<Client> findAllRange(boolean paramBoolean);
}
