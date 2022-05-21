/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.versement_recette;

import entities.Boutique;
import entities.Personnel;
import entities.Recette;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import service.BoutiqueService;
import service.PersonnelService;
import service.RecetteService;

/**
 *
 * @author kenne
 */
public class AbstractVersementRecetteController {

    @EJB
    protected RecetteService recetteService;

    @EJB
    protected BoutiqueService boutiqueService;

    @EJB
    protected PersonnelService personnelService;

    protected List<Personnel> personnels;

    protected List<Boutique> boutiques;

    protected Recette recette;
    protected List<Recette> recettes;

    protected String mode;

    protected boolean modifier = true;
    protected boolean supprimer = true;

    public Recette getRecette() {
        return recette;
    }

    public void setRecette(Recette recette) {
        modifier = supprimer = (Objects.isNull(recette.getIdRecette()) || Objects.isNull(recette));
        this.recette = recette;
    }

    public List<Recette> getRecettes() {
        return recettes;
    }

    public void setRecettes(List<Recette> recettes) {
        this.recettes = recettes;
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
