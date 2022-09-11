/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.depense.rapport_periodique;

import entities.AnneeMois;
import entities.Recette;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import service.DepenseService;
import sessions.AnneeMoisFacadeLocal;
import sessions.PrivilegeFacadeLocal;
import utils.SessionMBean;

/**
 *
 * @author kenne
 */
public class AbstractRapportDepenseController {

    @EJB
    protected DepenseService depenseService;
    protected List<Recette> recettes = new ArrayList<>();

    @EJB
    protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;
    protected AnneeMois anneeMois = new AnneeMois();
    protected List<AnneeMois> anneeMoises = new ArrayList<>();

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;

    protected Date startDate = new Date();
    protected Date endDate = new Date();

    protected String fileName = "";

    protected boolean showPrintButton = true;
    protected boolean showReportPrintDialog = false;

    public List<Recette> getRecettes() {
        return recettes;
    }

    public void setRecettes(List<Recette> recettes) {
        this.recettes = recettes;
    }

    public boolean isShowPrintButton() {
        return this.showPrintButton;
    }

    public String getFileName() {
        return this.fileName;
    }

    public boolean isShowReportPrintDialog() {
        return this.showReportPrintDialog;
    }

    public void setShowReportPrintDialog(boolean showReportPrintDialog) {
        this.showReportPrintDialog = showReportPrintDialog;
    }

    public AnneeMois getAnneeMois() {
        return this.anneeMois;
    }

    public void setAnneeMois(AnneeMois anneeMois) {
        this.anneeMois = anneeMois;
    }

    public List<AnneeMois> getAnneeMoises() {
        this.anneeMoises = anneeMoisFacadeLocal.findByAnnee(SessionMBean.getAnnee().getIdannee());
        return this.anneeMoises;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
