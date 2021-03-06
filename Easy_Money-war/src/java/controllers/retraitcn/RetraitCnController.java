package controllers.retraitcn;

import entities.AnneeMois;
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
        /*  37 */ this.client = new Client();
        /*  38 */ this.retrait = new Retrait();
        /*  39 */ this.anneeMois = new AnneeMois();
    }

    public void updateMois() {
        try {
            this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareCreate() {
        /*  51 */ this.commission = 0;
        /*  52 */ this.retrait1 = 0;
        /*  53 */ this.client = new Client();
        /*  54 */ this.retrait = new Retrait();
        /*  55 */ this.retrait.setDate(SessionMBean.getDate());
        /*  56 */ this.mode = "Create";
        /*  57 */ this.showClient = false;
        /*  58 */ this.anneeMois = new AnneeMois();
        /*  59 */ this.anneeMois = SessionMBean.getMois();

        try {
            filterDate(new Date());
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
        /*  82 */ this.mode = "Edit";
        /*  83 */ this.showClient = true;

        /*  85 */ if (this.retrait != null) {
            /*  86 */ this.retrait1 = this.retrait.getMontant();
            /*  87 */ this.commission = this.retrait.getCommission();
            /*  88 */ this.anneeMois = this.retrait.getIdmois();
        }

        try {
            /*  92 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            /*  93 */ if (p != null) {
                /*  94 */ this.showRetraitCreateDialog = true;
            } else {
                /*  96 */ p = new Privilege();
                /*  97 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 14);
                /*  98 */ if (p != null) {
                    /*  99 */ this.showRetraitCreateDialog = true;
                } else {
                    /* 101 */ this.showRetraitCreateDialog = false;
                    /* 102 */ JsfUtil.addErrorMessage("Vous n'avez pas le privilège de modifier un rétrait");
                }
            }
            /* 105 */        } catch (Exception e) {
            /* 106 */ e.printStackTrace();
        }
    }

    public void updateSolde1() {
        if (this.mode == "Create") {
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
                    /* 129 */ Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                    /* 130 */ int solde = c.getSolde().intValue() + this.retrait.getMontant().intValue() + this.retrait.getCommission().intValue() - this.retrait1.intValue() - this.commission.intValue();
                    /* 131 */ this.retrait.getIdclient().setSolde(Integer.valueOf(solde));
                }
            } else {
                /* 134 */ Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                /* 135 */ this.retrait.getIdclient().setSolde(c.getSolde());
            }
        }
    }

    public void updateSolde() {
        this.retrait1 = (0);
    }

    public void create() {
        try {
            if (this.mode.equals("Create")) {

                if (this.commission == null) {
                    this.commission = 0;
                }

                /* 153 */ Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                /* 154 */ c.setSolde((c.getSolde() - this.retrait1 - this.commission));

                /* 156 */ this.retrait.setIdretrait(this.retraitFacadeLocal.nextLongVal());
                /* 157 */ this.retrait.setMontant(this.retrait1);
                /* 158 */ this.retrait.setCommission(this.commission);
                /* 159 */ this.retrait.setSolde(Double.valueOf(c.getSolde()));
                /* 160 */ this.retrait.setHeure(new Date());
                /* 161 */ this.retrait.setIdmois(this.anneeMois);
                /* 162 */ this.retrait.setCommissionAuto((false));
                /* 163 */ this.retraitFacadeLocal.create(this.retrait);

                /* 165 */ this.clientFacadeLocal.edit(c);

                /* 167 */ Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
                /* 168 */ caisse.setMontant((caisse.getMontant() - this.retrait1));
                /* 169 */ this.caisseFacadeLocal.edit(caisse);

                /* 171 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du retrait -> client : " + this.retrait.getIdclient().getPrenom() + " " + this.retrait.getIdclient().getNom() + " ; Montant : " + this.retrait1, SessionMBean.getUserAccount());

                /* 173 */ this.retrait = new Retrait();
                /* 174 */ JsfUtil.addSuccessMessage("Transaction réussie");

            } /* 177 */ else if (this.retrait != null && this.retrait1 != 0) {

                /* 179 */ Retrait r = this.retraitFacadeLocal.find(this.retrait.getIdretrait());

                /* 181 */ Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                /* 182 */ c.setSolde((c.getSolde() + r.getMontant() + r.getCommission()));
                /* 183 */ if (c.getSolde() < 0) {
                    /* 184 */ c.setSolde(0);
                }
                /* 186 */ this.clientFacadeLocal.edit(c);

                /* 188 */ Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
                /* 189 */ caisse.setMontant((caisse.getMontant() + r.getMontant() + r.getCommission()));

                /* 191 */ if (caisse.getMontant() < 0) {
                    /* 192 */ caisse.setMontant((0));
                }
                /* 194 */ this.caisseFacadeLocal.edit(caisse);

                /* 196 */ if (this.commission == null) {
                    /* 197 */ this.commission = (0);
                }

                /* 200 */ this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());

                /* 202 */ this.retrait.setMontant(this.retrait1);
                /* 203 */ this.retrait.setCommission(this.commission);
                /* 204 */ this.retrait.setIdmois(this.anneeMois);
                /* 205 */ this.retraitFacadeLocal.edit(this.retrait);

                /* 207 */ Client c1 = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                /* 208 */ c1.setSolde((c1.getSolde() - this.retrait1 - this.commission));
                /* 209 */ this.clientFacadeLocal.edit(c1);

                /* 211 */ Caisse caisse1 = this.caisseFacadeLocal.findAll().get(0);
                /* 212 */ caisse1.setMontant((caisse1.getMontant() - this.retrait1));
                /* 213 */ this.caisseFacadeLocal.edit(caisse1);

                /* 215 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Modification du retrait -> client : " + this.retrait.getIdclient().getPrenom() + " " + this.retrait.getIdclient().getNom() + " Ancien montant : " + r.getMontant() + " ; Nouveau Montant : " + this.retrait1, SessionMBean.getUserAccount());

                /* 217 */ JsfUtil.addSuccessMessage("Opération réussie");
            } else {

                /* 220 */ JsfUtil.addErrorMessage("Aucun retrait selectionné");
            }

            /* 223 */        } catch (Exception e) {
            /* 224 */ e.printStackTrace();
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

    private void filterDate(Date date) {
        for (AnneeMois a : this.anneeMoises) {
            try {
                if ((a.getDateDebut().equals(date) || a.getDateDebut().before(date)) && (a.getDateFin().equals(date) || a.getDateFin().after(date))) {
                    this.anneeMois = a;
                    System.err.println("retouvé");
                    break;
                }
            } catch (Exception exception) {
            }
        }
    }
}
