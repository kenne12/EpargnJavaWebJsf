/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.boutique;

import entities.Boutique;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import service.BoutiqueService;

/**
 *
 * @author kenne
 */
public class AbstractBoutiqueController {

    @EJB
    protected BoutiqueService boutiqueService;

    protected Boutique boutique = new Boutique();
    protected List<Boutique> boutiques = new ArrayList<>();

    protected String mode;

    protected boolean modifier = true;
    protected boolean supprimer = true;

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        modifier = supprimer == (Objects.isNull(boutique.getIdBoutique()) || boutique == null);
        this.boutique = boutique;
    }

    public List<Boutique> getBoutiques() {
        return boutiques;
    }

    public void setBoutiques(List<Boutique> boutiques) {
        this.boutiques = boutiques;
    }

    public boolean isModifier() {
        return modifier;
    }

    public void setModifier(boolean modifier) {
        this.modifier = modifier;
    }

    public boolean isSupprimer() {
        return supprimer;
    }

    public void setSupprimer(boolean supprimer) {
        this.supprimer = supprimer;
    }

}
