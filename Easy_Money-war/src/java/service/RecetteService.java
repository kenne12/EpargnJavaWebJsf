/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Recette;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface RecetteService {

    Recette saveRecette(Recette recette) throws Exception;

    Recette editRecette(Recette recette);

    void deleteRecette(Integer idRecette);

}
