/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.impl;

import entities.Personnel;
import exception.EntityNotFoundException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import service.PersonnelService;
import sessions.PersonnelFacadeLocal;

@Stateless
public class PersonnelServiceImpl implements PersonnelService {
    
    @EJB
    private PersonnelFacadeLocal personnelFacadeLocal;
    
    @Override
    public Personnel savePersonnel(Personnel personnel) throws RuntimeException {
        personnel.setIdPersonnel(personnelFacadeLocal.nextId());
        personnelFacadeLocal.create(personnel);
        return null;
    }
    
    @Override
    public Personnel editPersonnel(Personnel personnel) {
        
        Personnel old = personnelFacadeLocal.find(personnel.getIdPersonnel());
        if (old == null) {
            throw new EntityNotFoundException("Person not found with id : " + personnel.getIdPersonnel());
        }
        
        if (personnel.getNom() != null) {
            old.setNom(personnel.getNom());
        }
        
        if (personnel.getPrenom() != null) {
            old.setPrenom(personnel.getPrenom());
        }
        personnelFacadeLocal.edit(old);
        return old;
    }
    
    @Override
    public void deletePersonnel(Integer idPersonnel) {
        Personnel personnel = personnelFacadeLocal.find(idPersonnel);
        if (personnel == null) {
            throw new EntityNotFoundException("Person not found with id : " + idPersonnel);
        }
        
        personnelFacadeLocal.remove(personnel);
    }
    
    @Override
    public List<Personnel> findAll() {
        return personnelFacadeLocal.findAllOrderByName();
    }
    
}
