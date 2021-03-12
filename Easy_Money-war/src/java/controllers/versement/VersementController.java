package controllers.versement;

import entities.Caisse;
import entities.Client;
import entities.Privilege;
import entities.Versement;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class VersementController extends AbstractVersementController implements Serializable {

    @PostConstruct
    private void init() {
        this.searchMode = "date";
        this.searchDate = SessionMBean.getDate();
    }

    public void updateMois() {
        try {
            this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareCreate() {
        this.mode = "Create";
        this.client = new Client();
        this.versement = new Versement();
        this.versement.setDate(SessionMBean.getDate());
        this.versement.setSolde(0.0);
        this.showClient = false;
        this.anneeMois = SessionMBean.getMois();

        try {
            Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 1);
            if (p != null) {
                this.showVersementCreateDialog = true;
            } else {
                p = new Privilege();
                p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 10);
                if (p != null) {
                    this.showVersementCreateDialog = true;
                } else {
                    this.showVersementCreateDialog = false;
                    JsfUtil.addErrorMessage("Vous n 'avez pas le privilege d'enregistrer un versement");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareEdit() {
        this.mode = "Edit";
        this.showClient = true;

        if (this.versement != null) {
            this.versement1 = this.versement.getMontant();
            this.anneeMois = this.versement.getIdmois();
        }

        try {
            Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            if (p != null) {
                this.showVersementCreateDialog = true;
            } else {
                p = new Privilege();
                p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 11);
                if (p != null) {
                    this.showVersementCreateDialog = true;
                } else {
                    this.showVersementCreateDialog = false;
                    JsfUtil.addErrorMessage("Vous n 'avez pas le privilege de modifier ce versement");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSolde1() {
        if (this.mode.equals("Create")) {
            if (this.versement.getIdclient() != null) {
                if (this.versement1 != null) {
                    Client c = this.clientFacadeLocal.find(this.versement.getIdclient().getIdclient());
                    int solde = c.getSolde() + this.versement1;
                    this.versement.getIdclient().setSolde(solde);
                } else {
                    Client c = this.clientFacadeLocal.find(this.versement.getIdclient().getIdclient());
                    this.versement.getIdclient().setSolde(c.getSolde());
                }

            }
        } else if (this.versement.getIdclient() != null) {
            if (this.versement1 != null) {
                Client c = this.clientFacadeLocal.find(this.versement.getIdclient().getIdclient());
                int solde = c.getSolde() - this.versement1;
                this.versement.getIdclient().setSolde((solde));
            } else {
                Client c = this.clientFacadeLocal.find(this.versement.getIdclient().getIdclient());
                this.versement.getIdclient().setSolde(c.getSolde());
            }
        }
    }

    public void updateSolde() {
        this.versement1 = 0;
    }

    public void updateMode() {

    }

    public void searchData() {
        try {
            versements.clear();
            if (searchMode.equals("date")) {
                if (!searchDate.equals(null)) {
                    this.versements = versementFacadeLocal.findByDate(searchDate);
                    return;
                }
            }
            if (searchMode.equals("mois")) {
                if (searchMois.getIdAnneeMois() != 0) {
                    this.versements = versementFacadeLocal.findByIdMois(searchMois.getIdAnneeMois());
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            if (this.mode.equals("Create")) {

                Client c = this.clientFacadeLocal.find(this.versement.getIdclient().getIdclient());
                c.setSolde(c.getSolde() + this.versement1);

                this.versement.setIdversement(this.versementFacadeLocal.nextVal());
                this.versement.setMontant(this.versement1);
                this.versement.setSolde(Double.valueOf(c.getSolde()));
                this.versement.setHeure(new Date());
                this.versement.setIdmois(this.anneeMois);
                this.versementFacadeLocal.create(this.versement);

                this.clientFacadeLocal.edit(c);

                Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
                caisse.setMontant((caisse.getMontant() + this.versement1));
                this.caisseFacadeLocal.edit(caisse);

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du versement -> client : " + this.versement.getIdclient().getPrenom() + " " + this.versement.getIdclient().getNom() + " ; Montant : " + this.versement1, SessionMBean.getUserAccount());

                this.versement = new Versement();
                JsfUtil.addSuccessMessage("Transaction réussie");
            } else if (this.versement != null) {

                Versement v = this.versementFacadeLocal.find(this.versement.getIdversement());

                Client c = this.clientFacadeLocal.find(this.versement.getIdclient().getIdclient());
                c.setSolde((c.getSolde() - v.getMontant()));
                if (c.getSolde() < 0) {
                    c.setSolde(0);
                }
                this.clientFacadeLocal.edit(c);

                Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
                caisse.setMontant((caisse.getMontant() - v.getMontant()));

                if (caisse.getMontant() < 0) {
                    caisse.setMontant(0);
                }
                this.caisseFacadeLocal.edit(caisse);

                this.versement.setMontant(this.versement1);
                this.versement.setIdmois(this.anneeMois);
                this.versementFacadeLocal.edit(this.versement);

                Client c1 = this.clientFacadeLocal.find(this.versement.getIdclient().getIdclient());
                c1.setSolde(c1.getSolde() + this.versement1);
                this.clientFacadeLocal.edit(c1);

                Caisse caisse1 = this.caisseFacadeLocal.findAll().get(0);
                caisse1.setMontant(caisse1.getMontant() + this.versement1);
                this.caisseFacadeLocal.edit(caisse1);

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Modification du versement -> client : " + this.versement.getIdclient().getPrenom() + " " + this.versement.getIdclient().getNom() + " Ancien montant : " + v.getMontant() + " ; Nouveau Montant : " + this.versement1, SessionMBean.getUserAccount());

                JsfUtil.addSuccessMessage("Opération réussie");
            } else {
                JsfUtil.addErrorMessage("Aucun versement selectionné");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit() {
    }

    public void delete() {
        try {
            if (this.versement != null) {
                try {
                    Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
                    if (p != null) {
                        this.showVersementDeleteDialog = true;
                    } else {
                        p = new Privilege();
                        p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 12);
                        if (p != null) {
                            this.showVersementDeleteDialog = true;
                        } else {
                            this.showVersementDeleteDialog = (false);
                            JsfUtil.addErrorMessage("Vous n 'avez pas le privilege de supprimer ce versement");
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                this.versementFacadeLocal.remove(this.versement);

                Client c = this.versement.getIdclient();
                c.setSolde((c.getSolde() - this.versement.getMontant()));
                this.clientFacadeLocal.edit(c);

                Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
                caisse.setMontant((caisse.getMontant() - this.versement.getMontant()));
                this.caisseFacadeLocal.edit(caisse);

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression du versement -> client : " + this.versement.getIdclient().getPrenom() + " " + this.versement.getIdclient().getNom() + " ; Montant : " + this.versement.getMontant(), SessionMBean.getUserAccount());

                JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                JsfUtil.addErrorMessage("Aucun client selectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print() {
        Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
        if (p != null) {
            this.showVersementPrintDialog = (true);
        } else {
            p = new Privilege();
            p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 12);
            if (p != null) {
                this.showVersementPrintDialog = (true);
            } else {
                this.showVersementPrintDialog = (false);
                JsfUtil.addErrorMessage("Vous n 'avez pas le privilege d'imprimer le rapport des versement");
                return;
            }
        }
    }

}
