package sessions;

import entities.AnneeMois;
import entities.Client;
import entities.Retrait;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class RetraitFacade extends AbstractFacade<Retrait> implements RetraitFacadeLocal {

    @PersistenceContext(unitName = "Easy_Money-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public RetraitFacade() {
        super(Retrait.class);
    }

    @Override
    public Long nextLongVal() {
        Query query = this.em.createQuery("SELECT MAX(r.idretrait) FROM Retrait r");
        try {
            return (Long) query.getSingleResult() + 1;
        } catch (Exception e) {
            return 1l;
        }
    }

    @Override
    public List<Retrait> find(Client client) {
        Query query = this.em.createQuery("SELECT r FROM Retrait r WHERE r.idclient.idclient =:client");
        query.setParameter("client", client.getIdclient());
        return query.getResultList();
    }

    @Override
    public List<Retrait> find(Client client, Date datedebut, Date datefin) {
        Query query = this.em.createQuery("SELECT r FROM Retrait r WHERE r.idclient.idclient=:client AND r.dateOperation BETWEEN :datedebut AND :datefin");
        query.setParameter("datedebut", datedebut).setParameter("datefin", datefin);
        query.setParameter("client", client.getIdclient());
        return query.getResultList();
    }

    @Override
    public List<Retrait> findByToDates(Date datedebut, Date datefin) {
        return this.em.createQuery("SELECT r FROM Retrait r WHERE r.dateOperation BETWEEN :datedebut AND :datefin ORDER BY r.dateOperation")
                .setParameter("datedebut", datedebut).setParameter("datefin", datefin)
                .getResultList();
    }

    @Override
    public List<Retrait> find(Client client, Date date) {
        Query query = this.em.createQuery("SELECT r FROM Retrait r WHERE r.idclient.idclient=:client AND r.dateOperation=:date");
        query.setParameter("date", date);
        query.setParameter("client", client.getIdclient());
        return query.getResultList();
    }

    @Override
    public List<Retrait> findAllRange(boolean commission) throws Exception {
        Query query = this.em.createQuery("SELECT r FROM Retrait r WHERE r.commissionAuto=:commission ORDER BY r.dateOperation DESC , r.heure DESC");
        query.setParameter("commission", commission);
        return query.getResultList();
    }

    @Override
    public List<Retrait> find(Client client, AnneeMois anneeMois) throws Exception {
        Query query = this.em.createQuery("SELECT r FROM Retrait r WHERE r.idclient.idclient=:client AND r.idmois.idAnneeMois=:mois ORDER BY r.dateOperation");
        query.setParameter("mois", anneeMois.getIdAnneeMois());
        query.setParameter("client", client.getIdclient());
        return query.getResultList();
    }

    @Override
    public List<Retrait> findByIdMois(int idMois) throws Exception {
        Query query = this.em.createQuery("SELECT r FROM Retrait r WHERE r.idmois.idAnneeMois=:mois ORDER BY r.dateOperation");
        query.setParameter("mois", idMois);
        return query.getResultList();
    }

    @Override
    public List<Retrait> findByDate(Date date) throws Exception {
        Query query = this.em.createQuery("SELECT r FROM Retrait r WHERE r.dateOperation =:date ORDER BY  r.idretrait DESC");
        query.setParameter("date", date);
        return query.getResultList();
    }
}
