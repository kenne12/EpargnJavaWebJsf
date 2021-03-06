package utils;

import entities.Annee;
import entities.AnneeMois;
import entities.Utilisateur;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionMBean {

    public static HttpSession getSession() {
        /* 19 */ return (HttpSession) FacesContext.getCurrentInstance()
                /* 20 */.getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        /* 24 */ return (HttpServletRequest) FacesContext.getCurrentInstance()
                /* 25 */.getExternalContext().getRequest();
    }

    public static String getUserName() {
        /* 30 */ HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        /* 31 */ return session.getAttribute("login").toString();
    }

    public static String getUserId() {
        /* 35 */ HttpSession session = getSession();
        /* 36 */ if (session != null) {
            /* 37 */ return (String) session.getAttribute("utilisateurid");
        }
        /* 39 */ return null;
    }

    public static Utilisateur getUserAccount() {
        /* 44 */ HttpSession session = getSession();
        /* 45 */ if (session != null) {
            /* 46 */ return (Utilisateur) session.getAttribute("compte");
        }
        /* 48 */ return null;
    }

    public static Date getDate() {
        /* 53 */ HttpSession session = getSession();
        /* 54 */ if (session != null) {
            /* 55 */ return (Date) session.getAttribute("date");
        }
        /* 57 */ return null;
    }

    public static AnneeMois getMois() {
        /* 62 */ HttpSession session = getSession();
        /* 63 */ if (session != null) {
            /* 64 */ return (AnneeMois) session.getAttribute("mois");
        }
        /* 66 */ return null;
    }

    public static Annee getAnnee() {
        /* 71 */ HttpSession session = getSession();
        /* 72 */ if (session != null) {
            /* 73 */ return (Annee) session.getAttribute("annee");
        }
        /* 75 */ return null;
    }
}
