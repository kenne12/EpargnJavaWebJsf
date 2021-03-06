package controllers.client;

import entities.Client;
import entities.Profession;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.CaisseFacadeLocal;
import sessions.ClientFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.PrivilegeFacadeLocal;
import sessions.ProfessionFacadeLocal;
import sessions.RetraitFacadeLocal;
import sessions.VersementFacadeLocal;

public class AbstractClientController {

    @EJB
    protected ClientFacadeLocal clientFacadeLocal;
    protected Client client;
    /*  30 */    protected List<Client> clients = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    @EJB
    protected ProfessionFacadeLocal professionFacadeLocal;
    /*  37 */    protected List<Profession> professions = new ArrayList<>();

    @EJB
    protected VersementFacadeLocal versementFacadeLocal;

    @EJB
    protected CaisseFacadeLocal caisseFacadeLocal;

    @EJB
    protected RetraitFacadeLocal retraitFacadeLocal;

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;

    /*  51 */    protected String fileName = "";

    /*  53 */    protected Boolean detail = Boolean.valueOf(true);
    /*  54 */    protected Boolean modifier = Boolean.valueOf(true);
    /*  55 */    protected Boolean consulter = Boolean.valueOf(true);
    /*  56 */    protected Boolean imprimer = Boolean.valueOf(true);
    /*  57 */    protected Boolean supprimer = Boolean.valueOf(true);

    /*  59 */    protected Boolean showEditSolde = Boolean.valueOf(true);

    protected boolean showClientCreateDialog = false;
    protected boolean showClientDeleteDialog = false;
    /*  63 */    protected Boolean showClientPrintDialog = Boolean.valueOf(false);

    protected boolean showMontantCarnet = true;

    protected boolean showMontantCarnetCompnent = true;
    /*  68 */    protected int carnet = 500;

    /*  70 */    protected String mode = "";

    public Client getClient() {
        /*  73 */ return this.client;
    }

    public void setClient(Client client) {
        /*  77 */ this.modifier = this.supprimer = this.detail = Boolean.valueOf((client == null));
        /*  78 */ this.client = client;
    }

    public List<Client> getClients() {
        /*  82 */ this.clients = this.clientFacadeLocal.findAll1();
        /*  83 */ return this.clients;
    }

    public void setClients(List<Client> clients) {
        /*  87 */ this.clients = clients;
    }

    public Boolean getDetail() {
        /*  91 */ return this.detail;
    }

    public void setDetail(Boolean detail) {
        /*  95 */ this.detail = detail;
    }

    public Boolean getModifier() {
        /*  99 */ return this.modifier;
    }

    public void setModifier(Boolean modifier) {
        /* 103 */ this.modifier = modifier;
    }

    public Boolean getConsulter() {
        /* 107 */ return this.consulter;
    }

    public void setConsulter(Boolean consulter) {
        /* 111 */ this.consulter = consulter;
    }

    public Boolean getImprimer() {
        /* 115 */ this.imprimer = Boolean.valueOf(this.clientFacadeLocal.findAllRange().isEmpty());
        /* 116 */ return this.imprimer;
    }

    public void setImprimer(Boolean imprimer) {
        /* 120 */ this.imprimer = imprimer;
    }

    public List<Profession> getProfessions() {
        /* 124 */ this.professions = this.professionFacadeLocal.findAll();
        /* 125 */ return this.professions;
    }

    public void setProfessions(List<Profession> professions) {
        /* 129 */ this.professions = professions;
    }

    public Boolean getSupprimer() {
        /* 133 */ return this.supprimer;
    }

    public void setSupprimer(Boolean supprimer) {
        /* 137 */ this.supprimer = supprimer;
    }

    public Boolean getShowEditSolde() {
        /* 141 */ return this.showEditSolde;
    }

    public void setShowEditSolde(Boolean showEditSolde) {
        /* 145 */ this.showEditSolde = showEditSolde;
    }

    public boolean isShowClientCreateDialog() {
        /* 149 */ return this.showClientCreateDialog;
    }

    public void setShowClientCreateDialog(boolean showClientCreateDialog) {
        /* 153 */ this.showClientCreateDialog = showClientCreateDialog;
    }

    public boolean isShowClientDeleteDialog() {
        /* 157 */ return this.showClientDeleteDialog;
    }

    public void setShowClientDeleteDialog(boolean showClientDeleteDialog) {
        /* 161 */ this.showClientDeleteDialog = showClientDeleteDialog;
    }

    public Boolean getShowClientPrintDialog() {
        /* 165 */ return this.showClientPrintDialog;
    }

    public void setShowClientPrintDialog(Boolean showClientPrintDialog) {
        /* 169 */ this.showClientPrintDialog = showClientPrintDialog;
    }

    public String getFileName() {
        /* 173 */ return this.fileName;
    }

    public void setFileName(String fileName) {
        /* 177 */ this.fileName = fileName;
    }

    public int getCarnet() {
        /* 181 */ return this.carnet;
    }

    public void setCarnet(int carnet) {
        /* 185 */ this.carnet = carnet;
    }

    public boolean isShowMontantCarnet() {
        /* 189 */ return this.showMontantCarnet;
    }

    public void setShowMontantCarnet(boolean showMontantCarnet) {
        /* 193 */ this.showMontantCarnet = showMontantCarnet;
    }

    public boolean isShowMontantCarnetCompnent() {
        /* 197 */ return this.showMontantCarnetCompnent;
    }

    public void setShowMontantCarnetCompnent(boolean showMontantCarnetCompnent) {
        /* 201 */ this.showMontantCarnetCompnent = showMontantCarnetCompnent;
    }
}
