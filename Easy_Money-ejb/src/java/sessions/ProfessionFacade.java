package sessions;

import entities.Profession;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProfessionFacade extends AbstractFacade<Profession> implements ProfessionFacadeLocal {

    @PersistenceContext(unitName = "Easy_Money-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public ProfessionFacade() {
        super(Profession.class);
    }
}
