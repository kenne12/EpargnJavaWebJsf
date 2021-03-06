package sessions;

import entities.Privilege;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PrivilegeFacade extends AbstractFacade<Privilege> implements PrivilegeFacadeLocal {

    @PersistenceContext(unitName = "Easy_Money-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        /* 27 */ return this.em;
    }

    public PrivilegeFacade() {
        /* 31 */ super(Privilege.class);
    }

    @Override
    public List<Privilege> findAll1() {
        /* 36 */ List<Privilege> privileges = null;
        /* 37 */ Query query = this.em.createQuery("SELECT p FROM Privilege p ORDER BY p.idutilisateur.nom, p.idutilisateur.prenom,p.idmenu.idmenu DESC");
        /* 38 */ privileges = query.getResultList();
        /* 39 */ return privileges;
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(p.idprivilege) FROM Privilege p");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = (result + 1L);
        }
        return result;
    }

    @Override
    public List<Privilege> findByUser(int utilisateur) {
        /* 56 */ List<Privilege> privileges = null;
        /* 57 */ Query query = this.em.createQuery("SELECT p FROM Privilege p WHERE p.idutilisateur.idutilisateur=:utilisateur");
        /* 58 */ query.setParameter("utilisateur", utilisateur);
        /* 59 */ privileges = query.getResultList();
        /* 60 */ return privileges;
    }

    @Override
    public Privilege findByUser(int utilisateur, int menu) {
        Query query = this.em.createQuery("SELECT p FROM Privilege p WHERE p.idutilisateur.idutilisateur=:utilisateur AND p.idmenu.idmenu=:menu");
        query.setParameter("utilisateur", utilisateur).setParameter("menu", menu);
        List list = query.getResultList();
        if (!list.isEmpty()) {
            return (Privilege) list.get(0);
        }
        return null;
    }

    @Override
    public void delete(int menu, int utilisateur) {
        try {
            Query query = this.em.createQuery("DELETE FROM Privilege p WHERE p.idmenu.idmenu=:menu AND p.idutilisateur.idutilisateur=:utilisateur");
            query.setParameter("menu", menu).setParameter("utilisateur", utilisateur);
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
