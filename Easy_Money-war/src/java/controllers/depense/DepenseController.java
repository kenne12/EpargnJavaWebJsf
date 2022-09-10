/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.depense;

import entities.Boutique;
import entities.Personnel;
import entities.Recette;
import enumeration.OperationModeType;
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
@Named(value = "depenseController")
@SessionScoped
public class DepenseController extends AbstractDepenseController implements Serializable {

    /**
     * Creates a new instance of VersementRecetteController
     */
    public DepenseController() {
    }

    @PostConstruct
    private void init() {
        this.setRecette(new Recette(new Boutique(0), new Personnel(0), OperationModeType.DEPENSE));
        this.loadRecette();
        this.loadBoutique();
    }

    public void reload() {
        this.init();
        this.loadBoutique();
    }

    private void loadRecette() {
        this.setRecettes(depenseService
                .findOperationBetweenTwoDates(SessionMBean.getMois().getDateDebut(), SessionMBean.getMois().getDateFin()));
    }

    private void loadBoutique() {
        this.setBoutiques(this.boutiqueService.findAll());
    }

    public void prepareCreate() {
        this.mode = "Create";
        this.recette = new Recette(new Boutique(0), new Personnel(0), OperationModeType.DEPENSE);
        this.recette.setDateOperation(SessionMBean.getDate());
        this.setPersonnels(new ArrayList<>());
        RequestContext.getCurrentInstance().execute("PF('DepenseCreerDialog').show()");
    }

    public void prepareEdit() {
        if (recette.getIdRecette() != null || Objects.nonNull(recette)) {
            this.mode = "Edit";
            if(recette.getPersonnel()==null)
                recette.setPersonnel(new Personnel(0));
            if(recette.getBoutique()==null)
                recette.setBoutique(new Boutique(0));
            this.setPersonnels(new ArrayList<>());
            this.personnels.add(recette.getPersonnel());
            RequestContext.getCurrentInstance().execute("PF('DepenseCreerDialog').show()");
        }
    }

    public void save() {
        try {
            switch (this.mode) {
                case "Create":
                    this.depenseService.save(recette);
                    this.setRecette(new Recette(new Boutique(0), new Personnel(0), OperationModeType.DEPENSE));
                    RequestContext.getCurrentInstance().execute("PF('DepenseCreerDialog').hide()");
                    break;
                case "Edit":
                    depenseService.edit(recette);
                    this.setRecette(new Recette(new Boutique(0), new Personnel(0), OperationModeType.DEPENSE));
                    RequestContext.getCurrentInstance().execute("PF('DepenseCreerDialog').hide()");
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
            depenseService.delete(recette.getIdRecette());
            this.setRecette(new Recette(new Boutique(0), new Personnel(0), OperationModeType.DEPENSE));
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

    private Integer sumTotalDepense() {
        if (this.getRecettes().isEmpty()) {
            return 0;
        }
        return this.getRecettes().stream().mapToInt(Recette::getMontant).sum();
    }

    public String getformatTotalDepense() {
        return JsfUtil.formaterStringMoney(this.sumTotalDepense());
    }
    
    

}
