package controllers.rapporthebdomadaire;

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

public class AbstractRapportHebdomaireController {

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

    Date dateDebut = new Date();
    Date dateFin = new Date();

    protected String fileName = "";

    protected boolean showPrintButton = true;
    protected boolean showReportPrintDialog = false;

    public List<Solde> getSoldes() {
        return this.soldes;
    }

    public void setSoldes(List<Solde> soldes) {
        this.soldes = soldes;
    }

    public Date getDateDebut() {
        return this.dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return this.dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public boolean isShowPrintButton() {
        return this.showPrintButton;
    }

    public void setShowPrintButton(boolean showPrintButton) {
        this.showPrintButton = showPrintButton;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isShowReportPrintDialog() {
        return this.showReportPrintDialog;
    }

    public void setShowReportPrintDialog(boolean showReportPrintDialog) {
        this.showReportPrintDialog = showReportPrintDialog;
    }

    public Annee getAnnee() {
        return this.annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public List<Annee> getAnnees() {
        this.annees = this.anneeFacadeLocal.findByEtat(true);
        return this.annees;
    }

    public void setAnnees(List<Annee> annees) {
        this.annees = annees;
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

    public void setAnneeMoises(List<AnneeMois> anneeMoises) {
        this.anneeMoises = anneeMoises;
    }
}
