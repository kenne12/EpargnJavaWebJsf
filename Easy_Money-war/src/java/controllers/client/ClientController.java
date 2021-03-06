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
        this.client = new Client();
    }

    public void prepareCreate() {
        /*  42 */ this.client = new Client();
        /*  43 */ this.client.setSolde(Integer.valueOf(0));
        /*  44 */ this.client.setEtat(Boolean.valueOf(true));
        /*  45 */ this.mode = "Create";
        /*  46 */ this.showEditSolde = Boolean.valueOf(true);
        /*  47 */ this.showMontantCarnet = true;
        /*  48 */ this.showMontantCarnetCompnent = true;
        try {
            /*  50 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            /*  51 */ if (p != null) {
                /*  52 */ this.showClientCreateDialog = true;
            } else {
                /*  54 */ p = new Privilege();
                /*  55 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 7);
                /*  56 */ if (p != null) {
                    /*  57 */ this.showClientCreateDialog = true;
                } else {
                    /*  59 */ JsfUtil.addErrorMessage("Vous n'avez pas le privilège d'enregistrer un client");
                }
            }
            /*  62 */        } catch (Exception e) {
            /*  63 */ e.printStackTrace();
        }
    }

    public void prepareEdit() {
        /*  68 */ this.mode = "Edit";
        /*  69 */ this.showMontantCarnet = false;
        /*  70 */ this.showMontantCarnetCompnent = false;
        /*  71 */ if (this.client != null) {
            /*  72 */ List<Versement> versementsTemp = this.versementFacadeLocal.find(this.client);
            /*  73 */ if (versementsTemp.isEmpty()) {
                /*  74 */ List<Retrait> retraitsTemp = this.retraitFacadeLocal.find(this.client);
                /*  75 */ if (retraitsTemp.isEmpty()) {
                    /*  76 */ this.showEditSolde = Boolean.valueOf(true);
                } else {
                    /*  78 */ this.showEditSolde = Boolean.valueOf(false);
                }
            } else {
                /*  81 */ this.showEditSolde = Boolean.valueOf(false);
            }
        }

        try {
            /*  86 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            /*  87 */ if (p != null) {
                /*  88 */ this.showClientCreateDialog = true;
                return;
            }
            /*  91 */ p = new Privilege();
            /*  92 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 8);
            /*  93 */ if (p != null) {
                /*  94 */ this.showClientCreateDialog = true;
                return;
            }
            /*  97 */ JsfUtil.addErrorMessage("Vous n'avez pas le privilège de modifier ce client");

        } /* 100 */ catch (Exception e) {
            /* 101 */ e.printStackTrace();
        }
    }

    public void create() {
        try {
            /* 108 */ if (this.mode.equals("Create")) {
                /* 109 */ Client temp = this.clientFacadeLocal.findByCni(this.client.getCni());
                /* 110 */ if (temp == null) {

                    /* 112 */ temp = this.clientFacadeLocal.findByNumeroCarnet(this.client.getNumerocarnet());

                    /* 114 */ if (temp == null) {

                        /* 116 */ if (this.showMontantCarnet) {
                            /* 117 */ this.client.setFraiscarnet(Integer.valueOf(this.carnet));
                            /* 118 */ this.client.setCarnet(Boolean.valueOf(true));
                        } else {
                            /* 120 */ this.client.setFraiscarnet(Integer.valueOf(0));
                            /* 121 */ this.client.setCarnet(Boolean.valueOf(false));
                        }
                        /* 123 */ this.client.setIdclient(this.clientFacadeLocal.nextVal());
                        /* 124 */ this.clientFacadeLocal.create(this.client);

                        /* 126 */ List<Caisse> caisses = this.caisseFacadeLocal.findAll();
                        /* 127 */ if (caisses.isEmpty()) {
                            /* 128 */ Caisse c = new Caisse();
                            /* 129 */ c.setIdcaisse(this.caisseFacadeLocal.nextVal());
                            /* 130 */ c.setMontant(this.client.getSolde());
                            /* 131 */ if (this.showMontantCarnet) {
                                /* 132 */ c.setMontant(Integer.valueOf(c.getMontant().intValue() + this.carnet));
                            }
                            /* 134 */ this.caisseFacadeLocal.create(c);
                        } else {
                            /* 136 */ Caisse c = this.caisseFacadeLocal.findAll().get(0);
                            /* 137 */ c.setMontant(Integer.valueOf(c.getMontant().intValue() + this.client.getSolde().intValue()));
                            /* 138 */ if (this.showMontantCarnet) {
                                /* 139 */ c.setMontant(Integer.valueOf(c.getMontant().intValue() + this.carnet));
                            }
                            /* 141 */ this.caisseFacadeLocal.edit(c);
                        }

                        /* 144 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du client : " + this.client.getNom() + " " + this.client.getPrenom(), SessionMBean.getUserAccount());
                        /* 145 */ JsfUtil.addSuccessMessage("Client enregistré avec succès");
                    } else {
                        /* 147 */ JsfUtil.addErrorMessage("Un client utilisant ce numero de carnet existe deja");
                    }
                } else {

                    /* 151 */ JsfUtil.addErrorMessage("Un client ayant ce numero de cni existe dejà");
                }

                /* 154 */            } else if (this.client != null) {
                /* 155 */ this.clientFacadeLocal.edit(this.client);
                /* 156 */ JsfUtil.addSuccessMessage("Opération réussie");
            }

            /* 159 */        } catch (Exception e) {
            /* 160 */ e.printStackTrace();
        }
    }

    public void delete() {
        try {
            /* 166 */ if (this.client != null) {

                /* 168 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
                /* 169 */ if (p != null) {
                    /* 170 */ this.showClientDeleteDialog = true;
                } else {
                    /* 172 */ p = new Privilege();
                    /* 173 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 9);
                    /* 174 */ if (p != null) {
                        /* 175 */ this.showClientDeleteDialog = true;
                    } else {
                        /* 177 */ this.showClientDeleteDialog = false;
                        /* 178 */ JsfUtil.addErrorMessage("Vous n'avez pas le privilège de supprimer ce  client");

                        return;
                    }
                }
                /* 183 */ this.clientFacadeLocal.remove(this.client);

                /* 185 */ if (this.client.getSolde().intValue() != 0) {
                    /* 186 */ Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
                    /* 187 */ caisse.setMontant(Integer.valueOf(caisse.getMontant().intValue() - this.client.getSolde().intValue()));
                    /* 188 */ this.caisseFacadeLocal.edit(caisse);
                }

                /* 191 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression du client : " + this.client.getNom() + " " + this.client.getPrenom(), SessionMBean.getUserAccount());
                /* 192 */ JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                /* 194 */ JsfUtil.addErrorMessage("Aucun client selectionnée");
            }
            /* 196 */        } catch (Exception e) {
            /* 197 */ e.printStackTrace();
        }
    }

    public void print() {
        try {
            /* 203 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            /* 204 */ if (p != null) {
                /* 205 */ this.showClientPrintDialog = Boolean.valueOf(true);
            } else {
                /* 207 */ p = new Privilege();
                /* 208 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 20);
                /* 209 */ if (p != null) {
                    /* 210 */ this.showClientPrintDialog = Boolean.valueOf(true);
                } else {
                    /* 212 */ this.showClientPrintDialog = Boolean.valueOf(false);
                    /* 213 */ JsfUtil.addErrorMessage("Vous n'avez pas le privilège d'éditer la liste des clients");
                    return;
                }
            }
            /* 217 */ this.fileName = PrintUtils.printCustomerList(this.clientFacadeLocal.findAllRange(true));
            /* 218 */        } catch (Exception e) {
            /* 219 */ e.printStackTrace();
        }
    }
}
