/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.versement_recette;

import entities.Boutique;
import entities.Personnel;
import entities.Recette;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.SessionMBean;

/**
 *
 * @author kenne
 */
@Named(value = "versementRecetteController")
@SessionScoped
public class VersementRecetteController extends AbstractVersementRecetteController implements Serializable {

    /**
     * Creates a new instance of VersementRecetteController
     */
    public VersementRecetteController() {
    }

    @PostConstruct
    private void init() {
        this.setRecette(new Recette(new Boutique(0), new Personnel(0)));
        this.loadRecette();
        this.loadBoutique();
    }

    public void reload() {
        this.init();
        this.loadBoutique();
    }

    private void loadRecette() {
        this.setRecettes(recetteService
                .findOperationBetweenTwoDates(SessionMBean.getMois().getDateDebut(), SessionMBean.getMois().getDateFin()));
    }

    private void loadBoutique() {
        this.setBoutiques(this.boutiqueService.findAll());
    }

    public void prepareCreate() {
        this.mode = "Create";
        this.recette = new Recette(new Boutique(0), new Personnel(0));
        this.recette.setDateOperation(SessionMBean.getDate());
        this.setPersonnels(new ArrayList<>());
        RequestContext.getCurrentInstance().execute("PF('RecetteCreerDialog').show()");
    }

    public void prepareEdit() {
        if (recette.getIdRecette() != null || Objects.nonNull(recette)) {
            this.mode = "Edit";
            this.setPersonnels(new ArrayList<>());
            this.personnels.add(recette.getPersonnel());
            RequestContext.getCurrentInstance().execute("PF('RecetteCreerDialog').show()");
        }
    }

    public void save() {
        try {
            switch (this.mode) {
                case "Create":
                    this.recetteService.saveRecette(recette);
                    this.setRecette(new Recette(new Boutique(0), new Personnel(0)));
                    RequestContext.getCurrentInstance().execute("PF('RecetteCreerDialog').hide()");
                    break;
                case "Edit":
                    recetteService.editRecette(recette);
                    this.setRecette(new Recette(new Boutique(0), new Personnel(0)));
                    RequestContext.getCurrentInstance().execute("PF('RecetteCreerDialog').hide()");
                    break;
                default:
                    JsfUtil.addErrorMessage("Operation non supportée");
                    break;
            }
            this.mode = "";
            this.loadRecette();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error while saving person");
        }
    }

    public void delete() {
        try {
            recetteService.deleteRecette(recette.getIdRecette());
            this.setRecette(new Recette(new Boutique(0), new Personnel(0)));
            this.loadRecette();
            JsfUtil.addSuccessMessage("Operation passed with success");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error while saving person");
        }
    }

    public void handleFilterPersonne() {
        this.setPersonnels(new ArrayList<>());
        this.recette.getPersonnel().setIdPersonnel(null);
        if (this.recette.getBoutique().getIdBoutique() != null) {
            this.setPersonnels(this.personnelService.findByBoutiqueId(this.recette.getBoutique().getIdBoutique()));
        }
    }

    private Integer sumTotalRecette() {
        if (this.getRecettes().isEmpty()) {
            return 0;
        }
        return this.getRecettes().stream().mapToInt(Recette::getMontant).sum();
    }

    public String getformatTotalRecette() {
        return JsfUtil.formaterStringMoney(this.sumTotalRecette());
    }

}
