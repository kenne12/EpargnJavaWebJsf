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
        this.utilisateur = new Utilisateur();
    }

    public void prepareCreate() {
        this.utilisateur = new Utilisateur();
        this.mode = "Create";

        try {
            Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            if (p != null) {
                this.showUserCreateDialog = true;
            } else {
                p = new Privilege();
                p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 2);
                if (p != null) {
                    this.showUserCreateDialog = true;
                } else {
                    this.showUserCreateDialog = false;
                    JsfUtil.addErrorMessage("Vous n 'avez pas le privilege d'enregistrer un utilisateur");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareDetail() {
    }

    public void prepareEdit() {
        this.mode = "Edit";

        try {
            Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            if (p != null) {
                this.showUserCreateDialog = true;
                return;
            }
            p = new Privilege();
            p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 3);
            if (p != null) {
                this.showUserCreateDialog = true;
                return;
            }
            this.showUserCreateDialog = false;
            JsfUtil.addErrorMessage("Vous n 'avez pas le privilege de modifier cet utilisateur");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            if (this.mode.equals("Create")) {

                this.utilisateur.setIdutilisateur(this.utilisateurFacadeLocal.nextVal());
                this.utilisateur.setActif(true);
                this.utilisateur.setPassword((new ShaHash()).hash(this.utilisateur.getPassword()));
                this.utilisateurFacadeLocal.create(this.utilisateur);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de l'utilisateur : " + this.utilisateur.getNom() + " " + this.utilisateur.getPrenom(), SessionMBean.getUserAccount());
                JsfUtil.addSuccessMessage("Client enregistré avec succès");
                this.utilisateur = new Utilisateur();
            } else if (this.utilisateur != null) {
                this.utilisateurFacadeLocal.edit(this.utilisateur);
                JsfUtil.addSuccessMessage("Opération réussie");
            } else {
                JsfUtil.addErrorMessage("Aucun utilisateur selectionné");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit() {
    }

    public void delete() {
        try {
            if (this.utilisateur != null) {

                Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
                if (p != null) {
                    this.showUserDeleteDialog = true;
                } else {
                    p = new Privilege();
                    p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 4);
                    if (p != null) {
                        this.showUserDeleteDialog = true;
                    } else {
                        this.showUserDeleteDialog = false;
                        JsfUtil.addErrorMessage("Vous n 'avez pas le privilege de supprimer cet utilisateur");
                        return;
                    }
                }
                this.utilisateurFacadeLocal.remove(this.utilisateur);

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion de l'utilisateur : " + this.utilisateur.getNom() + " " + this.utilisateur.getPrenom(), SessionMBean.getUserAccount());

                JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                JsfUtil.addErrorMessage("Aucun Utilisateur selectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeStatus(Utilisateur utilisateur, String mode) {
        try {
            if (mode.equals("activer")) {
                Privilege privilege = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
                if (privilege != null) {
                    utilisateur.setActif(true);
                    this.utilisateurFacadeLocal.edit(utilisateur);
                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Activation du compte de l'utilisateur : " + utilisateur.getNom() + " " + utilisateur.getPrenom(), SessionMBean.getUserAccount());
                    JsfUtil.addSuccessMessage("Operation réussie");
                    return;
                }
                privilege = new Privilege();
                privilege = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 18);
                if (privilege != null) {
                    utilisateur.setActif(true);
                    this.utilisateurFacadeLocal.edit(utilisateur);
                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Activation du compte de l'utilisateur : " + utilisateur.getNom() + " " + utilisateur.getPrenom(), SessionMBean.getUserAccount());
                    JsfUtil.addSuccessMessage("Operation réussie");
                    return;
                }
                JsfUtil.addErrorMessage("Vous n 'avez pas le privilege d activer un compte utilisateur");
                return;
            }

            Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            if (p != null) {
                utilisateur.setActif(false);
                this.utilisateurFacadeLocal.edit(utilisateur);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Désativation du compte de l'utilisateur : " + utilisateur.getNom() + " " + utilisateur.getPrenom(), SessionMBean.getUserAccount());
                JsfUtil.addSuccessMessage("Operation réussie");
                return;
            }
            p = new Privilege();
            p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 19);
            if (p != null) {
                utilisateur.setActif(false);
                this.utilisateurFacadeLocal.edit(utilisateur);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Desactivation du compte de l'utilisateur : " + utilisateur.getNom() + " " + utilisateur.getPrenom(), SessionMBean.getUserAccount());
                JsfUtil.addSuccessMessage("Operation réussie");
                return;
            }
            JsfUtil.addErrorMessage("Vous n 'avez pas le privilege de désactiver un compte utilisateur");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public void print() {
        Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
        if (p != null) {
            this.showUserPrintDialog = true;
        } else {
            p = new Privilege();
            p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 4);
            if (p != null) {
                this.showUserPrintDialog = true;
            } else {
                this.showUserPrintDialog = false;
                JsfUtil.addErrorMessage("Vous n 'avez pas le privilege d'imprimer la liste des utilisateurs");
                return;
            }
        }
    }
}
