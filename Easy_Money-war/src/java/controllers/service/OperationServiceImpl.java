/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.service;

import entities.Client;
import entities.Operation;
import entities.OperationType;
import java.time.Instant;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import sessions.ClientFacadeLocal;
import sessions.OperationFacadeLocal;

/**
 *
 * @author kenne
 */
@Stateless
public class OperationServiceImpl implements OperationService {

    @EJB
    private OperationFacadeLocal operationFacadeLocal;

    @EJB
    private ClientFacadeLocal clientFacadeLocal;

    @Override
    public Operation saveOperation(Operation operation) {
        if (operation.getClient() != null) {
            Double solde = null;
            if (operation.getOperationType().equals(OperationType.CREDIT)) {
                solde = this.updateSolde(operation.getClient().getIdclient(), operation.getOperationType(), operation.getMontant());
            } else {
                solde = this.updateSolde(operation.getClient().getIdclient(), operation.getOperationType(), (operation.getMontant() + operation.getCommission()));
            }
            operation.setIdOperation(operationFacadeLocal.nextVal());
            operation.setHeure(Date.from(Instant.now()));
            operation.setSolde(solde);
            operationFacadeLocal.create(operation);
            return operation;
        }
        return null;
    }

    @Override
    public void deleteOperationById(Long id) {
        Operation operation = operationFacadeLocal.find(id);
        if (operation != null) {
            operationFacadeLocal.deleteById(id);
            if (operation.getOperationType().equals(OperationType.CREDIT)) {
                this.updateSolde(operation.getClient().getIdclient(), OperationType.DEBIT, operation.getMontant());
            } else {
                this.updateSolde(operation.getClient().getIdclient(), OperationType.CREDIT, (operation.getMontant() + operation.getCommission()));
            }
        }
    }

    @Override
    public void editOperation(Long idOperation, Double montant, OperationType operationType) {
        Operation operation = operationFacadeLocal.find(idOperation);
        if (operationType.equals(OperationType.CREDIT)) {
            operation.setMontant(operation.getMontant() + montant);
            operation.setSolde(operation.getSolde() + montant);
        } else {
            operation.setSolde(operation.getSolde() - montant);
            operation.setMontant(operation.getMontant() - montant);
        }
        operationFacadeLocal.edit(operation);
    }

    private Double updateSolde(int idClient, OperationType operationType, Double montant) {
        Client c = clientFacadeLocal.find(idClient);
        if (operationType.equals(OperationType.DEBIT)) {
            c.setSolde(c.getSolde() - montant.intValue());
        } else {
            c.setSolde(c.getSolde() + montant.intValue());
        }
        clientFacadeLocal.edit(c);
        return c.getSolde().doubleValue();
    }

}
