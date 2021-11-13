package controllers.fraiscarnet;

import entities.AnneeMois;
import entities.Caisse;
import entities.Client;
import entities.Privilege;
import entities.Retrait;
import entities.Versement;
import java.io.Serializable;
import java.util.Date;
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
public class CarnetClientController extends AbstractCarnetClientController implements Serializable {

    @PostConstruct
    private void init() {
        this.client = new Client();
    }

    public void prepareCreate() {
        this.client = new Client();
        this.mode = "Create";
        this.showMontantCarnetCompnent = true;
        this.carnet = 500;
        try {
            filterDate(new Date());
            Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            if (p != null) {
                this.showClientCreateDialog = true;
            } else {
                p = new Privilege();
                p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 21);
                if (p != null) {
                    this.showClientCreateDialog = true;
                } else {
                    JsfUtil.addErrorMessage("Vous n'avez pas le privilege d'enregistrer un client");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareEdit() {
        this.mode = "Edit";
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
            JsfUtil.addErrorMessage("Vous n'avez pas le privilege de modifier ce client");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            if (this.mode.equals("Create")) {
                if (this.client.getIdclient() != null) {

                    Caisse c = this.caisseFacadeLocal.findAll().get(0);
                    c.setMontant((c.getMontant() + this.carnet));
                    this.caisseFacadeLocal.edit(c);

                    this.fraisCarnet.setId(this.fraisCarnetFacadeLocal.nextVal());
                    this.fraisCarnet.setIdmois(this.anneeMois);
                    this.fraisCarnet.setMontant(Double.valueOf(this.carnet));
                    this.fraisCarnet.setIdclient(this.client);
                    this.fraisCarnetFacadeLocal.create(this.fraisCarnet);

                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement des frais de carnet du client : " + this.client.getNom() + " " + this.client.getPrenom(), SessionMBean.getUserAccount());
                    this.client = new Client();
                    JsfUtil.addSuccessMessage("Opération réussie");
                } else {
                    JsfUtil.addErrorMessage("Aucun client sélectionné");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit() {
    }

    public void delete() {
        try {
            if (this.fraisCarnet != null) {

                Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
                if (p != null) {
                    this.showClientDeleteDialog = true;
                } else {
                    p = new Privilege();
                    p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 22);
                    if (p != null) {
                        this.showClientDeleteDialog = true;
                    } else {
                        this.showClientDeleteDialog = false;
                        JsfUtil.addErrorMessage("Vous n'avez pas le privilège de supprimer les frais de carnet");
                        return;
                    }
                }
                this.client = this.fraisCarnet.getIdclient();

                this.fraisCarnetFacadeLocal.remove(this.fraisCarnet);

                if (this.client.getFraiscarnet() != 0) {
                    Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
                    caisse.setMontant((caisse.getMontant() - this.client.getFraiscarnet()));
                    this.caisseFacadeLocal.edit(caisse);
                }

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression des frais de carnet du client : " + this.client.getNom() + " " + this.client.getPrenom(), SessionMBean.getUserAccount());
                JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                JsfUtil.addErrorMessage("Aucune ligne selectionnée");
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
                    this.showClientPrintDialog = true;
                } else {
                    this.showClientPrintDialog = false;
                    JsfUtil.addErrorMessage("Vous n 'avez pas le privilège d'éditer la liste des clients");
                    return;
                }
            }
            this.fileName = PrintUtils.printCustomerList(this.clients);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMois() {
        try {
            this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterDate(Date date) {
        for (AnneeMois a : this.anneeMoises) {
            try {
                if ((a.getDateDebut().equals(date) || a.getDateDebut().before(date)) && (a.getDateFin().equals(date) || a.getDateFin().after(date))) {
                    this.anneeMois = a;

                    break;
                }
            } catch (Exception exception) {
            }
        }
    }
}
