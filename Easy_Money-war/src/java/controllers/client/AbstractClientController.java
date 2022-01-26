package controllers.client;

import entities.Client;
import entities.Profession;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.CaisseFacadeLocal;
import sessions.ClientFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.ProfessionFacadeLocal;
import sessions.RetraitFacadeLocal;
import sessions.VersementFacadeLocal;

public class AbstractClientController {

    @EJB
    protected ClientFacadeLocal clientFacadeLocal;
    protected Client client = new Client();
    protected List<Client> clients = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    @EJB
    protected ProfessionFacadeLocal professionFacadeLocal;
    protected List<Profession> professions = new ArrayList<>();

    @EJB
    protected VersementFacadeLocal versementFacadeLocal;

    @EJB
    protected CaisseFacadeLocal caisseFacadeLocal;

    @EJB
    protected RetraitFacadeLocal retraitFacadeLocal;

    protected String fileName = "";

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected boolean showMontantCarnetCompnent = false;

    protected Boolean showEditSolde = true;

    protected boolean showMontantCarnet = true;

    protected int carnet = 500;

    protected String mode = "";

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.modifier = this.supprimer = this.detail = (client == null);
        this.client = client;
    }

    public List<Client> getClients() {
        this.clients = this.clientFacadeLocal.findAll1();
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
        this.imprimer = this.clientFacadeLocal.findAllRange().isEmpty();
        return this.imprimer;
    }

    public List<Profession> getProfessions() {
        this.professions = this.professionFacadeLocal.findAll();
        return this.professions;
    }

    public Boolean getSupprimer() {
        return this.supprimer;
    }

    public Boolean getShowEditSolde() {
        return this.showEditSolde;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getCarnet() {
        return this.carnet;
    }

    public boolean isShowMontantCarnet() {
        return showMontantCarnet;
    }

    public void setShowMontantCarnet(boolean showMontantCarnet) {
        this.showMontantCarnet = showMontantCarnet;
    }

    public boolean isShowMontantCarnetCompnent() {
        return showMontantCarnetCompnent;
    }

}
