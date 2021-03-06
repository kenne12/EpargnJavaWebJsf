package utils;

import entities.Utilisateur;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class UtilitaireSession {
    /*  27 */ private static final utils.UtilitaireSession instance = new utils.UtilitaireSession();

    /*  31 */    private final String user = "user";

    public static utils.UtilitaireSession getInstance() {
        /*  48 */ return instance;
    }

    public void destroy() {
        /*  59 */ FacesContext fc = FacesContext.getCurrentInstance();
        /*  60 */ getSession(fc).invalidate();
    }

    private boolean isContextOk(FacesContext fc) {
        /*  71 */ boolean res = (fc != null && fc.getExternalContext() != null && fc.getExternalContext().getSession(false) != null);
        /*  72 */ return res;
    }

    private HttpSession getSession(FacesContext fc) {
        /*  79 */ return (HttpSession) fc.getExternalContext().getSession(false);
    }

    public Object get(String cle) {
        /*  89 */ FacesContext fc = FacesContext.getCurrentInstance();
        /*  90 */ Object res = null;
        /*  91 */ if (isContextOk(fc)) {
            /*  92 */ res = getSession(fc).getAttribute(cle);
        }
        /*  94 */ return res;
    }

    public void set(String cle, Object valeur) {
        /* 104 */ FacesContext fc = FacesContext.getCurrentInstance();
        /* 105 */ if (fc != null && fc.getExternalContext() != null) {
            /* 106 */ getSession(fc).setAttribute(cle, valeur);
        }
    }

    public void setuser(Utilisateur utilisateur) {
        /* 116 */ set("user", utilisateur);
    }

    public Utilisateur getuser() {
        /* 125 */ return (Utilisateur) get("user");
    }
}
