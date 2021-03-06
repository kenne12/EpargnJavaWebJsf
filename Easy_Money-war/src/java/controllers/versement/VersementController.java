package controllers.versement;

import entities.AnneeMois;
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
        this.client = new Client();
        this.versement = new Versement();
    }

    public void updateMois() {
        try {
            this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareCreate() {
        /*  51 */ this.client = new Client();
        /*  52 */ this.versement = new Versement();
        /*  53 */ this.versement.setDate(SessionMBean.getDate());
        /*  54 */ this.versement.setSolde((0.0D));
        /*  55 */ this.mode = "Create";
        /*  56 */ this.showClient = (false);
        /*  57 */ this.anneeMois = new AnneeMois();
        /*  58 */ this.anneeMois = SessionMBean.getMois();

        try {
            /*  62 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            /*  63 */ if (p != null) {
                /*  64 */ this.showVersementCreateDialog = (true);
            } else {
                /*  66 */ p = new Privilege();
                /*  67 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 10);
                /*  68 */ if (p != null) {
                    /*  69 */ this.showVersementCreateDialog = (true);
                } else {
                    /*  71 */ this.showVersementCreateDialog = (false);
                    /*  72 */ JsfUtil.addErrorMessage("Vous n 'avez pas le privilege d'enregistrer un versement");
                }
            }
            /*  75 */        } catch (Exception e) {
            /*  76 */ e.printStackTrace();
        }
    }

    public void prepareEdit() {
        /*  82 */ this.mode = "Edit";
        /*  83 */ this.showClient = true;

        /*  85 */ if (this.versement != null) {
            /*  86 */ this.versement1 = this.versement.getMontant();

            /*  88 */ this.anneeMois = this.versement.getIdmois();
        }

        try {
            /*  92 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            /*  93 */ if (p != null) {
                /*  94 */ this.showVersementCreateDialog = (true);
            } else {
                /*  96 */ p = new Privilege();
                /*  97 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 11);
                /*  98 */ if (p != null) {
                    /*  99 */ this.showVersementCreateDialog = (true);
                } else {
                    /* 101 */ this.showVersementCreateDialog = (false);
                    /* 102 */ JsfUtil.addErrorMessage("Vous n 'avez pas le privilege de modifier ce versement");
                }
            }
            /* 105 */        } catch (Exception e) {
            /* 106 */ e.printStackTrace();
        }
    }

    public void updateSolde1() {
        if (this.mode == "Create") {
            if (this.versement.getIdclient() != null) {
                if (this.versement1 != null) {
                    /* 116 */ Client c = this.clientFacadeLocal.find(this.versement.getIdclient().getIdclient());
                    /* 117 */ int solde = c.getSolde() + this.versement1;
                    /* 118 */ this.versement.getIdclient().setSolde((solde));
                } else {
                    /* 120 */ Client c = this.clientFacadeLocal.find(this.versement.getIdclient().getIdclient());
                    /* 121 */ this.versement.getIdclient().setSolde(c.getSolde());
                }

            }
        } else if (this.versement.getIdclient() != null) {
            if (this.versement1 != null) {
                Client c = this.clientFacadeLocal.find(this.versement.getIdclient().getIdclient());
                int solde = c.getSolde() - this.versement1;
                this.versement.getIdclient().setSolde((solde));
            } else {
                /* 131 */ Client c = this.clientFacadeLocal.find(this.versement.getIdclient().getIdclient());
                /* 132 */ this.versement.getIdclient().setSolde(c.getSolde());
            }
        }
    }

    public void updateSolde() {
        /* 139 */ this.versement1 = (0);
    }

    public void create() {
        try {
            /* 144 */ if (this.mode.equals("Create")) {

                /* 146 */ Client c = this.clientFacadeLocal.find(this.versement.getIdclient().getIdclient());
                /* 147 */ c.setSolde(c.getSolde() + this.versement1);

                /* 149 */ this.versement.setIdversement(this.versementFacadeLocal.nextVal());
                /* 150 */ this.versement.setMontant(this.versement1);
                /* 151 */ this.versement.setSolde(Double.valueOf(c.getSolde()));
                /* 152 */ this.versement.setHeure(new Date());
                /* 153 */ this.versement.setIdmois(this.anneeMois);
                /* 154 */ this.versementFacadeLocal.create(this.versement);

                /* 156 */ this.clientFacadeLocal.edit(c);

                /* 158 */ Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
                /* 159 */ caisse.setMontant((caisse.getMontant() + this.versement1));
                /* 160 */ this.caisseFacadeLocal.edit(caisse);

                /* 162 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du versement -> client : " + this.versement.getIdclient().getPrenom() + " " + this.versement.getIdclient().getNom() + " ; Montant : " + this.versement1, SessionMBean.getUserAccount());

                /* 164 */ this.versement = new Versement();
                /* 165 */ JsfUtil.addSuccessMessage("Transaction réussie");
            } /* 167 */ else if (this.versement != null) {

                /* 169 */ Versement v = this.versementFacadeLocal.find(this.versement.getIdversement());

                /* 171 */ Client c = this.clientFacadeLocal.find(this.versement.getIdclient().getIdclient());
                /* 172 */ c.setSolde((c.getSolde() - v.getMontant()));
                /* 173 */ if (c.getSolde() < 0) {
                    /* 174 */ c.setSolde((0));
                }
                /* 176 */ this.clientFacadeLocal.edit(c);

                /* 178 */ Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
                /* 179 */ caisse.setMontant((caisse.getMontant() - v.getMontant()));

                /* 181 */ if (caisse.getMontant() < 0) {
                    /* 182 */ caisse.setMontant((0));
                }
                /* 184 */ this.caisseFacadeLocal.edit(caisse);

                /* 186 */ this.versement.setMontant(this.versement1);
                /* 187 */ this.versement.setIdmois(this.anneeMois);
                /* 188 */ this.versementFacadeLocal.edit(this.versement);

                /* 190 */ Client c1 = this.clientFacadeLocal.find(this.versement.getIdclient().getIdclient());
                /* 191 */ c1.setSolde((c1.getSolde() + this.versement1));
                /* 192 */ this.clientFacadeLocal.edit(c1);

                /* 194 */ Caisse caisse1 = this.caisseFacadeLocal.findAll().get(0);
                /* 195 */ caisse1.setMontant((caisse1.getMontant() + this.versement1));
                /* 196 */ this.caisseFacadeLocal.edit(caisse1);

                /* 198 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Modification du versement -> client : " + this.versement.getIdclient().getPrenom() + " " + this.versement.getIdclient().getNom() + " Ancien montant : " + v.getMontant() + " ; Nouveau Montant : " + this.versement1, SessionMBean.getUserAccount());

                /* 200 */ JsfUtil.addSuccessMessage("Opération réussie");
            } else {

                /* 203 */ JsfUtil.addErrorMessage("Aucun versement selectionné");
            }

            /* 206 */        } catch (Exception e) {
            /* 207 */ e.printStackTrace();
        }
    }

    public void edit() {
    }

    public void delete() {
        try {
            /* 217 */ if (this.versement != null) {

                try {
                    /* 220 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
                    /* 221 */ if (p != null) {
                        /* 222 */ this.showVersementDeleteDialog = (true);
                    } else {
                        /* 224 */ p = new Privilege();
                        /* 225 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 12);
                        /* 226 */ if (p != null) {
                            /* 227 */ this.showVersementDeleteDialog = (true);
                        } else {
                            /* 229 */ this.showVersementDeleteDialog = (false);
                            /* 230 */ JsfUtil.addErrorMessage("Vous n 'avez pas le privilege de supprimer ce versement");
                            return;
                        }
                    }
                    /* 234 */                } catch (Exception e) {
                    /* 235 */ e.printStackTrace();
                }

                /* 238 */ this.versementFacadeLocal.remove(this.versement);

                /* 240 */ Client c = this.versement.getIdclient();
                /* 241 */ c.setSolde((c.getSolde() - this.versement.getMontant()));
                /* 242 */ this.clientFacadeLocal.edit(c);

                /* 244 */ Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
                /* 245 */ caisse.setMontant((caisse.getMontant() - this.versement.getMontant()));
                /* 246 */ this.caisseFacadeLocal.edit(caisse);

                /* 248 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression du versement -> client : " + this.versement.getIdclient().getPrenom() + " " + this.versement.getIdclient().getNom() + " ; Montant : " + this.versement.getMontant(), SessionMBean.getUserAccount());

                /* 250 */ JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                /* 252 */ JsfUtil.addErrorMessage("Aucun client selectionnée");
            }
            /* 254 */        } catch (Exception e) {
            /* 255 */ e.printStackTrace();
        }
    }

    public void print() {
        /* 260 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
        /* 261 */ if (p != null) {
            /* 262 */ this.showVersementPrintDialog = Boolean.valueOf(true);
        } else {
            /* 264 */ p = new Privilege();
            /* 265 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 12);
            /* 266 */ if (p != null) {
                /* 267 */ this.showVersementPrintDialog = Boolean.valueOf(true);
            } else {
                /* 269 */ this.showVersementPrintDialog = Boolean.valueOf(false);
                /* 270 */ JsfUtil.addErrorMessage("Vous n 'avez pas le privilege d'imprimer le rapport des versement");
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
