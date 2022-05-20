/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Boutique;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kenne
 */
@Local
public interface BoutiqueService {

    public Boutique saveBoutique(Boutique boutique) throws Exception;

    public Boutique editBoutique(Boutique boutique);

    public void deleteBoutique(Integer idBoutique);

    public List<Boutique> findAll();

}
