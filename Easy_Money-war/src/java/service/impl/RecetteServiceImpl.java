/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.impl;

import entities.Recette;
import enumeration.VersementState;
import exception.EntityNotFoundException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import service.RecetteService;
import sessions.RecetteFacadeLocal;

@Stateless
public class RecetteServiceImpl implements RecetteService {
    
    @EJB
    private RecetteFacadeLocal recetteFacadeLocal;
    
    @Override
    public Recette saveRecette(Recette recette) throws Exception {
        recette.setIdRecette(recetteFacadeLocal.nextId());
        recette.setVersementState(VersementState.ATTENTE);
        recette.setHeureOperation(Date.from(Instant.now()));
        recetteFacadeLocal.create(recette);
        return null;
    }
    
    @Override
    public Recette editRecette(Recette recette) {
        Recette old = recetteFacadeLocal.find(recette.getIdRecette());
        if (old == null) {
            throw new EntityNotFoundException("Recette not found with id : " + recette.getIdRecette());
        }
        
        if (recette.getMontant() != null) {
            old.setMontant(recette.getMontant());
        }
        
        if (recette.getDateOperation() != null) {
            old.setDateOperation(recette.getDateOperation());
        }
        recetteFacadeLocal.edit(old);
        return old;
    }
    
    @Override
    public void deleteRecette(Integer idRecette) {
        Recette r = recetteFacadeLocal.find(idRecette);
        if (r == null) {
            throw new EntityNotFoundException("Recette not found with id : " + idRecette);
        }
        recetteFacadeLocal.remove(r);
    }
    
    @Override
    public List<Recette> findByOperationDate(Date operationDate) {
        return recetteFacadeLocal.findAll(operationDate);
    }
    
    @Override
    public List<Recette> findOperationBetweenTwoDates(Date startDate, Date endDate) {
        return recetteFacadeLocal.findAll(startDate, endDate);
    }
    
}
