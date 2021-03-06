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
    /*  30 */    protected List<Privilege> privileges = new ArrayList<>();

    @EJB
    protected MenuFacadeLocal menuFacadeLocal;
    protected Menu menu;
    /*  35 */    protected List<Menu> menus = new ArrayList<>();
    /*  36 */    protected DualListModel<Menu> dualMenu = new DualListModel();

    @EJB
    protected UtilisateurFacadeLocal utilisateurFacadeLocal;
    protected Utilisateur utilisateur;
    /*  41 */    protected List<Utilisateur> utilisateurs = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    protected String fileName;

    protected boolean detail = true;

    protected boolean supprimer = true;
    protected boolean imprimer = true;
    /*  52 */    protected Boolean showPrivilegeCreateDialog = Boolean.valueOf(false);
    /*  53 */    protected Boolean showPrivilegeDeleteDialog = Boolean.valueOf(false);
    /*  54 */    protected Boolean showPrivilegePrintDialog = Boolean.valueOf(false);

    public boolean isDetail() {
        /*  57 */ return this.detail;
    }

    public boolean isSupprimer() {
        /*  63 */ return this.supprimer;
    }

    public boolean isImprimer() {
        /*  67 */ this.imprimer = this.privilegeFacadeLocal.findAll().isEmpty();
        /*  68 */ return this.imprimer;
    }

    public Menu getMenu() {
        /*  72 */ return this.menu;
    }

    public void setMenu(Menu menu) {
        /*  76 */ this.menu = menu;
    }

    public List<Menu> getMenus() {
        /*  80 */ return this.menus;
    }

    public void setMenus(List<Menu> menus) {
        /*  84 */ this.menus = menus;
    }

    public DualListModel<Menu> getDualMenu() {
        /*  88 */ return this.dualMenu;
    }

    public void setDualMenu(DualListModel<Menu> dualMenu) {
        /*  92 */ this.dualMenu = dualMenu;
    }

    public Utilisateur getUtilisateur() {
        /*  96 */ return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        /* 100 */ this.utilisateur = utilisateur;
    }

    public List<Utilisateur> getUtilisateurs() {
        /* 104 */ return this.utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        /* 108 */ this.utilisateurs = utilisateurs;
    }

    public List<Privilege> getPrivileges() {
        /* 112 */ this.privileges = this.privilegeFacadeLocal.findAll1();
        /* 113 */ return this.privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        /* 117 */ this.privileges = privileges;
    }

    public Privilege getPrivilege() {
        /* 121 */ return this.privilege;
    }

    public void setPrivilege(Privilege privilege) {
        /* 125 */ this.privilege = privilege;
    }

    public Boolean getShowPrivilegeCreateDialog() {
        /* 129 */ return this.showPrivilegeCreateDialog;
    }

    public void setShowPrivilegeCreateDialog(Boolean showPrivilegeCreateDialog) {
        /* 133 */ this.showPrivilegeCreateDialog = showPrivilegeCreateDialog;
    }

    public Boolean getShowPrivilegeDeleteDialog() {
        /* 137 */ return this.showPrivilegeDeleteDialog;
    }

    public void setShowPrivilegeDeleteDialog(Boolean showPrivilegeDeleteDialog) {
        /* 141 */ this.showPrivilegeDeleteDialog = showPrivilegeDeleteDialog;
    }

    public Boolean getShowPrivilegePrintDialog() {
        /* 145 */ return this.showPrivilegePrintDialog;
    }

    public void setShowPrivilegePrintDialog(Boolean showPrivilegePrintDialog) {
        /* 149 */ this.showPrivilegePrintDialog = showPrivilegePrintDialog;
    }
}
