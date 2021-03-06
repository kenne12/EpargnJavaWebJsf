package controllers.utilisateur;

import entities.Privilege;
import entities.Utilisateur;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.ShaHash;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class UtilisateurController extends AbstractUtilisateurController implements Serializable {

    @PostConstruct
    private void init() {
        /*  34 */ this.utilisateur = new Utilisateur();
    }

    public void prepareCreate() {
        /*  38 */ this.utilisateur = new Utilisateur();
        /*  39 */ this.mode = "Create";

        try {
            /*  42 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            /*  43 */ if (p != null) {
                /*  44 */ this.showUserCreateDialog = Boolean.valueOf(true);
            } else {
                /*  46 */ p = new Privilege();
                /*  47 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 2);
                /*  48 */ if (p != null) {
                    /*  49 */ this.showUserCreateDialog = Boolean.valueOf(true);
                } else {
                    /*  51 */ this.showUserCreateDialog = Boolean.valueOf(false);
                    /*  52 */ JsfUtil.addErrorMessage("Vous n 'avez pas le privilege d'enregistrer un utilisateur");
                }
            }
            /*  55 */        } catch (Exception e) {
            /*  56 */ e.printStackTrace();
        }
    }

    public void prepareDetail() {
    }

    public void prepareEdit() {
        /*  69 */ this.mode = "Edit";

        try {
            /*  72 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            /*  73 */ if (p != null) {
                /*  74 */ this.showUserCreateDialog = Boolean.valueOf(true);
                return;
            }
            /*  77 */ p = new Privilege();
            /*  78 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 3);
            /*  79 */ if (p != null) {
                /*  80 */ this.showUserCreateDialog = Boolean.valueOf(true);
                return;
            }
            /*  83 */ this.showUserCreateDialog = Boolean.valueOf(false);
            /*  84 */ JsfUtil.addErrorMessage("Vous n 'avez pas le privilege de modifier cet utilisateur");

        } /*  87 */ catch (Exception e) {
            /*  88 */ e.printStackTrace();
        }
    }

    public void create() {
        try {
            /*  95 */ if (this.mode.equals("Create")) {

                /*  97 */ this.utilisateur.setIdutilisateur(this.utilisateurFacadeLocal.nextVal());
                /*  98 */ this.utilisateur.setActif(Boolean.valueOf(true));
                /*  99 */ this.utilisateur.setPassword((new ShaHash()).hash(this.utilisateur.getPassword()));
                /* 100 */ this.utilisateurFacadeLocal.create(this.utilisateur);
                /* 101 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de l'utilisateur : " + this.utilisateur.getNom() + " " + this.utilisateur.getPrenom(), SessionMBean.getUserAccount());
                /* 102 */ JsfUtil.addSuccessMessage("Client enregistré avec succès");
                /* 103 */ this.utilisateur = new Utilisateur();
            } /* 105 */ else if (this.utilisateur != null) {
                /* 106 */ this.utilisateurFacadeLocal.edit(this.utilisateur);
                /* 107 */ JsfUtil.addSuccessMessage("Opération réussie");
            } else {

                /* 110 */ JsfUtil.addErrorMessage("Aucun utilisateur selectionné");
            }

            /* 113 */        } catch (Exception e) {
            /* 114 */ e.printStackTrace();
        }
    }

    public void edit() {
    }

    public void delete() {
        try {
            /* 124 */ if (this.utilisateur != null) {

                /* 126 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
                /* 127 */ if (p != null) {
                    /* 128 */ this.showUserDeleteDialog = Boolean.valueOf(true);
                } else {
                    /* 130 */ p = new Privilege();
                    /* 131 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 4);
                    /* 132 */ if (p != null) {
                        /* 133 */ this.showUserDeleteDialog = Boolean.valueOf(true);
                    } else {
                        /* 135 */ this.showUserDeleteDialog = Boolean.valueOf(false);
                        /* 136 */ JsfUtil.addErrorMessage("Vous n 'avez pas le privilege de supprimer cet utilisateur");

                        return;
                    }
                }
                /* 141 */ this.utilisateurFacadeLocal.remove(this.utilisateur);

                /* 143 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion de l'utilisateur : " + this.utilisateur.getNom() + " " + this.utilisateur.getPrenom(), SessionMBean.getUserAccount());

                /* 145 */ JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                /* 147 */ JsfUtil.addErrorMessage("Aucun Utilisateur selectionnée");
            }
            /* 149 */        } catch (Exception e) {
            /* 150 */ e.printStackTrace();
        }
    }

    public void changeStatus(Utilisateur utilisateur, String mode) {
        try {
            /* 156 */ if (mode.equals("activer")) {
                /* 157 */ Privilege privilege = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
                /* 158 */ if (privilege != null) {
                    /* 159 */ utilisateur.setActif(Boolean.valueOf(true));
                    /* 160 */ this.utilisateurFacadeLocal.edit(utilisateur);
                    /* 161 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Activation du compte de l'utilisateur : " + utilisateur.getNom() + " " + utilisateur.getPrenom(), SessionMBean.getUserAccount());
                    /* 162 */ JsfUtil.addSuccessMessage("Operation réussie");
                    return;
                }
                /* 165 */ privilege = new Privilege();
                /* 166 */ privilege = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 18);
                /* 167 */ if (privilege != null) {
                    /* 168 */ utilisateur.setActif(Boolean.valueOf(true));
                    /* 169 */ this.utilisateurFacadeLocal.edit(utilisateur);
                    /* 170 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Activation du compte de l'utilisateur : " + utilisateur.getNom() + " " + utilisateur.getPrenom(), SessionMBean.getUserAccount());
                    /* 171 */ JsfUtil.addSuccessMessage("Operation réussie");
                    return;
                }
                /* 174 */ JsfUtil.addErrorMessage("Vous n 'avez pas le privilege d activer un compte utilisateur");

                return;
            }

            /* 179 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            /* 180 */ if (p != null) {
                /* 181 */ utilisateur.setActif(Boolean.valueOf(false));
                /* 182 */ this.utilisateurFacadeLocal.edit(utilisateur);
                /* 183 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Désativation du compte de l'utilisateur : " + utilisateur.getNom() + " " + utilisateur.getPrenom(), SessionMBean.getUserAccount());
                /* 184 */ JsfUtil.addSuccessMessage("Operation réussie");
                return;
            }
            /* 187 */ p = new Privilege();
            /* 188 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 19);
            /* 189 */ if (p != null) {
                /* 190 */ utilisateur.setActif(Boolean.valueOf(false));
                /* 191 */ this.utilisateurFacadeLocal.edit(utilisateur);
                /* 192 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Desactivation du compte de l'utilisateur : " + utilisateur.getNom() + " " + utilisateur.getPrenom(), SessionMBean.getUserAccount());
                /* 193 */ JsfUtil.addSuccessMessage("Operation réussie");
                return;
            }
            /* 196 */ JsfUtil.addErrorMessage("Vous n 'avez pas le privilege de désactiver un compte utilisateur");

            return;
            /* 201 */        } catch (Exception e) {
            /* 202 */ e.printStackTrace();
            return;
        }
    }

    public void print() {
        /* 207 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
        /* 208 */ if (p != null) {
            /* 209 */ this.showUserPrintDialog = Boolean.valueOf(true);
        } else {
            /* 211 */ p = new Privilege();
            /* 212 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 4);
            /* 213 */ if (p != null) {
                /* 214 */ this.showUserPrintDialog = Boolean.valueOf(true);
            } else {
                /* 216 */ this.showUserPrintDialog = Boolean.valueOf(false);
                /* 217 */ JsfUtil.addErrorMessage("Vous n 'avez pas le privilege d'imprimer la liste des utilisateurs");
                return;
            }
        }
    }
}
