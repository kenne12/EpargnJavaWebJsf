/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.impl;

import entities.Boutique;
import exception.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import service.BoutiqueService;
import sessions.BoutiqueFacadeLocal;

@Stateless
public class BoutiqueServiceImpl implements BoutiqueService {

    @EJB
    private BoutiqueFacadeLocal bfl;

    @Override
    public Boutique saveBoutique(Boutique boutique) throws Exception {
        boutique.setIdBoutique(bfl.nextId());
        bfl.create(boutique);
        return null;
    }

    @Override
    public Boutique editBoutique(Boutique boutique) {
        if (boutique.getIdBoutique() == null) {
            throw new RuntimeException("Can not edit customer with null id");
        }
        Boutique old;
        try {
            old = bfl.find(boutique.getIdBoutique());
            if (old == null) {
                throw new EntityNotFoundException("Boutique not found with id : " + boutique.getIdBoutique());
            }
        } catch (Exception e) {
            throw new EntityNotFoundException("Boutique not found with id : " + boutique.getIdBoutique());
        }

        if (Objects.nonNull(old.getNom())) {
            old.setNom(boutique.getNom());
        }

        if (Objects.nonNull(boutique.getCode())) {
            old.setCode(boutique.getCode());
        }
        bfl.edit(old);
        return bfl.find(old.getIdBoutique());
    }

    @Override
    public void deleteBoutique(Integer idBoutique) {
        Boutique old;
        try {
            old = bfl.find(idBoutique);
            if (old == null) {
                throw new EntityNotFoundException("Boutique not found with id : " + idBoutique);
            }
            bfl.remove(old);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Boutique not found with id : " + idBoutique);
        }
    }

    @Override
    public List<Boutique> findAll() {
        return this.bfl.findAllOrderByName();
    }

}
