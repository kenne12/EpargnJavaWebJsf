package sessions;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return (T) getEntityManager().find(this.entityClass, id);
    }

    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select((Selection) cq.from(this.entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select((Selection) cq.from(this.entityClass));
        TypedQuery typedQuery = getEntityManager().createQuery(cq);
        typedQuery.setMaxResults(range[1] - range[0] + 1);
        typedQuery.setFirstResult(range[0]);
        return typedQuery.getResultList();
    }

    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(this.entityClass);
        cq.select((Selection) getEntityManager().getCriteriaBuilder().count((Expression) rt));
        TypedQuery typedQuery = getEntityManager().createQuery(cq);
        return ((Long) typedQuery.getSingleResult()).intValue();
    }
}
