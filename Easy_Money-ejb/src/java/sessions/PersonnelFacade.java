/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Personnel;
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
public class PersonnelFacade extends AbstractFacade<Personnel> implements PersonnelFacadeLocal {

    @PersistenceContext(unitName = "Easy_Money-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonnelFacade() {
        super(Personnel.class);
    }

    @Override
    public Integer nextId() {
        Query query = em.createQuery("SELECT max(p.idPersonnel) FROM Personnel p");
        try {
            return ((Integer) query.getSingleResult() + 1);
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public List<Personnel> findAllOrderByName() {
        return em.createQuery("SELECT p from Personnel p ORDER BY p.nom, p.prenom")
                .getResultList();
    }

}
