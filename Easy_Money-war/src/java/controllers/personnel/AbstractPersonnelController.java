/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.personnel;

import entities.Boutique;
import entities.Personnel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import service.BoutiqueService;
import service.PersonnelService;

/**
 *
 * @author kenne
 */
public class AbstractPersonnelController {

    @EJB
    protected PersonnelService personnelService;
    
    @EJB
    protected BoutiqueService boutiqueService;

    protected Personnel personnel;
    protected List<Personnel> personnels;

    protected List<Boutique> boutiques = new ArrayList<>();

    protected String mode;

    protected boolean modifier = true;
    protected boolean supprimer = true;

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        modifier = supprimer = (Objects.isNull(personnel.getIdPersonnel()) || personnel == null);
        this.personnel = personnel;
    }

    public List<Personnel> getPersonnels() {
        return personnels;
    }

    public void setPersonnels(List<Personnel> personnels) {
        this.personnels = personnels;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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
