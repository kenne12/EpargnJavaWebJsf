package controllers.client;

import entities.Caisse;
import entities.Client;
import entities.Privilege;
import entities.Retrait;
import entities.Versement;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
        this.client = new Client();
        this.client.setSolde(0);
        this.client.setEtat(true);
        this.mode = "Create";
        this.showEditSolde = true;
        this.showMontantCarnet = true;
        this.showMontantCarnetCompnent = true;
        try {
            Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            if (p != null) {
                this.showClientCreateDialog = true;
            } else {
                p = new Privilege();
                p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 7);
                if (p != null) {
                    this.showClientCreateDialog = true;
                } else {
                    JsfUtil.addErrorMessage("Vous n'avez pas le privilège d'enregistrer un client");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareEdit() {
        this.mode = "Edit";
        this.showMontantCarnet = false;
        this.showMontantCarnetCompnent = false;
        if (this.client != null) {
            List<Versement> versementsTemp = this.versementFacadeLocal.find(this.client);
            if (versementsTemp.isEmpty()) {
                List<Retrait> retraitsTemp = this.retraitFacadeLocal.find(this.client);
                if (retraitsTemp.isEmpty()) {
                    this.showEditSolde = true;
                } else {
                    this.showEditSolde = false;
                }
            } else {
                this.showEditSolde = false;
            }
        }

        try {
            Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            if (p != null) {
                this.showClientCreateDialog = true;
                return;
            }
            p = new Privilege();
            p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 8);
            if (p != null) {
                this.showClientCreateDialog = true;
                return;
            }
            JsfUtil.addErrorMessage("Vous n'avez pas le privilège de modifier ce client");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

                Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
                if (p != null) {
                    this.showClientDeleteDialog = true;
                } else {
                    p = new Privilege();
                    p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 9);
                    if (p != null) {
                        this.showClientDeleteDialog = true;
                    } else {
                        this.showClientDeleteDialog = false;
                        JsfUtil.addErrorMessage("Vous n'avez pas le privilège de supprimer ce  client");
                        return;
                    }
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
            Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            if (p != null) {
                this.showClientPrintDialog = (true);
            } else {
                p = new Privilege();
                p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 20);
                if (p != null) {
                    this.showClientPrintDialog = (true);
                } else {
                    this.showClientPrintDialog = (false);
                    JsfUtil.addErrorMessage("Vous n'avez pas le privilège d'éditer la liste des clients");
                    return;
                }
            }
            this.fileName = PrintUtils.printCustomerList(this.clientFacadeLocal.findAllRange(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
