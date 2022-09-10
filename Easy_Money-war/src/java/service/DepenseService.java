/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Recette;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface DepenseService {
    Recette save(Recette recette) throws Exception;

    Recette edit(Recette recette);

    void delete(Integer idRecette);

    List<Recette> findByOperationDate(Date operationDate);

    List<Recette> findOperationBetweenTwoDates(Date startDate, Date endDate);
}
