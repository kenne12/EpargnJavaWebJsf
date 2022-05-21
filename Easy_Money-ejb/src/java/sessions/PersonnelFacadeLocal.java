/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Personnel;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface PersonnelFacadeLocal {

    void create(Personnel personnel);

    void edit(Personnel personnel);

    void remove(Personnel personnel);

    Personnel find(Object id);

    List<Personnel> findAll();

    List<Personnel> findRange(int[] range);

    int count();

    Integer nextId();

    List<Personnel> findAllOrderByName();

    List<Personnel> findByBoutiqueId(int boutiqueId);

}
