package controllers.privilege;

import entities.Menu;
import entities.Privilege;
import entities.Utilisateur;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import org.primefaces.model.DualListModel;
import sessions.MenuFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.PrivilegeFacadeLocal;
import sessions.UtilisateurFacadeLocal;

public class AbstractPrivilegeController {

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;
    protected Privilege privilege;
    protected List<Privilege> privileges = new ArrayList<>();

    @EJB
    protected MenuFacadeLocal menuFacadeLocal;
    protected Menu menu;
    protected List<Menu> menus = new ArrayList<>();
    protected DualListModel<Menu> dualMenu = new DualListModel();

    @EJB
    protected UtilisateurFacadeLocal utilisateurFacadeLocal;
    protected Utilisateur utilisateur;
    protected List<Utilisateur> utilisateurs = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    protected String fileName;

    protected boolean detail = true;

    protected boolean supprimer = true;
    protected boolean imprimer = true;
    protected Boolean showPrivilegeCreateDialog = false;
    protected Boolean showPrivilegeDeleteDialog = false;
    protected Boolean showPrivilegePrintDialog = false;

    public boolean isDetail() {
        return this.detail;
    }

    public boolean isSupprimer() {
        return this.supprimer;
    }

    public boolean isImprimer() {
        this.imprimer = this.privilegeFacadeLocal.findAll().isEmpty();
        return this.imprimer;
    }

    public Menu getMenu() {
        return this.menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Menu> getMenus() {
        return this.menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public DualListModel<Menu> getDualMenu() {
        return this.dualMenu;
    }

    public void setDualMenu(DualListModel<Menu> dualMenu) {
        this.dualMenu = dualMenu;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Utilisateur> getUtilisateurs() {
        return this.utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public List<Privilege> getPrivileges() {
        this.privileges = this.privilegeFacadeLocal.findAll1();
        return this.privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Privilege getPrivilege() {
        return this.privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public Boolean getShowPrivilegeCreateDialog() {
        return this.showPrivilegeCreateDialog;
    }

    public void setShowPrivilegeCreateDialog(Boolean showPrivilegeCreateDialog) {
        this.showPrivilegeCreateDialog = showPrivilegeCreateDialog;
    }

    public Boolean getShowPrivilegeDeleteDialog() {
        return this.showPrivilegeDeleteDialog;
    }

    public void setShowPrivilegeDeleteDialog(Boolean showPrivilegeDeleteDialog) {
        this.showPrivilegeDeleteDialog = showPrivilegeDeleteDialog;
    }

    public Boolean getShowPrivilegePrintDialog() {
        return this.showPrivilegePrintDialog;
    }

    public void setShowPrivilegePrintDialog(Boolean showPrivilegePrintDialog) {
        this.showPrivilegePrintDialog = showPrivilegePrintDialog;
    }
}
