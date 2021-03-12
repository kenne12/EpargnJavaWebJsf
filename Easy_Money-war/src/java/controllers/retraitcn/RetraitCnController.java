package controllers.retraitcn;

import entities.Caisse;
import entities.Client;
import entities.Privilege;
import entities.Retrait;
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
public class RetraitCnController extends AbstractRetraitCnController implements Serializable {

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
        this.commission = 0;
        this.retrait1 = 0;
        this.client = new Client();
        this.retrait = new Retrait();
        this.retrait.setDate(SessionMBean.getDate());
        this.showClient = false;
        this.anneeMois = SessionMBean.getMois();
        try {
            Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            if (p != null) {
                this.showRetraitCreateDialog = true;
            } else {
                p = new Privilege();
                p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 13);
                if (p != null) {
                    this.showRetraitCreateDialog = true;
                } else {
                    this.showRetraitCreateDialog = false;
                    JsfUtil.addErrorMessage("Vous n 'avez pas le privilege d'enregistrer un rétrait");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareEdit() {
        this.mode = "Edit";
        this.showClient = true;

        if (this.retrait != null) {
            this.retrait1 = this.retrait.getMontant();
            this.commission = this.retrait.getCommission();
            this.anneeMois = this.retrait.getIdmois();
        }

        try {
            Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            if (p != null) {
                this.showRetraitCreateDialog = true;
            } else {
                p = new Privilege();
                p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 14);
                if (p != null) {
                    this.showRetraitCreateDialog = true;
                } else {
                    this.showRetraitCreateDialog = false;
                    JsfUtil.addErrorMessage("Vous n'avez pas le privilège de modifier un rétrait");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSolde1() {
        if (this.mode.equals("Create")) {
            if (this.retrait.getIdclient() != null) {
                if (this.retrait1 != null) {
                    Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                    if (this.commission != null) {
                        c.setSolde((c.getSolde() - this.retrait1 - this.commission));
                        this.retrait.getIdclient().setSolde(c.getSolde());
                    }
                } else {
                    Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                    this.retrait.getIdclient().setSolde(c.getSolde());
                }

            }
        } else if (this.retrait.getIdclient() != null) {
            if (this.retrait1 != null) {
                if (this.commission != null) {
                    Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                    int solde = c.getSolde() + this.retrait.getMontant() + this.retrait.getCommission() - this.retrait1 - this.commission;
                    this.retrait.getIdclient().setSolde((solde));
                }
            } else {
                Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                this.retrait.getIdclient().setSolde(c.getSolde());
            }
        }
    }

    public void updateSolde() {
        this.retrait1 = (0);
    }
    
    public void updateMode() {

    }

    public void searchData() {
        try {
            retraits.clear();
            if (searchMode.equals("date")) {
                if (!searchDate.equals(null)) {
                    this.retraits = retraitFacadeLocal.findByDate(searchDate);
                    return;
                }
            }
            if (searchMode.equals("mois")) {
                if (searchMois.getIdAnneeMois() != 0) {
                    this.retraits = retraitFacadeLocal.findByIdMois(searchMois.getIdAnneeMois());
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

                if (this.commission == null) {
                    this.commission = 0;
                }

                Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                c.setSolde((c.getSolde() - this.retrait1 - this.commission));

                this.retrait.setIdretrait(this.retraitFacadeLocal.nextLongVal());
                this.retrait.setMontant(this.retrait1);
                this.retrait.setCommission(this.commission);
                this.retrait.setSolde(Double.valueOf(c.getSolde()));
                this.retrait.setHeure(new Date());
                this.retrait.setIdmois(this.anneeMois);
                this.retrait.setCommissionAuto((false));
                this.retraitFacadeLocal.create(this.retrait);

                this.clientFacadeLocal.edit(c);

                Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
                caisse.setMontant((caisse.getMontant() - this.retrait1));
                this.caisseFacadeLocal.edit(caisse);

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du retrait -> client : " + this.retrait.getIdclient().getPrenom() + " " + this.retrait.getIdclient().getNom() + " ; Montant : " + this.retrait1, SessionMBean.getUserAccount());

                this.retrait = new Retrait();
                JsfUtil.addSuccessMessage("Transaction réussie");

            } else if (this.retrait != null && this.retrait1 != 0) {

                Retrait r = this.retraitFacadeLocal.find(this.retrait.getIdretrait());

                Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                c.setSolde((c.getSolde() + r.getMontant() + r.getCommission()));
                if (c.getSolde() < 0) {
                    c.setSolde(0);
                }
                this.clientFacadeLocal.edit(c);

                Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
                caisse.setMontant((caisse.getMontant() + r.getMontant() + r.getCommission()));

                if (caisse.getMontant() < 0) {
                    caisse.setMontant(0);
                }
                this.caisseFacadeLocal.edit(caisse);

                if (this.commission == null) {
                    this.commission = 0;
                }

                this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());

                this.retrait.setMontant(this.retrait1);
                this.retrait.setCommission(this.commission);
                this.retrait.setIdmois(this.anneeMois);
                this.retraitFacadeLocal.edit(this.retrait);

                Client c1 = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                c1.setSolde((c1.getSolde() - this.retrait1 - this.commission));
                this.clientFacadeLocal.edit(c1);

                Caisse caisse1 = this.caisseFacadeLocal.findAll().get(0);
                caisse1.setMontant((caisse1.getMontant() - this.retrait1));
                this.caisseFacadeLocal.edit(caisse1);

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Modification du retrait -> client : " + this.retrait.getIdclient().getPrenom() + " " + this.retrait.getIdclient().getNom() + " Ancien montant : " + r.getMontant() + " ; Nouveau Montant : " + this.retrait1, SessionMBean.getUserAccount());

                JsfUtil.addSuccessMessage("Opération réussie");
            } else {
                JsfUtil.addErrorMessage("Aucun retrait selectionné");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            if (this.retrait != null) {

                try {
                    Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
                    if (p != null) {
                        this.showRetraitDeleteDialog = (true);
                    } else {
                        p = new Privilege();
                        p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 15);
                        if (p != null) {
                            this.showRetraitDeleteDialog = true;
                        } else {
                            this.showRetraitDeleteDialog = false;
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                this.retraitFacadeLocal.remove(this.retrait);

                Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                c.setSolde((c.getSolde() + this.retrait.getMontant() + this.retrait.getCommission()));
                this.clientFacadeLocal.edit(c);

                Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
                caisse.setMontant((caisse.getMontant() + this.retrait.getMontant()));
                this.caisseFacadeLocal.edit(caisse);

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression du retrait -> client : " + this.retrait.getIdclient().getPrenom() + " " + this.retrait.getIdclient().getNom() + " ; Montant : " + this.retrait.getMontant(), SessionMBean.getUserAccount());

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
            this.showRetraitPrintDialog = (true);
        } else {
            p = new Privilege();
            p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 12);
            if (p != null) {
                this.showRetraitPrintDialog = (true);
            } else {
                this.showRetraitPrintDialog = (false);
                JsfUtil.addErrorMessage("Vous n'avez pas le privilege d'imprimer le rapport des retrait");
                return;
            }
        }
    }
}
