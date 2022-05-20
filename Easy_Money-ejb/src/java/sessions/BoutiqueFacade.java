/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Boutique;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author kenne
 */
@Stateless
public class BoutiqueFacade extends AbstractFacade<Boutique> implements BoutiqueFacadeLocal {

    @PersistenceContext(unitName = "Easy_Money-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BoutiqueFacade() {
        super(Boutique.class);
    }

    @Override
    public Integer nextId() {
        Query query = em.createQuery("SELECT max(b.idBoutique) FROM Boutique b");
        try {
            return (Integer) query.getSingleResult() + 1;
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public List<Boutique> findAllOrderByName() {
        return em.createQuery("SELECT b FROM Boutique b ORDER BY b.nom").getResultList();
    }

}
