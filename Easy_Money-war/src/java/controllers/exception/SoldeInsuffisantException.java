/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.exception;

/**
 *
 * @author kenne
 */
public class SoldeInsuffisantException extends RuntimeException {

    public SoldeInsuffisantException(String message) {
        super(message);
    }

    public SoldeInsuffisantException(Exception ex, String message) {
        super(message, ex);
    }
}
