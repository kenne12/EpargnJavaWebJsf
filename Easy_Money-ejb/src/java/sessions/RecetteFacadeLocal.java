/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Recette;
import enumeration.OperationModeType;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface RecetteFacadeLocal {

    void create(Recette recette);

    void edit(Recette recette);

    void remove(Recette recette);

    Recette find(Object id);

    List<Recette> findAll();

    List<Recette> findRange(int[] range);

    int count();

    public Integer nextId();

    List<Recette> findAll(OperationModeType operationType, Date date);

    List<Recette> findAll(OperationModeType operationType, Date startDate, Date endDate);

}
