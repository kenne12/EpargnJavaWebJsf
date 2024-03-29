package utils;

import entities.AnneeMois;
import entities.Mouchard;
import entities.Privilege;
import entities.Utilisateur;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean extends AbstractLoginBean implements Serializable {

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Mouchard traceur;
    @EJB
    private UtilisateurFacadeLocal utilisateurFacadeLocal;

    public Mouchard getTraceur() {
        return this.traceur;
    }

    public void setTraceur(Mouchard traceur) {
        this.traceur = traceur;
    }

    private Utilisateur utilisateur = new Utilisateur();

    private Utilisateur utilisateurConnected;
    String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

    @PostConstruct
    public void init() {
        this.utilisateur = new Utilisateur();
    }

    public void login() {
        try {

            this.utilisateur = this.utilisateurFacadeLocal
                    .login(this.utilisateur.getLogin(), (new ShaHash()).hash(this.utilisateur.getPassword()));
            if (this.utilisateur != null) {
                JsfUtil.addSuccessMessage("Operation reussie");

                this.showSessionPanel = true;

                HttpSession session = SessionMBean.getSession();
                session.setAttribute("compte", this.utilisateur);

                this.annees = this.anneeFacadeLocal.findByEtat(true);
                if (this.annees.size() == 1) {
                    this.anneeMoises = this.anneeMoisFacadeLocal.findByAnneeEtat(this.annees.get(0).getIdannee(), true);
                    this.annee = this.annees.get(0);
                    filterDate(Date.from(Instant.now()));
                }

                List<Privilege> privilegesTemp = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur());
                List<Long> accesses = new ArrayList<>();
                List<String> access = new ArrayList<>();

                privilegesTemp.stream().map((p) -> {
                    accesses.add(Long.valueOf(p.getIdmenu().getIdmenu()));
                    return p;
                }).map((p) -> p.getIdmenu().getRessource().split(";")).forEachOrdered((menus) -> {
                    for (String temp : menus) {
                        if (!access.contains(temp)) {
                            access.add(temp);
                        }
                    }
                });

                session.setAttribute("accesses", accesses);
                session.setAttribute("access", access);

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Connexion", this.utilisateur);

                FacesContext.getCurrentInstance().getExternalContext().redirect(this.sc + "/index.html");
            } else {
                this.utilisateur = new Utilisateur();
                JsfUtil.addErrorMessage("Login ou mot de passe incorrect");
            }
        } catch (Exception ex) {
            this.utilisateur = new Utilisateur();
            ex.printStackTrace();
        }
    }

    public void updateMois() {
        try {
            anneeMoises.clear();
            if (annee.getIdannee().equals(0)) {
                JsfUtil.addErrorMessage("Selectionner une année");
                return;
            }
            anneeMoises.addAll(this.anneeMoisFacadeLocal.findByAnneeEtat(annee.getIdannee(), true));
            filterDate(Date.from(Instant.now()));
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Echec");
        }
    }

    public void updateCalendar() {
        try {
            if (this.anneeMois.getIdAnneeMois().equals(0)) {
                JsfUtil.addErrorMessage("Echec : sélectionner une année");
                return;
            }
            this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Echec");
        }
    }

    public void deconnexion() {
        this.traceur = new Mouchard();
        Utilisateur user = SessionMBean.getUserAccount();
        Utilitaires.saveOperation(this.mouchardFacadeLocal, "Déconnexion", user);
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

            UtilitaireSession.getInstance().setuser(null);
            FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/login.html");
        } catch (IOException ex) {
            Logger.getLogger(utils.LoginBean.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
    }

    public void hibbernate() {
        try {
            this.showHibernatePanel = true;
            this.hibernatePassword = "";
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void unHibbernate() {
        try {
            if (this.hibernatePassword.equals(SessionMBean.getUserAccount().getPassword())) {
                this.showHibernatePanel = false;
            } else {
                this.showHibernatePanel = true;
                JsfUtil.addErrorMessage("Mot de passe incorrect");
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public Utilisateur getUserconnected() {
        this.utilisateurConnected = UtilitaireSession.getInstance().getuser();
        String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        if (this.utilisateurConnected == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/login.html");
            } catch (IOException ex) {
                Logger.getLogger(utils.LoginBean.class.getName()).log(Level.SEVERE, (String) null, ex);
            }
            System.out.println("Uitlisateur déconnecté +++++++++++++++++++ ");
        }
        return this.utilisateurConnected;
    }

    public void setPriv() {
        watcher();
    }

    public static void watcher() {
        try {
            if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("compte")) {
                String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/login.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Object getUser() {
        return this.utilisateur;
    }

    public void setUser(Object user) {
        this.utilisateur = (Utilisateur) user;
    }

    public void initSession() {
        try {
            if (this.annee.getIdannee() != 0) {

                if (this.anneeMois.getIdAnneeMois() != 0) {

                    if (this.date.equals(this.anneeMois.getDateDebut()) || (this.date.after(this.anneeMois.getDateDebut()) && this.date.equals(this.anneeMois.getDateFin())) || this.date.before(this.anneeMois.getDateFin())) {

                        HttpSession session = SessionMBean.getSession();

                        this.annee = this.anneeFacadeLocal.find(this.annee.getIdannee());
                        session.setAttribute("mois", this.anneeMois);
                        session.setAttribute("date", this.date);
                        session.setAttribute("annee", this.annee);

                        this.showSessionPanel = false;
                    } else {
                        JsfUtil.addErrorMessage("Choisir une date valable dans le mois choisi");
                    }
                } else {
                    JsfUtil.addErrorMessage("Veuillez selectionner une année");
                }
            } else {
                JsfUtil.addErrorMessage("Veuillez selectionner une année");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Echec");
        }
    }

    private void filterDate(Date date) {
        for (AnneeMois a : this.anneeMoises) {
            try {
                if ((a.getDateDebut().before(date) || a.getDateDebut().equals(date)) && (a.getDateFin().after(date) || a.getDateFin().equals(date))) {
                    this.anneeMois = a;
                    this.annee = a.getIdannee();
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
