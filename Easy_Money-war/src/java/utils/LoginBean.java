package utils;

import entities.AnneeMois;
import entities.Mouchard;
import entities.Utilisateur;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import sessions.MouchardFacadeLocal;
import sessions.UtilisateurFacadeLocal;
import utils.AbstractLoginBean;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.ShaHash;
import utils.UtilitaireSession;
import utils.Utilitaires;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean
        extends AbstractLoginBean
        implements Serializable {

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Mouchard traceur;
    @EJB
    private UtilisateurFacadeLocal utilisateurFacadeLocal;

    public Mouchard getTraceur() {
        /*  40 */ return this.traceur;
    }

    public void setTraceur(Mouchard traceur) {
        /*  44 */ this.traceur = traceur;
    }

    /*  49 */    private Utilisateur utilisateur = new Utilisateur();

    private Utilisateur utilisateurConnected;
    /*  52 */    String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

    @PostConstruct
    public void init() {
        /*  56 */ this.utilisateur = new Utilisateur();
    }

    public void login() {
        try {
            /*  65 */ System.out.println("password : " + (new ShaHash()).hash(this.utilisateur.getPassword()));
            /*  66 */ this.utilisateur = this.utilisateurFacadeLocal.login(this.utilisateur.getLogin(), (new ShaHash()).hash(this.utilisateur.getPassword()));
            /*  67 */ if (this.utilisateur != null) {
                /*  68 */ JsfUtil.addSuccessMessage("Operation reussie");

                /*  70 */ this.showSessionPanel = true;

                /*  72 */ HttpSession session = SessionMBean.getSession();
                /*  73 */ session.setAttribute("compte", this.utilisateur);
                /*  74 */ this.anneeMoises = this.anneeMoisFacadeLocal.findByEtat(Boolean.valueOf(true));
                /*  75 */ this.annees = this.anneeFacadeLocal.findByEtat(true);
                /*  76 */ filterDate(new Date());

                /*  78 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Connexion", this.utilisateur);

                /*  80 */ FacesContext.getCurrentInstance().getExternalContext().redirect(this.sc + "/index.html");
            } else {
                /*  82 */ this.utilisateur = new Utilisateur();
                /*  83 */ JsfUtil.addErrorMessage("Login ou mot de passe incorrect");
            }
            /*  85 */        } catch (Exception ex) {
            /*  86 */ this.utilisateur = new Utilisateur();
            /*  87 */ ex.printStackTrace();
        }
    }

    public void updateMois() {
        try {
            /*  93 */ if (this.annee.getIdannee().intValue() == 0) {
                /*  94 */ JsfUtil.addErrorMessage("Selectionner une année");
            } else {
                /*  96 */ this.anneeMoises = this.anneeMoisFacadeLocal.findByAnnee(this.annee);
            }
            /*  98 */        } catch (Exception e) {
            /*  99 */ e.printStackTrace();
            /* 100 */ JsfUtil.addErrorMessage("Echec");
        }
    }

    public void updateCalendar() {
        try {
            /* 106 */ if (this.anneeMois.getIdAnneeMois().intValue() == 0) {
                /* 107 */ JsfUtil.addErrorMessage("Echec : sélectionner une année");
            } else {
                /* 109 */ this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());
            }
            /* 111 */        } catch (Exception e) {
            /* 112 */ e.printStackTrace();
            /* 113 */ JsfUtil.addErrorMessage("Echec");
        }
    }

    public void deconnexion() {
        /* 119 */ this.traceur = new Mouchard();
        /* 120 */ Utilisateur user = SessionMBean.getUserAccount();
        /* 121 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Déconnexion", user);
        try {
            /* 123 */ FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            /* 124 */ FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            /* 125 */ String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

            /* 127 */ UtilitaireSession.getInstance().setuser(null);
            /* 128 */ FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/login.html");
        } /* 130 */ catch (IOException ex) {
            /* 131 */ Logger.getLogger(utils.LoginBean.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }

    public void hibbernate() {
        try {
            /* 138 */ this.showHibernatePanel = true;
            /* 139 */ this.hibernatePassword = "";
            /* 140 */        } catch (Exception e) {
            /* 141 */ e.getMessage();
        }
    }

    public void unHibbernate() {
        try {
            /* 147 */ if (this.hibernatePassword.equals(SessionMBean.getUserAccount().getPassword())) {
                /* 148 */ this.showHibernatePanel = false;
            } else {
                /* 150 */ this.showHibernatePanel = true;
                /* 151 */ JsfUtil.addErrorMessage("Mot de passe incorrect");
            }
            /* 153 */        } catch (Exception e) {
            /* 154 */ e.getMessage();
        }
    }

    public Utilisateur getUserconnected() {
        /* 159 */ this.utilisateurConnected = UtilitaireSession.getInstance().getuser();
        /* 160 */ String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        /* 161 */ if (this.utilisateurConnected == null) {
            try {
                /* 163 */ FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/login.html");
                /* 164 */            } catch (IOException ex) {
                /* 165 */ Logger.getLogger(utils.LoginBean.class.getName()).log(Level.SEVERE, (String) null, ex);
            }
            /* 167 */ System.out.println("Uitlisateur déconnecté +++++++++++++++++++ ");
        }
        /* 169 */ return this.utilisateurConnected;
    }

    public void setPriv() {
        /* 173 */ watcher();
    }

    public static void watcher() {
        try {
            /* 178 */ if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("compte")) {
                /* 179 */ String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                /* 180 */ FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/login.html");
            }
            /* 182 */        } catch (Exception e) {
            /* 183 */ e.printStackTrace();
        }
    }

    public Utilisateur getUtilisateur() {
        /* 188 */ return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        /* 192 */ this.utilisateur = utilisateur;
    }

    public Object getUser() {
        /* 196 */ return this.utilisateur;
    }

    public void setUser(Object user) {
        /* 200 */ this.utilisateur = (Utilisateur) user;
    }

    public void initSession() {
        try {
            /* 206 */ if (this.annee.getIdannee().intValue() != 0) {

                /* 208 */ if (this.anneeMois.getIdAnneeMois().intValue() != 0) {

                    /* 210 */ if (this.date.equals(this.anneeMois.getDateDebut()) || (this.date.after(this.anneeMois.getDateDebut()) && this.date.equals(this.anneeMois.getDateFin())) || this.date.before(this.anneeMois.getDateFin())) {

                        /* 212 */ HttpSession session = SessionMBean.getSession();

                        /* 214 */ this.annee = this.anneeFacadeLocal.find(this.annee.getIdannee());
                        /* 215 */ session.setAttribute("mois", this.anneeMois);
                        /* 216 */ session.setAttribute("date", this.date);
                        /* 217 */ session.setAttribute("annee", this.annee);

                        /* 219 */ this.showSessionPanel = false;
                    } else {
                        /* 221 */ JsfUtil.addErrorMessage("Choisir une date valable dans le mois choisi");
                    }
                } else {
                    /* 224 */ JsfUtil.addErrorMessage("Veuillez selectionner une année");
                }
            } else {
                /* 227 */ JsfUtil.addErrorMessage("Veuillez selectionner une année");
            }
            /* 229 */        } catch (Exception e) {
            /* 230 */ e.printStackTrace();
            /* 231 */ JsfUtil.addErrorMessage("Echec");
        }
    }

    private void filterDate(Date date) {
        /* 236 */ for (AnneeMois a : this.anneeMoises) {
            try {
                /* 238 */ if ((a.getDateDebut().before(date) || a.getDateDebut().equals(date)) && ( /* 239 */a.getDateFin().after(date) || a.getDateFin().equals(date))) {
                    /* 240 */ this.anneeMois = a;
                    /* 241 */ this.annee = a.getIdannee();
                    /* 242 */ System.err.println("retouvé");

                    break;
                }
                /* 246 */            } catch (Exception e) {
                /* 247 */ e.printStackTrace();
            }
        }
    }
}
