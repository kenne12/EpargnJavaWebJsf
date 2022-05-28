/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Mois;
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
public class MoisFacade extends AbstractFacade<Mois> implements MoisFacadeLocal {

    @PersistenceContext(unitName = "Easy_Money-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MoisFacade() {
        super(Mois.class);
    }
    
    @Override
    public List<Mois> findAllRange() {
        Query query = this.em.createQuery("SELECT m FROM Mois m ORDER BY m.numero");
        return query.getResultList();
    }
    
}
