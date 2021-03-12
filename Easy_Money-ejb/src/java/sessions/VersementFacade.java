package sessions;

import entities.AnneeMois;
import entities.Client;
import entities.Versement;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class VersementFacade extends AbstractFacade<Versement> implements VersementFacadeLocal {

    @PersistenceContext(unitName = "Easy_Money-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public VersementFacade() {
        super(Versement.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(v.idversement) FROM Versement v");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = (1L);
        } else {
            result = result + 1L;
        }
        return result;
    }

    @Override
    public List<Versement> find(Client client) {
        Query query = this.em.createQuery("SELECT v FROM Versement v WHERE v.idclient.idclient=:client");
        query.setParameter("client", client.getIdclient());
        return query.getResultList();
    }

    @Override
    public List<Versement> find(Client client, Date datedebut, Date datefin) {
        Query query = this.em.createQuery("SELECT v FROM Versement v WHERE v.idclient.idclient=:client AND v.date BETWEEN :datedebut AND  :datefin");
        query.setParameter("client", client.getIdclient());
        query.setParameter("datedebut", datedebut).setParameter("datefin", datefin);
        return query.getResultList();
    }

    @Override
    public List<Versement> find(Client client, Date date) {
        Query query = this.em.createQuery("SELECT v FROM Versement v WHERE v.idclient.idclient=:client AND v.date=:date");
        query.setParameter("client", client.getIdclient());
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public List<Versement> find(Client client, AnneeMois anneeMois) throws Exception {
        Query query = this.em.createQuery("SELECT v FROM Versement v WHERE v.idclient.idclient=:client AND v.idmois.idAnneeMois =:mois ORDER BY v.date");
        query.setParameter("client", client.getIdclient());
        query.setParameter("mois", anneeMois.getIdAnneeMois());
        return query.getResultList();
    }

    @Override
    public List<Versement> findAllRange() {
        Query query = this.em.createQuery("SELECT v FROM Versement v ORDER BY v.date DESC, v.heure");
        return query.getResultList();
    }
    
    @Override
    public List<Versement> findByIdMois(int idMois) throws Exception {
        Query query = this.em.createQuery("SELECT v FROM Versement v WHERE v.idmois.idAnneeMois=:idMois ORDER BY v.date");
        query.setParameter("idMois", idMois);
        return query.getResultList();
    }
    
    @Override
    public List<Versement> findByDate(Date date) throws Exception {
        Query query = this.em.createQuery("SELECT v FROM Versement v WHERE v.date=:date ORDER BY v.idversement DESC");
        query.setParameter("date", date);
        return query.getResultList();
    }
}
