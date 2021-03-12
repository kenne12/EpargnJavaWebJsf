package controllers.versement;

import entities.Annee;
import entities.AnneeMois;
import entities.Client;
import entities.Versement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import sessions.AnneeMoisFacadeLocal;
import sessions.CaisseFacadeLocal;
import sessions.ClientFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.PrivilegeFacadeLocal;
import sessions.VersementFacadeLocal;
import utils.SessionMBean;

public class AbstractVersementController {

    @EJB
    protected VersementFacadeLocal versementFacadeLocal;
    protected Versement versement = new Versement();
    protected List<Versement> versements = new ArrayList<>();

    @EJB
    protected ClientFacadeLocal clientFacadeLocal;
    protected Client client = new Client();
    protected List<Client> clients = new ArrayList<>();

    protected Annee annee = SessionMBean.getAnnee();

    @EJB
    protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;
    protected AnneeMois anneeMois = SessionMBean.getMois();
    protected List<AnneeMois> anneeMoises = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    @EJB
    protected CaisseFacadeLocal caisseFacadeLocal;

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;

    protected String searchMode = "";
    protected Date searchDate;
    protected AnneeMois searchMois = SessionMBean.getMois();

    protected Integer versement1 = 0;

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected Boolean showVersementCreateDialog = false;
    protected Boolean showVersementDetailDialog = false;
    protected Boolean showVersementDeleteDialog = false;
    protected Boolean showVersementEditDialog = false;
    protected Boolean showVersementPrintDialog = false;

    protected Boolean showClient = true;

    protected String mode = "";

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Client> getClients() {
        this.clients = this.clientFacadeLocal.findAllRange(true);
        return this.clients;
    }

    public Boolean getDetail() {
        return this.detail;
    }

    public Boolean getModifier() {
        return this.modifier;
    }

    public Boolean getConsulter() {
        return this.consulter;
    }

    public Boolean getImprimer() {
        this.imprimer = versements.isEmpty();
        return this.imprimer;
    }

    public Boolean getSupprimer() {
        return this.supprimer;
    }

    public Versement getVersement() {
        return this.versement;
    }

    public void setVersement(Versement versement) {
        this.supprimer = this.modifier = this.detail = this.consulter = (versement == null);
        this.versement = versement;
    }

    public List<Versement> getVersements() {
        return this.versements;
    }

    public int getVersement1() {
        return this.versement1;
    }

    public void setVersement1(int versement1) {
        this.versement1 = (versement1);
    }

    public Boolean getShowClient() {
        return this.showClient;
    }

    public Boolean getShowVersementCreateDialog() {
        return this.showVersementCreateDialog;
    }

    public Boolean getShowVersementDetailDialog() {
        return this.showVersementDetailDialog;
    }

    public Boolean getShowVersementDeleteDialog() {
        return this.showVersementDeleteDialog;
    }

    public Boolean getShowVersementEditDialog() {
        return this.showVersementEditDialog;
    }

    public Boolean getShowVersementPrintDialog() {
        return this.showVersementPrintDialog;
    }

    public List<AnneeMois> getAnneeMoises() {
        this.anneeMoises = this.anneeMoisFacadeLocal.findByAnnee(SessionMBean.getAnnee().getIdannee());
        return this.anneeMoises;
    }

    public AnneeMois getAnneeMois() {
        return this.anneeMois;
    }

    public void setAnneeMois(AnneeMois anneeMois) {
        this.anneeMois = anneeMois;
    }

    public String getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(String searchMode) {
        this.searchMode = searchMode;
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }

    public AnneeMois getSearchMois() {
        return searchMois;
    }

    public void setSearchMois(AnneeMois searchMois) {
        this.searchMois = searchMois;
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

}
