package controllers.rapportjournalier;

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

public class AbstractRapportJournalierController {

    @EJB
    protected AnneeFacadeLocal anneeFacadeLocal;
    /*  32 */    protected Annee annee = new Annee();
    /*  33 */    protected List<Annee> annees = new ArrayList<>();

    @EJB
    protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;
    /*  37 */    protected AnneeMois anneeMois = new AnneeMois();
    /*  38 */    protected List<AnneeMois> anneeMoises = new ArrayList<>();

    @EJB
    protected VersementFacadeLocal versementFacadeLocal;

    @EJB
    protected FraisCarnetFacadeLocal fraisCarnetFacadeLocal;

    @EJB
    protected RetraitFacadeLocal retraitFacadeLocal;

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;

    @EJB
    protected ClientFacadeLocal clientFacadeLocal;
    /*  54 */    List<Client> clients = new ArrayList<>();

    /*  56 */    protected List<Solde> soldes = new ArrayList<>();

    /*  58 */    Date date = new Date();

    /*  60 */    protected String fileName = "";

    protected boolean showPrintButton = true;
    protected boolean showReportPrintDialog = false;

    public List<Solde> getSoldes() {
        /*  66 */ return this.soldes;
    }

    public void setSoldes(List<Solde> soldes) {
        /*  70 */ this.soldes = soldes;
    }

    public Date getDate() {
        /*  74 */ return this.date;
    }

    public void setDate(Date date) {
        /*  78 */ this.date = date;
    }

    public boolean isShowPrintButton() {
        /*  82 */ return this.showPrintButton;
    }

    public void setShowPrintButton(boolean showPrintButton) {
        /*  86 */ this.showPrintButton = showPrintButton;
    }

    public String getFileName() {
        /*  90 */ return this.fileName;
    }

    public void setFileName(String fileName) {
        /*  94 */ this.fileName = fileName;
    }

    public boolean isShowReportPrintDialog() {
        /*  98 */ return this.showReportPrintDialog;
    }

    public void setShowReportPrintDialog(boolean showReportPrintDialog) {
        /* 102 */ this.showReportPrintDialog = showReportPrintDialog;
    }

    public List<Annee> getAnnees() {
        try {
            /* 107 */ this.annees = this.anneeFacadeLocal.findByEtat(true);
            /* 108 */        } catch (Exception e) {
            /* 109 */ e.printStackTrace();
        }
        /* 111 */ return this.annees;
    }

    public void setAnnees(List<Annee> annees) {
        /* 115 */ this.annees = annees;
    }

    public AnneeMois getAnneeMois() {
        /* 119 */ return this.anneeMois;
    }

    public void setAnneeMois(AnneeMois anneeMois) {
        /* 123 */ this.anneeMois = anneeMois;
    }

    public List<AnneeMois> getAnneeMoises() {
        /* 127 */ return this.anneeMoises;
    }

    public void setAnneeMoises(List<AnneeMois> anneeMoises) {
        /* 131 */ this.anneeMoises = anneeMoises;
    }

    public Annee getAnnee() {
        /* 135 */ return this.annee;
    }

    public void setAnnee(Annee annee) {
        /* 139 */ this.annee = annee;
    }
}
