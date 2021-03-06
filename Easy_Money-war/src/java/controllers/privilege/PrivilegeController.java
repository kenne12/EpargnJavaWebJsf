package controllers.privilege;

import controllers.privilege.AbstractPrivilegeController;
import controllers.privilege.PrivilegeInterfaceController;
import entities.Menu;
import entities.Privilege;
import entities.Utilisateur;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class PrivilegeController extends AbstractPrivilegeController implements PrivilegeInterfaceController, Serializable {

    @PostConstruct
    private void initAcces() {
        /*  31 */ this.privilege = new Privilege();
        /*  32 */ this.utilisateur = new Utilisateur();
        /*  33 */ this.menu = new Menu();
    }

    public void prepareCreate() {
        /*  37 */ this.dualMenu.getSource().clear();
        /*  38 */ this.dualMenu.getTarget().clear();
        /*  39 */ this.utilisateurs = this.utilisateurFacadeLocal.findByActif(Boolean.valueOf(true));

        try {
            /*  42 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            /*  43 */ if (p != null) {
                /*  44 */ this.showPrivilegeCreateDialog = Boolean.valueOf(true);
                return;
            }
            /*  47 */ p = new Privilege();
            /*  48 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 5);
            /*  49 */ if (p != null) {
                /*  50 */ this.showPrivilegeCreateDialog = Boolean.valueOf(true);
                return;
            }
            /*  53 */ this.showPrivilegeCreateDialog = Boolean.valueOf(false);
            /*  54 */ JsfUtil.addErrorMessage("Vous n 'avez pas le droit d'attribuer les privileges aux utilisateurs");

        } /*  57 */ catch (Exception e) {
            /*  58 */ e.printStackTrace();
        }
    }

    public void prepareDelete() {
        /*  64 */ this.dualMenu.getSource().clear();
        /*  65 */ this.dualMenu.getTarget().clear();
        /*  66 */ this.utilisateurs = this.utilisateurFacadeLocal.findByActif(Boolean.valueOf(true));

        try {
            /*  69 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            /*  70 */ if (p != null) {
                /*  71 */ this.showPrivilegeDeleteDialog = Boolean.valueOf(true);
                return;
            }
            /*  74 */ p = new Privilege();
            /*  75 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 6);
            /*  76 */ if (p != null) {
                /*  77 */ this.showPrivilegeDeleteDialog = Boolean.valueOf(true);
                return;
            }
            /*  80 */ this.showPrivilegeDeleteDialog = Boolean.valueOf(false);
            /*  81 */ JsfUtil.addErrorMessage("Vous n 'avez pas le droit de retirer les privileges aux utilisateurs");

        } /*  84 */ catch (Exception e) {
            /*  85 */ e.printStackTrace();
        }
    }

    public void handleUserChange() {
        /*  91 */ this.dualMenu.getSource().clear();
        /*  92 */ this.dualMenu.getTarget().clear();
        try {
            /*  94 */ if (this.utilisateur.getIdutilisateur() != null) {
                /*  95 */ this.utilisateur = this.utilisateurFacadeLocal.find(this.utilisateur.getIdutilisateur());
                /*  96 */ List<Privilege> privilegeTemp = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur().intValue());
                /*  97 */ if (privilegeTemp.isEmpty()) {
                    /*  98 */ this.dualMenu.setSource(this.menuFacadeLocal.findAll());
                } else {
                    /* 100 */ Privilege privilegeTemp1 = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur().intValue(), 1);
                    /* 101 */ if (privilegeTemp1 != null) {
                        /* 102 */ JsfUtil.addWarningMessage("Vous disposez deja de tous les droit possibles dans ce systeme");
                    } else {

                        /* 105 */ List<Menu> menus1 = this.menuFacadeLocal.findAll();
                        /* 106 */ List<Menu> menus2 = new ArrayList<>();
                        /* 107 */ List<Privilege> privilegeTemp2 = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur().intValue());

                        /* 109 */ for (Privilege p : privilegeTemp2) {
                            /* 110 */ menus2.add(p.getIdmenu());
                        }

                        /* 113 */ for (Menu m : menus1) {
                            /* 114 */ if (!menus2.contains(m)) {
                                /* 115 */ this.dualMenu.getSource().add(m);
                            }
                        }
                    }
                }
            }
            /* 121 */        } catch (Exception e) {
            /* 122 */ e.printStackTrace();
        }
    }

    public void handleUserChange1() {
        /* 127 */ this.dualMenu.getSource().clear();
        /* 128 */ this.dualMenu.getTarget().clear();
        try {
            /* 130 */ if (this.utilisateur.getIdutilisateur() != null) {
                /* 131 */ this.utilisateur = this.utilisateurFacadeLocal.find(this.utilisateur.getIdutilisateur());
                /* 132 */ List<Privilege> privilegeTemp = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur().intValue());
                /* 133 */ if (privilegeTemp.isEmpty()) {
                    /* 134 */ this.dualMenu.setSource(this.menuFacadeLocal.findAll());
                } else {

                    /* 137 */ List<Menu> menus1 = new ArrayList<>();

                    /* 139 */ List<Privilege> privilegeTemp2 = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur().intValue());

                    /* 141 */ for (Privilege p : privilegeTemp2) {
                        /* 142 */ menus1.add(p.getIdmenu());
                    }
                    /* 144 */ this.dualMenu.getSource().addAll(menus1);
                }
            }
            /* 147 */        } catch (Exception e) {
            /* 148 */ e.printStackTrace();
        }
    }

    public void save() {
        try {
            /* 155 */ if (this.utilisateur.getIdutilisateur() != null) {
                /* 156 */ this.utilisateur = this.utilisateurFacadeLocal.find(this.utilisateur.getIdutilisateur());
                /* 157 */ if (!this.dualMenu.getTarget().isEmpty()) {
                    /* 158 */ Boolean flag = Boolean.valueOf(false);
                    /* 159 */ for (Menu m : this.dualMenu.getTarget()) {

                        /* 161 */ if (!flag.booleanValue()) {

                            /* 163 */ if (m.getIdmenu().intValue() == 1) {
                                /* 164 */ flag = Boolean.valueOf(true);
                                /* 165 */ Privilege privilege = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur().intValue(), 1);
                                /* 166 */ if (privilege == null) {
                                    /* 167 */ privilege = new Privilege();
                                    /* 168 */ privilege.setIdprivilege(this.privilegeFacadeLocal.nextVal());
                                    /* 169 */ privilege.setIdmenu(m);
                                    /* 170 */ privilege.setIdutilisateur(this.utilisateur);
                                    /* 171 */ this.privilegeFacadeLocal.create(privilege);
                                    /* 172 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "ATTRIBUTION DU PRIVILEGE -> ADMINISTRATEUR à l'utilisateur -> " + this.utilisateur.getNom() + " " + this.utilisateur.getPrenom(), SessionMBean.getUserAccount());
                                    break;
                                }
                                /* 175 */ JsfUtil.addSuccessMessage("Vous Disposez deja du privilege SUPER ADMINISTRATEUR");

                                break;
                            }
                            /* 179 */ Privilege p = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur().intValue(), m.getIdmenu().intValue());
                            /* 180 */ if (p == null) {
                                /* 181 */ p = new Privilege();
                                /* 182 */ p.setIdprivilege(this.privilegeFacadeLocal.nextVal());
                                /* 183 */ p.setIdmenu(m);
                                /* 184 */ p.setIdutilisateur(this.utilisateur);
                                /* 185 */ this.privilegeFacadeLocal.create(p);
                                /* 186 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "ATTRIBUTION DU PRIVILEGE -> " + m.getNom() + " à l'utilisateur -> " + this.utilisateur.getNom() + " " + this.utilisateur.getPrenom(), SessionMBean.getUserAccount());
                            }
                        }

                        /* 191 */ if (!flag.booleanValue()) {
                            /* 192 */ JsfUtil.addSuccessMessage("Opération réussie");
                            continue;
                        }
                        /* 194 */ JsfUtil.addSuccessMessage("Opération réussie - Le privilege ADMINISTRATEUR etait dans la liste et la suite ne plus etre enregistrée");
                    }
                } else {

                    /* 198 */ JsfUtil.addErrorMessage("Le tableau de destination est vide");
                }
            } else {
                /* 201 */ JsfUtil.addErrorMessage("Aucun utilisateur selectionné");
            }
            /* 203 */        } catch (Exception e) {
            /* 204 */ e.printStackTrace();
        }
    }

    public void delete() {
        try {
            /* 211 */ if (this.utilisateur.getIdutilisateur() != null) {
                /* 212 */ if (!this.dualMenu.getTarget().isEmpty()) {
                    /* 213 */ for (Menu m : this.dualMenu.getTarget()) {
                        /* 214 */ this.privilegeFacadeLocal.delete(m.getIdmenu().intValue(), this.utilisateur.getIdutilisateur().intValue());
                        /* 215 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "RETRAIT DU PRIVILEGE -> " + m.getNom() + " à l'utilisateur -> " + this.utilisateur.getNom() + " " + this.utilisateur.getPrenom(), SessionMBean.getUserAccount());
                    }
                    /* 217 */ JsfUtil.addSuccessMessage("Opération réussie");
                } else {
                    /* 219 */ JsfUtil.addErrorMessage("La liste de destination est vide");
                }
            } else {
                /* 222 */ JsfUtil.addErrorMessage("Aucun utilisateur selectionné");
            }
            /* 224 */        } catch (Exception e) {
            /* 225 */ e.printStackTrace();
        }
    }

    public void print() {
        try {
            /* 231 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            /* 232 */ if (p != null) {
                /* 233 */ this.showPrivilegePrintDialog = Boolean.valueOf(true);
                return;
            }
            /* 236 */ p = new Privilege();
            /* 237 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 6);
            /* 238 */ if (p != null) {
                /* 239 */ this.showPrivilegePrintDialog = Boolean.valueOf(true);
                return;
            }
            /* 242 */ this.showPrivilegePrintDialog = Boolean.valueOf(false);
            /* 243 */ JsfUtil.addErrorMessage("Vous n 'avez pas le droit d'imprimer les privileges aux utilisateurs");

        } /* 246 */ catch (Exception e) {
            /* 247 */ e.printStackTrace();
        }
    }
}
