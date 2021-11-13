package controllers.rapportperiodique;

import entities.Annee;
import entities.AnneeMois;
import entities.Client;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import sessions.AnneeFacadeLocal;
import sessions.AnneeMoisFacadeLocal;
import sessions.ClientFacadeLocal;
import sessions.FraisCarnetFacadeLocal;
import sessions.PrivilegeFacadeLocal;
import sessions.RetraitFacadeLocal;
import sessions.VersementFacadeLocal;
import utils.Solde;

public class AbstractRapportPeriodiqueController {

    @EJB
    protected AnneeFacadeLocal anneeFacadeLocal;
    protected Annee annee = new Annee();
    protected List<Annee> annees = new ArrayList<>();

    @EJB
    protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;
    protected AnneeMois anneeMois = new AnneeMois();
    protected List<AnneeMois> anneeMoises = new ArrayList<>();

    @EJB
    protected VersementFacadeLocal versementFacadeLocal;

    @EJB
    protected RetraitFacadeLocal retraitFacadeLocal;

    @EJB
    protected FraisCarnetFacadeLocal fraisCarnetFacadeLocal;

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;

    @EJB
    protected ClientFacadeLocal clientFacadeLocal;
    List<Client> clients = new ArrayList<>();

    protected List<Solde> soldes = new ArrayList<>();

    protected Date date = new Date();
    protected Date date1 = new Date();

    protected String fileName = "";

    protected boolean showPrintButton = true;
    protected boolean showReportPrintDialog = false;

    public List<Solde> getSoldes() {
        return this.soldes;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public List<Annee> getAnnees() {
        try {
            this.annees = this.anneeFacadeLocal.findByEtat(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.annees;
    }

    public AnneeMois getAnneeMois() {
        return this.anneeMois;
    }

    public void setAnneeMois(AnneeMois anneeMois) {
        this.anneeMois = anneeMois;
    }

    public List<AnneeMois> getAnneeMoises() {
        return this.anneeMoises;
    }

    public Annee getAnnee() {
        return this.annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public Date getDate1() {
        return this.date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }
}
