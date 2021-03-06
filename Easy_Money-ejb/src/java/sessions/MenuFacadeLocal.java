package sessions;

import entities.Menu;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MenuFacadeLocal {

    void create(Menu paramMenu);

    void edit(Menu paramMenu);

    void remove(Menu paramMenu);

    Menu find(Object paramObject);

    List<Menu> findAll();

    List<Menu> findRange(int[] paramArrayOfint);

    int count();
}
