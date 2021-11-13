package controllers.releve_compte;

import entities.Client;
import entities.Retrait;
import entities.Versement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import sessions.ClientFacadeLocal;
import sessions.FraisCarnetFacadeLocal;
import sessions.PrivilegeFacadeLocal;
import sessions.RetraitFacadeLocal;
import sessions.VersementFacadeLocal;

public class AbstractReleveCompteController {

    @EJB
    protected VersementFacadeLocal versementFacadeLocal;
    protected List<Versement> versements = new ArrayList<>();

    @EJB
    protected FraisCarnetFacadeLocal fraisCarnetFacadeLocal;

    @EJB
    protected RetraitFacadeLocal retraitFacadeLocal;
    protected List<Retrait> retraits = new ArrayList<>();

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;

    @EJB
    protected ClientFacadeLocal clientFacadeLocal;
    protected Client client = new Client();
    protected List<Client> clients = new ArrayList<>();

    protected Integer sommeVersement = 0;
    protected Integer sommeRetrait = 0;
    protected Integer sommeCommission = 0;

    protected Date dateDebut = Date.from(Instant.now());
    protected Date dateFin = Date.from(Instant.now());

    protected String fileName = "";

    protected boolean showPrintButton = true;
    protected boolean showReportPrintDialog = false;

    public boolean isShowPrintButton() {
        return this.showPrintButton;
    }

    public String getFileName() {
        return this.fileName;
    }

    public boolean isShowReportPrintDialog() {
        return this.showReportPrintDialog;
    }

    public List<Versement> getVersements() {
        return versements;
    }

    public List<Retrait> getRetraits() {
        return retraits;
    }

    public List<Client> getClients() {
        clients = clientFacadeLocal.findAllRange(true);
        return clients;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

}
