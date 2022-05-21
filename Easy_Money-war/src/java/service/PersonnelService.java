/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Personnel;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface PersonnelService {

    public Personnel savePersonnel(Personnel personnel) throws RuntimeException;

    public Personnel editPersonnel(Personnel personnel);

    public void deletePersonnel(Integer idPersonnel);

    public List<Personnel> findAll();

    public List<Personnel> findByBoutiqueId(int boutiqueId);

}
