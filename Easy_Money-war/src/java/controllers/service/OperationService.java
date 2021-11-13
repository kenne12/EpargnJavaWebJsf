/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.service;

import entities.Operation;
import entities.OperationType;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface OperationService {

    /**
     *
     * @param operation
     * @return Operation
     */
    Operation saveOperation(Operation operation);

    void deleteOperationById(Long id);

    void editOperation(Long idOperation, Double montant, OperationType operationType);

}
