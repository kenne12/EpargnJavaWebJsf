package sessions;

import entities.Utilisateur;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UtilisateurFacadeLocal {

    void create(Utilisateur utilisateur);

    void edit(Utilisateur utilisateur);

    void remove(Utilisateur utilisateur);

    Utilisateur find(Object id);

    List<Utilisateur> findAll();

    List<Utilisateur> findRange(int[] range);

    int count();

    Integer nextVal();

    Utilisateur login(String paramString1, String paramString2) throws Exception;

    List<Utilisateur> findByActif(Boolean paramBoolean);
}
