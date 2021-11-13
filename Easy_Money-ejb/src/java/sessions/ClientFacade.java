package sessions;

import entities.Client;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ClientFacade extends AbstractFacade<Client> implements ClientFacadeLocal {

    @PersistenceContext(unitName = "Easy_Money-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public ClientFacade() {
        super(Client.class);
    }

    @Override
    public List<Client> findAll1() {
        Query query = this.em.createQuery("SELECT c FROM Client c ORDER BY c.numerocarnet,c.nom,c.prenom,c.solde");
        return query.getResultList();
    }

    @Override
    public Integer nextVal() {
        Query query = this.em.createQuery("SELECT MAX(c.idclient) FROM Client c");
        try {
            return ((Integer) query.getSingleResult() + 1);
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public Client findByCni(String cni) {
        Query query = this.em.createQuery("SELECT c FROM Client c WHERE UPPER(c.cni)=:cni");
        query.setParameter("cni", cni.toUpperCase());
        List list = query.getResultList();
        if (!list.isEmpty()) {
            return (Client) list.get(0);
        }
        return null;
    }

    @Override
    public Client findByNumeroCarnet(Integer carnet) {
        Query query = this.em.createQuery("SELECT c FROM Client c WHERE c.numerocarnet=:numerocarnet");
        query.setParameter("numerocarnet", carnet);
        List list = query.getResultList();
        if (!list.isEmpty()) {
            return (Client) list.get(0);
        }
        return null;
    }

    @Override
    public List<Client> findAllRange() {
        Query query = this.em.createQuery("SELECT c FROM Client c ORDER BY c.numerocarnet,c.nom,c.prenom,c.solde");
        return query.getResultList();
    }

    @Override
    public List<Client> findAllRange(boolean etat) {
        Query query = this.em.createQuery("SELECT c FROM Client c WHERE c.etat=:etat ORDER BY c.numerocarnet,c.nom,c.prenom,c.solde");
        query.setParameter("etat", etat);
        return query.getResultList();
    }
}
