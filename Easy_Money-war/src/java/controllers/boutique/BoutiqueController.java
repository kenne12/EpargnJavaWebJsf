/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.boutique;

import entities.Boutique;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;

/**
 *
 * @author kenne
 */
@Named(value = "boutiqueController")
@SessionScoped
public class BoutiqueController extends AbstractBoutiqueController implements Serializable {

    /**
     * Creates a new instance of BoutiqueController
     */
    public BoutiqueController() {
    }

    @PostConstruct
    private void init() {
        this.setBoutiques(boutiqueService.findAll());
    }

    public void reload() {
        this.init();
    }

    public void prepareCreate() {
        this.boutique = new Boutique();
        this.mode = "Create";
        RequestContext.getCurrentInstance().execute("PF('BoutiqueCreerDialog').show()");
    }

    public void prepareEdit() {
        if (boutique.getIdBoutique() != null) {
            this.mode = "Edit";
            RequestContext.getCurrentInstance().execute("PF('BoutiqueCreerDialog').show()");
        }
    }

    public void save() {
        try {
            switch (this.mode) {
                case "Create":
                    this.boutiqueService.saveBoutique(boutique);
                    this.setBoutique(new Boutique());
                    RequestContext.getCurrentInstance().execute("PF('BoutiqueCreerDialog').hide()");
                    break;
                case "Edit":
                    boutiqueService.editBoutique(boutique);
                    this.setBoutique(new Boutique());
                    RequestContext.getCurrentInstance().execute("PF('BoutiqueCreerDialog').hide()");
                    break;
                default:
                    JsfUtil.addErrorMessage("Operation non supportée");
                    break;
            }
            this.init();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "");
        }
    }

    public void delete() {
        try {
            boutiqueService.deleteBoutique(boutique.getIdBoutique());
            this.setBoutique(new Boutique());
            this.init();
            JsfUtil.addSuccessMessage("Operation passed with success");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Operation passed with success");
        }
    }
}
