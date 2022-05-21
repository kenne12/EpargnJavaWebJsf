package controllers.utilisateur;

import entities.Utilisateur;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.MouchardFacadeLocal;
import sessions.PrivilegeFacadeLocal;
import sessions.UtilisateurFacadeLocal;

public class AbstractUtilisateurController {

    @EJB
    protected UtilisateurFacadeLocal utilisateurFacadeLocal;
    protected Utilisateur utilisateur;
    protected List<Utilisateur> utilisateurs = new ArrayList<>();

    protected List<Utilisateur> utilisateurActifs = new ArrayList<>();
    protected List<Utilisateur> utilisateurInactifs = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected Boolean showEditSolde = true;

    protected Boolean showUserCreateDialog = false;
    protected Boolean showUserDetailDialog = false;
    protected Boolean showUserDeleteDialog = false;
    protected Boolean showUserEditDialog = false;
    protected Boolean showUserPrintDialog = false;

    protected boolean buttonActif = false;

    protected boolean buttonInactif = true;
    protected String mode = "";

    public Boolean getDetail() {
        return this.detail;
    }

    public void setDetail(Boolean detail) {
        this.detail = detail;
    }

    public Boolean getModifier() {
        return this.modifier;
    }

    public void setModifier(Boolean modifier) {
        this.modifier = modifier;
    }

    public Boolean getConsulter() {
        return this.consulter;
    }

    public void setConsulter(Boolean consulter) {
        this.consulter = consulter;
    }

    public Boolean getImprimer() {
        this.imprimer = this.utilisateurFacadeLocal.findAll().isEmpty();
        return this.imprimer;
    }

    public void setImprimer(Boolean imprimer) {
        this.imprimer = imprimer;
    }

    public Boolean getSupprimer() {
        return this.supprimer;
    }

    public void setSupprimer(Boolean supprimer) {
        this.supprimer = supprimer;
    }

    public Boolean getShowEditSolde() {
        return this.showEditSolde;
    }

    public void setShowEditSolde(Boolean showEditSolde) {
        this.showEditSolde = showEditSolde;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.modifier = this.supprimer = this.detail = (utilisateur == null);
        this.utilisateur = utilisateur;
    }

    public List<Utilisateur> getUtilisateurs() {
        this.utilisateurs = this.utilisateurFacadeLocal.findAll();
        return this.utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public Boolean getShowUserCreateDialog() {
        return this.showUserCreateDialog;
    }

    public void setShowUserCreateDialog(Boolean showUserCreateDialog) {
        this.showUserCreateDialog = showUserCreateDialog;
    }

    public Boolean getShowUserDetailDialog() {
        return this.showUserDetailDialog;
    }

    public void setShowUserDetailDialog(Boolean showUserDetailDialog) {
        this.showUserDetailDialog = showUserDetailDialog;
    }

    public Boolean getShowUserDeleteDialog() {
        return this.showUserDeleteDialog;
    }

    public void setShowUserDeleteDialog(Boolean showUserDeleteDialog) {
        this.showUserDeleteDialog = showUserDeleteDialog;
    }

    public Boolean getShowUserEditDialog() {
        return this.showUserEditDialog;
    }

    public void setShowUserEditDialog(Boolean showUserEditDialog) {
        this.showUserEditDialog = showUserEditDialog;
    }

    public Boolean getShowUserPrintDialog() {
        return this.showUserPrintDialog;
    }

    public void setShowUserPrintDialog(Boolean showUserPrintDialog) {
        this.showUserPrintDialog = showUserPrintDialog;
    }

    public List<Utilisateur> getUtilisateurActifs() {
        this.utilisateurActifs = this.utilisateurFacadeLocal.findByActif(true);
        return this.utilisateurActifs;
    }

    public void setUtilisateurActifs(List<Utilisateur> utilisateurActifs) {
        this.utilisateurActifs = utilisateurActifs;
    }

    public List<Utilisateur> getUtilisateurInactifs() {
        this.utilisateurInactifs = this.utilisateurFacadeLocal.findByActif(false);
        return this.utilisateurInactifs;
    }

    public void setUtilisateurInactifs(List<Utilisateur> utilisateurInactifs) {
        this.utilisateurInactifs = utilisateurInactifs;
    }

    public boolean isButtonActif() {
        return this.buttonActif;
    }

    public void setButtonActif(boolean buttonActif) {
        this.buttonActif = buttonActif;
    }

    public boolean isButtonInactif() {
        return this.buttonInactif;
    }

    public void setButtonInactif(boolean buttonInactif) {
        this.buttonInactif = buttonInactif;
    }
}
