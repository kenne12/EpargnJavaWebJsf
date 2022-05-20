/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.personnel;

import entities.Boutique;
import entities.Personnel;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Objects;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;

/**
 *
 * @author kenne
 */
@Named(value = "personnelController")
@SessionScoped
public class PersonnelController extends AbstractPersonnelController implements Serializable {

    /**
     * Creates a new instance of PersonnelController
     */
    public PersonnelController() {
    }

    @PostConstruct
    private void init() {
        this.setPersonnel(new Personnel(null, new Boutique(0)));
        this.setPersonnels(personnelService.findAll());
        this.loadBoutique();
    }

    public void reload() {
        this.init();
        this.loadBoutique();
    }

    private void loadBoutique() {
        this.setBoutiques(this.boutiqueService.findAll());
    }

    public void prepareCreate() {
        this.personnel = new Personnel(null, new Boutique(0));
        this.mode = "Create";
        RequestContext.getCurrentInstance().execute("PF('PersonnelCreerDialog').show()");
    }

    public void prepareEdit() {
        if (personnel.getIdPersonnel() != null || Objects.nonNull(personnel)) {
            this.mode = "Edit";
            RequestContext.getCurrentInstance().execute("PF('PersonnelCreerDialog').show()");
        }
    }

    public void save() {
        try {
            switch (this.mode) {
                case "Create":
                    this.personnelService.savePersonnel(personnel);
                    this.setPersonnel(new Personnel(null, new Boutique(0)));
                    RequestContext.getCurrentInstance().execute("PF('PersonnelCreerDialog').hide()");
                    break;
                case "Edit":
                    personnelService.editPersonnel(personnel);
                    this.setPersonnel(new Personnel(null, new Boutique(0)));
                    RequestContext.getCurrentInstance().execute("PF('PersonnelCreerDialog').hide()");
                    break;
                default:
                    JsfUtil.addErrorMessage("Operation non supportée");
                    break;
            }
            this.mode = "";
            this.init();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error while saving person");
        }
    }

    public void delete() {
        try {
            personnelService.deletePersonnel(personnel.getIdPersonnel());
            this.setPersonnel(new Personnel(null, new Boutique(0)));
            this.init();
            JsfUtil.addSuccessMessage("Operation passed with success");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error while saving person");
        }
    }

}
