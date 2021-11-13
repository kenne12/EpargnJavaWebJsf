package sessions;

import entities.Annee;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public List<Annee> findByEtat(boolean etat) {
        return this.em.createQuery("SELECT a FROM Annee a WHERE a.etat=:etat ORDER BY a.nom")
                .setParameter("etat", etat)
                .getResultList();

    }

    @Override
    public List<Annee> findAllRangeByNom() {
        return this.em.createQuery("SELECT a FROM Annee a ORDER BY a.nom")
                .getResultList();
    }
}
