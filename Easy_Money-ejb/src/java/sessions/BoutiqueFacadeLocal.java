/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Boutique;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface BoutiqueFacadeLocal {

    void create(Boutique boutique);

    void edit(Boutique boutique);

    void remove(Boutique boutique);

    Boutique find(Object id);

    List<Boutique> findAll();

    List<Boutique> findRange(int[] range);

    int count();

    Integer nextId();

    List<Boutique> findAllOrderByName();

}
