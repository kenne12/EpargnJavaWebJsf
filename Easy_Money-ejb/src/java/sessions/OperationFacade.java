/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Operation;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author kenne
 */
@Stateless
public class OperationFacade extends AbstractFacade<Operation> implements OperationFacadeLocal {

    @PersistenceContext(unitName = "Easy_Money-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OperationFacade() {
        super(Operation.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(o.idOperation) FROM Operation o");
        try {
            return ((Long) query.getSingleResult() + 1);
        } catch (Exception e) {
            return 1L;
        }
    }

    @Override
    public void deleteById(Long idOperation) {
        em.createQuery("DELETE FROM Operation o WHERE o.idOperation=:idOperation")
                .setParameter("idOperation", idOperation)
                .executeUpdate();
    }

}
