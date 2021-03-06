package controllers.versement;

import entities.AnneeMois;
import entities.Client;
import entities.Versement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.AnneeMoisFacadeLocal;
import sessions.CaisseFacadeLocal;
import sessions.ClientFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.PrivilegeFacadeLocal;
import sessions.VersementFacadeLocal;

public class AbstractVersementController {

    @EJB
    protected VersementFacadeLocal versementFacadeLocal;
    /*  29 */    protected Versement versement = new Versement();
    /*  30 */    protected List<Versement> versements = new ArrayList<>();

    @EJB
    protected ClientFacadeLocal clientFacadeLocal;
    /*  34 */    protected Client client = new Client();
    /*  35 */    protected List<Client> clients = new ArrayList<>();

    @EJB
    protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;
    /*  39 */    protected AnneeMois anneeMois = new AnneeMois();
    /*  40 */    protected List<AnneeMois> anneeMoises = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    @EJB
    protected CaisseFacadeLocal caisseFacadeLocal;

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;

    /*  51 */    protected Integer versement1 = (0);

    /*  53 */    protected Boolean detail = (true);
    /*  54 */    protected Boolean modifier = (true);
    /*  55 */    protected Boolean consulter = (true);
    /*  56 */    protected Boolean imprimer = (true);
    /*  57 */    protected Boolean supprimer = (true);

    /*  59 */    protected Boolean showVersementCreateDialog = (false);
    /*  60 */    protected Boolean showVersementDetailDialog = (false);
    /*  61 */    protected Boolean showVersementDeleteDialog = (false);
    /*  62 */    protected Boolean showVersementEditDialog = (false);
    /*  63 */    protected Boolean showVersementPrintDialog = (false);

    /*  65 */    protected Boolean showClient = (true);

    /*  67 */    protected String mode = "";

    public Client getClient() {
        /*  70 */ return this.client;
    }

    public void setClient(Client client) {
        /*  74 */ this.client = client;
    }

    public List<Client> getClients() {
        /*  78 */ this.clients = this.clientFacadeLocal.findAllRange(true);
        /*  79 */ return this.clients;
    }

    public void setClients(List<Client> clients) {
        /*  83 */ this.clients = clients;
    }

    public Boolean getDetail() {
        /*  87 */ return this.detail;
    }

    public void setDetail(Boolean detail) {
        /*  91 */ this.detail = detail;
    }

    public Boolean getModifier() {
        /*  95 */ return this.modifier;
    }

    public void setModifier(Boolean modifier) {
        /*  99 */ this.modifier = modifier;
    }

    public Boolean getConsulter() {
        /* 103 */ return this.consulter;
    }

    public void setConsulter(Boolean consulter) {
        /* 107 */ this.consulter = consulter;
    }

    public Boolean getImprimer() {
        /* 111 */ this.imprimer = Boolean.valueOf(this.versementFacadeLocal.findAll().isEmpty());
        /* 112 */ return this.imprimer;
    }

    public void setImprimer(Boolean imprimer) {
        /* 116 */ this.imprimer = imprimer;
    }

    public Boolean getSupprimer() {
        /* 120 */ return this.supprimer;
    }

    public void setSupprimer(Boolean supprimer) {
        /* 124 */ this.supprimer = supprimer;
    }

    public Versement getVersement() {
        /* 128 */ return this.versement;
    }

    public void setVersement(Versement versement) {
        /* 132 */ this.supprimer = this.modifier = this.detail = this.consulter = Boolean.valueOf((versement == null));
        /* 133 */ this.versement = versement;
    }

    public List<Versement> getVersements() {
        /* 137 */ this.versements = this.versementFacadeLocal.findAllRange();
        /* 138 */ return this.versements;
    }

    public void setVersements(List<Versement> versements) {
        /* 142 */ this.versements = versements;
    }

    public int getVersement1() {
        /* 146 */ return this.versement1.intValue();
    }

    public void setVersement1(int versement1) {
        /* 150 */ this.versement1 = Integer.valueOf(versement1);
    }

    public Boolean getShowClient() {
        /* 154 */ return this.showClient;
    }

    public void setShowClient(Boolean showClient) {
        /* 158 */ this.showClient = showClient;
    }

    public Boolean getShowVersementCreateDialog() {
        /* 162 */ return this.showVersementCreateDialog;
    }

    public void setShowVersementCreateDialog(Boolean showVersementCreateDialog) {
        /* 166 */ this.showVersementCreateDialog = showVersementCreateDialog;
    }

    public Boolean getShowVersementDetailDialog() {
        /* 170 */ return this.showVersementDetailDialog;
    }

    public void setShowVersementDetailDialog(Boolean showVersementDetailDialog) {
        /* 174 */ this.showVersementDetailDialog = showVersementDetailDialog;
    }

    public Boolean getShowVersementDeleteDialog() {
        /* 178 */ return this.showVersementDeleteDialog;
    }

    public void setShowVersementDeleteDialog(Boolean showVersementDeleteDialog) {
        /* 182 */ this.showVersementDeleteDialog = showVersementDeleteDialog;
    }

    public Boolean getShowVersementEditDialog() {
        /* 186 */ return this.showVersementEditDialog;
    }

    public void setShowVersementEditDialog(Boolean showVersementEditDialog) {
        /* 190 */ this.showVersementEditDialog = showVersementEditDialog;
    }

    public Boolean getShowVersementPrintDialog() {
        /* 194 */ return this.showVersementPrintDialog;
    }

    public void setShowVersementPrintDialog(Boolean showVersementPrintDialog) {
        /* 198 */ this.showVersementPrintDialog = showVersementPrintDialog;
    }

    public List<AnneeMois> getAnneeMoises() {
        try {
            this.anneeMoises = this.anneeMoisFacadeLocal.findByEtat((true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* 207 */ return this.anneeMoises;
    }

    public void setAnneeMoises(List<AnneeMois> anneeMoises) {
        /* 211 */ this.anneeMoises = anneeMoises;
    }

    public AnneeMois getAnneeMois() {
        /* 215 */ return this.anneeMois;
    }

    public void setAnneeMois(AnneeMois anneeMois) {
        /* 219 */ this.anneeMois = anneeMois;
    }
}
