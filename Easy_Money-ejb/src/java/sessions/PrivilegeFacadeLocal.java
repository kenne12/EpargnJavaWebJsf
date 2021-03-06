package sessions;

import entities.Privilege;
import java.util.List;
import javax.ejb.Local;

@Local
public interface PrivilegeFacadeLocal {

    void create(Privilege paramPrivilege);

    void edit(Privilege paramPrivilege);

    void remove(Privilege paramPrivilege);

    Privilege find(Object paramObject);

    List<Privilege> findAll();

    List<Privilege> findRange(int[] paramArrayOfint);

    int count();

    List<Privilege> findAll1();

    Long nextVal();

    List<Privilege> findByUser(int paramInt);

    Privilege findByUser(int paramInt1, int paramInt2);

    void delete(int paramInt1, int paramInt2);
}
