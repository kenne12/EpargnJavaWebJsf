package controllers.retraitcn;

import entities.Annee;
import entities.AnneeMois;
import entities.Client;
import entities.Retrait;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import sessions.AnneeFacadeLocal;
import sessions.AnneeMoisFacadeLocal;
import sessions.ClientFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.RetraitFacadeLocal;
import utils.SessionMBean;

public class AbstractRetraitCnController {

    @EJB
    protected RetraitFacadeLocal retraitFacadeLocal;
    protected Retrait retrait = new Retrait();
    protected List<Retrait> retraits = new ArrayList<>();

    @EJB
    protected ClientFacadeLocal clientFacadeLocal;
    protected Client client = new Client();
    protected List<Client> clients = new ArrayList<>();

    @EJB
    protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;
    protected AnneeMois anneeMois = new AnneeMois();
    protected List<AnneeMois> anneeMoises = new ArrayList<>();

    @EJB
    protected AnneeFacadeLocal anneeFacadeLocal;
    protected Annee annee = SessionMBean.getAnnee();
    protected Annee anneeSearch = SessionMBean.getAnnee();
    protected List<Annee> annees = new ArrayList<>();

    protected String searchMode = "";
    protected Date searchDate;
    protected AnneeMois searchMois = SessionMBean.getMois();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    protected Integer retrait1 = 0;
    protected Integer commission = 0;

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

    public Boolean getImprimer() {
        this.imprimer = retraits.isEmpty();
        return this.imprimer;
    }

    public Boolean getSupprimer() {
        return this.supprimer;
    }

    public Integer getRetrait1() {
        return this.retrait1;
    }

    public void setRetrait1(Integer retrait1) {
        this.retrait1 = retrait1;
    }

    public Boolean getShowClient() {
        return this.showClient;
    }

    public Retrait getRetrait() {
        return this.retrait;
    }

    public void setRetrait(Retrait retrait) {
        this.modifier = this.supprimer = this.detail = (retrait == null);
        this.retrait = retrait;
    }

    public List<Retrait> getRetraits() {
        return this.retraits;
    }

    public Integer getCommission() {
        return this.commission;
    }

    public void setCommission(Integer commission) {
        this.commission = commission;
    }

    public List<AnneeMois> getAnneeMoises() {
        try {
            this.anneeMoises = this.anneeMoisFacadeLocal.findByAnnee(SessionMBean.getAnnee().getIdannee());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public Annee getAnneeSearch() {
        return anneeSearch;
    }

    public void setAnneeSearch(Annee anneeSearch) {
        this.anneeSearch = anneeSearch;
    }

    public List<Annee> getAnnees() {
        annees = anneeFacadeLocal.findAllRangeByNom();
        return annees;
    }

}
