package controllers.privilege;

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
        this.privilege = new Privilege();
        this.utilisateur = new Utilisateur();
        this.menu = new Menu();
    }

    public void prepareCreate() {
        this.dualMenu.getSource().clear();
        this.dualMenu.getTarget().clear();
        this.utilisateurs = this.utilisateurFacadeLocal.findByActif(true);

        try {
            Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            if (p != null) {
                this.showPrivilegeCreateDialog = true;
                return;
            }
            p = new Privilege();
            p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 5);
            if (p != null) {
                this.showPrivilegeCreateDialog = true;
                return;
            }
            this.showPrivilegeCreateDialog = false;
            JsfUtil.addErrorMessage("Vous n 'avez pas le droit d'attribuer les privileges aux utilisateurs");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareDelete() {
        this.dualMenu.getSource().clear();
        this.dualMenu.getTarget().clear();
        this.utilisateurs = this.utilisateurFacadeLocal.findByActif(true);

        try {
            Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            if (p != null) {
                this.showPrivilegeDeleteDialog = true;
                return;
            }
            p = new Privilege();
            p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 6);
            if (p != null) {
                this.showPrivilegeDeleteDialog = true;
                return;
            }
            this.showPrivilegeDeleteDialog = false;
            JsfUtil.addErrorMessage("Vous n 'avez pas le droit de retirer les privileges aux utilisateurs");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleUserChange() {
        this.dualMenu.getSource().clear();
        this.dualMenu.getTarget().clear();
        try {
            if (this.utilisateur.getIdutilisateur() != null) {
                this.utilisateur = this.utilisateurFacadeLocal.find(this.utilisateur.getIdutilisateur());
                List<Privilege> privilegeTemp = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur());
                if (privilegeTemp.isEmpty()) {
                    this.dualMenu.setSource(this.menuFacadeLocal.findAll());
                } else {
                    Privilege privilegeTemp1 = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur().intValue(), 1);
                    if (privilegeTemp1 != null) {
                        JsfUtil.addWarningMessage("Vous disposez deja de tous les droit possibles dans ce systeme");
                    } else {

                        List<Menu> menus1 = this.menuFacadeLocal.findAll();
                        List<Menu> menus2 = new ArrayList<>();
                        List<Privilege> privilegeTemp2 = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur());

                        for (Privilege p : privilegeTemp2) {
                            menus2.add(p.getIdmenu());
                        }

                        for (Menu m : menus1) {
                            if (!menus2.contains(m)) {
                                this.dualMenu.getSource().add(m);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleUserChange1() {
        this.dualMenu.getSource().clear();
        this.dualMenu.getTarget().clear();
        try {
            if (this.utilisateur.getIdutilisateur() != null) {
                this.utilisateur = this.utilisateurFacadeLocal.find(this.utilisateur.getIdutilisateur());
                List<Privilege> privilegeTemp = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur());
                if (privilegeTemp.isEmpty()) {
                    this.dualMenu.setSource(this.menuFacadeLocal.findAll());
                } else {
                    List<Menu> menus1 = new ArrayList<>();

                    List<Privilege> privilegeTemp2 = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur());

                    for (Privilege p : privilegeTemp2) {
                        menus1.add(p.getIdmenu());
                    }
                    this.dualMenu.getSource().addAll(menus1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        try {
            if (this.utilisateur.getIdutilisateur() != null) {
                this.utilisateur = this.utilisateurFacadeLocal.find(this.utilisateur.getIdutilisateur());
                if (!this.dualMenu.getTarget().isEmpty()) {
                    Boolean flag = (false);
                    for (Menu m : this.dualMenu.getTarget()) {

                        if (!flag) {

                            if (m.getIdmenu() == 1) {
                                flag = (true);
                                Privilege privilege = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur().intValue(), 1);
                                if (privilege == null) {
                                    privilege = new Privilege();
                                    privilege.setIdprivilege(this.privilegeFacadeLocal.nextVal());
                                    privilege.setIdmenu(m);
                                    privilege.setIdutilisateur(this.utilisateur);
                                    this.privilegeFacadeLocal.create(privilege);
                                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "ATTRIBUTION DU PRIVILEGE -> ADMINISTRATEUR à l'utilisateur -> " + this.utilisateur.getNom() + " " + this.utilisateur.getPrenom(), SessionMBean.getUserAccount());
                                    break;
                                }
                                JsfUtil.addSuccessMessage("Vous Disposez deja du privilege SUPER ADMINISTRATEUR");
                                break;
                            }
                            Privilege p = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur().intValue(), m.getIdmenu().intValue());
                            if (p == null) {
                                p = new Privilege();
                                p.setIdprivilege(this.privilegeFacadeLocal.nextVal());
                                p.setIdmenu(m);
                                p.setIdutilisateur(this.utilisateur);
                                this.privilegeFacadeLocal.create(p);
                                Utilitaires.saveOperation(this.mouchardFacadeLocal, "ATTRIBUTION DU PRIVILEGE -> " + m.getNom() + " à l'utilisateur -> " + this.utilisateur.getNom() + " " + this.utilisateur.getPrenom(), SessionMBean.getUserAccount());
                            }
                        }

                        if (!flag) {
                            JsfUtil.addSuccessMessage("Opération réussie");
                            continue;
                        }
                        JsfUtil.addSuccessMessage("Opération réussie - Le privilege ADMINISTRATEUR etait dans la liste et la suite ne plus etre enregistrée");
                    }
                } else {
                    JsfUtil.addErrorMessage("Le tableau de destination est vide");
                }
            } else {
                JsfUtil.addErrorMessage("Aucun utilisateur selectionné");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        try {
            if (this.utilisateur.getIdutilisateur() != null) {
                if (!this.dualMenu.getTarget().isEmpty()) {
                    for (Menu m : this.dualMenu.getTarget()) {
                        this.privilegeFacadeLocal.delete(m.getIdmenu(), this.utilisateur.getIdutilisateur());
                        Utilitaires.saveOperation(this.mouchardFacadeLocal, "RETRAIT DU PRIVILEGE -> " + m.getNom() + " à l'utilisateur -> " + this.utilisateur.getNom() + " " + this.utilisateur.getPrenom(), SessionMBean.getUserAccount());
                    }
                    JsfUtil.addSuccessMessage("Opération réussie");
                } else {
                    JsfUtil.addErrorMessage("La liste de destination est vide");
                }
            } else {
                JsfUtil.addErrorMessage("Aucun utilisateur selectionné");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print() {
        try {
            Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            if (p != null) {
                this.showPrivilegePrintDialog = true;
                return;
            }
            p = new Privilege();
            p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 6);
            if (p != null) {
                this.showPrivilegePrintDialog = true;
                return;
            }
            this.showPrivilegePrintDialog = false;
            JsfUtil.addErrorMessage("Vous n 'avez pas le droit d'imprimer les privileges aux utilisateurs");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
