package sessions;

import entities.Annee;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class AnneeFacade extends AbstractFacade<Annee> implements AnneeFacadeLocal {

    @PersistenceContext(unitName = "Easy_Money-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public AnneeFacade() {
        super(Annee.class);
    }

    @Override
    public List<Annee> findByEtat(boolean etat) throws Exception {
        Query query = this.em.createQuery("SELECT a FROM Annee a WHERE a.etat=:etat ORDER BY a.nom");
        query.setParameter("etat", etat);
        return query.getResultList();
    }
}
