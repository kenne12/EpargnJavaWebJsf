package controllers.versement;

import entities.Annee;
import entities.AnneeMois;
import entities.Client;
import entities.Versement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import sessions.AnneeFacadeLocal;
import sessions.AnneeMoisFacadeLocal;
import sessions.ClientFacadeLocal;
import sessions.MouchardFacadeLocal;
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

    @EJB
    protected AnneeFacadeLocal anneeFacadeLocal;
    protected Annee annee = SessionMBean.getAnnee();
    protected Annee anneeSearch = SessionMBean.getAnnee();
    protected List<Annee> annees = new ArrayList<>();

    @EJB
    protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;
    protected AnneeMois anneeMois = SessionMBean.getMois();
    protected List<AnneeMois> anneeMoises = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    protected String searchMode = "";
    protected Date searchDate;
    protected AnneeMois searchMois = SessionMBean.getMois();

    protected Integer versement1 = 0;

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

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

    public List<Annee> getAnnees() {
        annees = anneeFacadeLocal.findAllRangeByNom();
        return annees;
    }

    public Annee getAnneeSearch() {
        return anneeSearch;
    }

    public void setAnneeSearch(Annee anneeSearch) {
        this.anneeSearch = anneeSearch;
    }

}
