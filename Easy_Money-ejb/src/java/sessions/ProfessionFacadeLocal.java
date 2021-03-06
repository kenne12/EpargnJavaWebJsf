package sessions;

import entities.Profession;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ProfessionFacadeLocal {

    void create(Profession paramProfession);

    void edit(Profession paramProfession);

    void remove(Profession paramProfession);

    Profession find(Object paramObject);

    List<Profession> findAll();

    List<Profession> findRange(int[] paramArrayOfint);

    int count();
}
