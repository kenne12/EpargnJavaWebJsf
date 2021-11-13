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
        try {
            return ((Long) query.getSingleResult() + 1);
        } catch (Exception e) {
            return 1l;
        }
    }

    @Override
    public List<Versement> find(Client client) {
        Query query = this.em.createQuery("SELECT v FROM Versement v WHERE v.idclient.idclient=:client");
        query.setParameter("client", client.getIdclient());
        return query.getResultList();
    }

    @Override
    public List<Versement> find(Client client, Date datedebut, Date datefin) {
        Query query = this.em.createQuery("SELECT v FROM Versement v WHERE v.idclient.idclient=:client AND v.dateOperation BETWEEN :datedebut AND  :datefin");
        query.setParameter("client", client.getIdclient());
        query.setParameter("datedebut", datedebut).setParameter("datefin", datefin);
        return query.getResultList();
    }

    @Override
    public List<Versement> findByTwoDates(Date datedebut, Date datefin) {
        return this.em.createQuery("SELECT v FROM Versement v WHERE v.dateOperation BETWEEN :datedebut AND  :datefin ORDER BY v.dateOperation")
                .setParameter("datedebut", datedebut).setParameter("datefin", datefin)
                .getResultList();
    }

    @Override
    public List<Versement> find(Client client, Date date) {
        Query query = this.em.createQuery("SELECT v FROM Versement v WHERE v.idclient.idclient=:client AND v.dateOperation=:date");
        query.setParameter("client", client.getIdclient());
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public List<Versement> find(Client client, AnneeMois anneeMois) throws Exception {
        Query query = this.em.createQuery("SELECT v FROM Versement v WHERE v.idclient.idclient=:client AND v.idmois.idAnneeMois =:mois ORDER BY v.dateOperation");
        query.setParameter("client", client.getIdclient());
        query.setParameter("mois", anneeMois.getIdAnneeMois());
        return query.getResultList();
    }

    @Override
    public List<Versement> findAllRange() {
        Query query = this.em.createQuery("SELECT v FROM Versement v ORDER BY v.dateOperation DESC, v.heure");
        return query.getResultList();
    }

    @Override
    public List<Versement> findByIdMois(int idMois) throws Exception {
        Query query = this.em.createQuery("SELECT v FROM Versement v WHERE v.idmois.idAnneeMois=:idMois ORDER BY v.dateOperation");
        query.setParameter("idMois", idMois);
        return query.getResultList();
    }

    @Override
    public List<Versement> findByDate(Date date) throws Exception {
        Query query = this.em.createQuery("SELECT v FROM Versement v WHERE v.dateOperation=:date ORDER BY v.idversement DESC");
        query.setParameter("date", date);
        return query.getResultList();
    }
}
