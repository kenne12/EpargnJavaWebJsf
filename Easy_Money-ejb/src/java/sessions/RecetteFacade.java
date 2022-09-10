/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Recette;
import enumeration.OperationModeType;
import java.util.Date;
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
public class RecetteFacade extends AbstractFacade<Recette> implements RecetteFacadeLocal {

    @PersistenceContext(unitName = "Easy_Money-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RecetteFacade() {
        super(Recette.class);
    }

    @Override
    public Integer nextId() {
        Query query = em.createQuery("SELECT max(r.idRecette) FROM Recette r");
        try {
            return ((Integer) query.getSingleResult() + 1);
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public List<Recette> findAll(OperationModeType operationType,  Date date) {
        return em.createQuery("SELECT r FROM Recette r WHERE r.operationType=:operationType AND r.dateOperation=:dateOperation ORDER BY r.dateOperation DESC, r.idRecette DESC")
                .setParameter("dateOperation", date)
                .getResultList();
    }

    @Override
    public List<Recette> findAll(OperationModeType operationType, Date startDate, Date endDate) {
        return em.createQuery("SELECT r FROM Recette r WHERE r.operationType=:operationType AND r.dateOperation BETWEEN :startDate AND :endDate ORDER BY r.dateOperation DESC, r.idRecette DESC")
                .setParameter("startDate", startDate).setParameter("endDate", endDate).setParameter("operationType", operationType)
                .getResultList();
    }

}
