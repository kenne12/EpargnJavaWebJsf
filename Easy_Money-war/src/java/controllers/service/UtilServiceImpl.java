/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.service;

import entities.Caisse;
import entities.Client;
import entities.OperationType;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import sessions.CaisseFacadeLocal;
import sessions.ClientFacadeLocal;

/**
 *
 * @author kenne
 */
@Stateless
public class UtilServiceImpl implements UtilService {

    @EJB
    private CaisseFacadeLocal caisseFacadeLocal;

    @EJB
    private ClientFacadeLocal clientFacadeLocal;

    @Override
    public void updateCaisse(Double montant, OperationType operationType) {
        Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
        if (operationType.equals(OperationType.CREDIT)) {
            caisse.setMontant((caisse.getMontant() + montant.intValue()));
        } else {
            caisse.setMontant((caisse.getMontant() - montant.intValue()));
        }
        this.caisseFacadeLocal.edit(caisse);
    }

    @Override
    public void updateClient(int idClient, Double montant, OperationType operationType) {
        Client client = clientFacadeLocal.find(idClient);
        if (operationType.equals(OperationType.CREDIT)) {
            client.setSolde((client.getSolde() + montant.intValue()));
        } else {
            client.setSolde((client.getSolde() - montant.intValue()));
        }
        clientFacadeLocal.edit(client);
    }

}
