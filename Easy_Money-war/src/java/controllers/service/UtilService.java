/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.service;

import entities.OperationType;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface UtilService {

    /**
     *
     * @param montant
     * @param operationType
     */
    void updateCaisse(Double montant, OperationType operationType);

    void updateClient(int idClient, Double montant, OperationType operationType);

}
