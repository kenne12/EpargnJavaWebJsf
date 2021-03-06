package sessions;

import entities.AnneeMois;
import entities.Client;
import entities.FraisCarnet;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class FraisCarnetFacade extends AbstractFacade<FraisCarnet> implements FraisCarnetFacadeLocal {

    @PersistenceContext(unitName = "Easy_Money-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public FraisCarnetFacade() {
        super(FraisCarnet.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(f.id) FROM FraisCarnet f");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = (result + 1L);
        }
        return result;
    }

    @Override
    public List<FraisCarnet> find(Client client) {
        Query query = this.em.createQuery("SELECT f FROM FraisCarnet f WHERE f.idclient.idclient=:client");
        query.setParameter("client", client.getIdclient());
        return query.getResultList();
    }

    @Override
    public List<FraisCarnet> find(Client client, Date datedebut, Date datefin) {
        Query query = this.em.createQuery("SELECT f FROM FraisCarnet f WHERE f.idclient.idclient=:client AND f.dateOperation BETWEEN :datedebut AND  :datefin");
        query.setParameter("client", client.getIdclient());
        query.setParameter("datedebut", datedebut).setParameter("datefin", datefin);
        return query.getResultList();
    }

    @Override
    public List<FraisCarnet> find(Client client, Date date) {
        Query query = this.em.createQuery("SELECT f FROM FraisCarnet f WHERE f.idclient.idclient=:client AND f.dateOperation=:date");
        query.setParameter("client", client.getIdclient());
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public List<FraisCarnet> find(Client client, AnneeMois anneeMois) throws Exception {
        Query query = this.em.createQuery("SELECT f FROM FraisCarnet f WHERE f.idclient.idclient=:client AND f.idmois.idAnneeMois =:mois ORDER BY f.dateOperation");
        query.setParameter("client", client.getIdclient());
        query.setParameter("mois", anneeMois.getIdAnneeMois());
        return query.getResultList();
    }

    @Override
    public List<FraisCarnet> findAllRange() {
        Query query = this.em.createQuery("SELECT f FROM FraisCarnet f ORDER BY f.dateOperation DESC , f.heure DESC");
        return query.getResultList();
    }
}
