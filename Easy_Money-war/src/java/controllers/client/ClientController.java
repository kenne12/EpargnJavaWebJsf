package controllers.client;

import entities.Caisse;
import entities.Client;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.PrintUtils;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class ClientController extends AbstractClientController implements Serializable {

    @PostConstruct
    private void init() {

    }

    public void prepareCreate() {
        if (!Utilitaires.isAccess(7l)) {
            JsfUtil.addErrorMessage("Vous n'avez pas le privilège d'enregistrer un client");
            return;
        }

        this.client = new Client();
        this.client.setSolde(0);
        this.client.setEtat(true);
        this.mode = "Create";
        this.showEditSolde = true;
        this.showMontantCarnet = true;
        RequestContext.getCurrentInstance().execute("PF('ClientCreerDialog').show()");
    }

    public void prepareEdit() {

        if (Objects.isNull(client) && Objects.isNull(client.getIdclient())) {
            JsfUtil.addWarningMessage("Veuillez sélectionner un client");
            return;
        }
        if (!Utilitaires.isAccess(8l)) {
            JsfUtil.addErrorMessage("Vous n'avez pas le privilège de modifier un client");
            return;
        }

        this.mode = "Edit";
        this.showMontantCarnet = false;
        this.showEditSolde = true;

        if (!versementFacadeLocal.find(client).isEmpty() || !retraitFacadeLocal.find(this.client).isEmpty()) {
            this.showEditSolde = false;
        }

        RequestContext.getCurrentInstance().execute("PF('ClientCreerDialog').show()");
    }

    public void create() {
        try {
            if (this.mode.equals("Create")) {
                Client temp = this.clientFacadeLocal.findByCni(this.client.getCni());
                if (temp == null) {

                    temp = this.clientFacadeLocal.findByNumeroCarnet(this.client.getNumerocarnet());

                    if (temp == null) {

                        if (this.showMontantCarnet) {
                            this.client.setFraiscarnet(this.carnet);
                            this.client.setCarnet(true);
                        } else {
                            this.client.setFraiscarnet(0);
                            this.client.setCarnet((false));
                        }
                        this.client.setIdclient(this.clientFacadeLocal.nextVal());
                        this.clientFacadeLocal.create(this.client);

                        List<Caisse> caisses = this.caisseFacadeLocal.findAll();
                        if (caisses.isEmpty()) {
                            Caisse c = new Caisse();
                            c.setIdcaisse(this.caisseFacadeLocal.nextVal());
                            c.setMontant(this.client.getSolde());
                            if (this.showMontantCarnet) {
                                c.setMontant((c.getMontant() + this.carnet));
                            }
                            this.caisseFacadeLocal.create(c);
                        } else {
                            Caisse c = this.caisseFacadeLocal.findAll().get(0);
                            c.setMontant((c.getMontant() + this.client.getSolde()));
                            if (this.showMontantCarnet) {
                                c.setMontant((c.getMontant() + this.carnet));
                            }
                            this.caisseFacadeLocal.edit(c);
                        }

                        Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du client : " + this.client.getNom() + " " + this.client.getPrenom(), SessionMBean.getUserAccount());
                        JsfUtil.addSuccessMessage("Client enregistré avec succès");
                    } else {
                        JsfUtil.addErrorMessage("Un client utilisant ce numero de carnet existe deja");
                    }
                } else {
                    JsfUtil.addErrorMessage("Un client ayant ce numero de cni existe dejà");
                }
            } else if (this.client != null) {
                this.clientFacadeLocal.edit(this.client);
                JsfUtil.addSuccessMessage("Opération réussie");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            if (this.client != null) {

                if (!Utilitaires.isAccess(9l)) {
                    JsfUtil.addErrorMessage("Vous n'avez pas le privilège de supprimer un client");
                    return;
                }

                this.clientFacadeLocal.remove(this.client);

                if (this.client.getSolde() != 0) {
                    Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
                    caisse.setMontant((caisse.getMontant() - this.client.getSolde()));
                    this.caisseFacadeLocal.edit(caisse);
                }

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression du client : " + this.client.getNom() + " " + this.client.getPrenom(), SessionMBean.getUserAccount());
                JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                JsfUtil.addErrorMessage("Aucun client selectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print() {
        try {
            if (!Utilitaires.isAccess(7l)) {
                JsfUtil.addErrorMessage("Vous n'avez pas le privilège d'éditer la liste des clients");
                return;
            }
            this.fileName = PrintUtils.printCustomerList(this.clientFacadeLocal.findAllRange(true));
            RequestContext.getCurrentInstance().execute("PF('ClientImprimerDialog').show()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
