package utils;

import entities.Annee;
import entities.AnneeMois;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import sessions.AnneeFacadeLocal;
import sessions.AnneeMoisFacadeLocal;
import sessions.PrivilegeFacadeLocal;

public class AbstractLoginBean {

    protected boolean showSessionPanel;
    @EJB
    protected AnneeFacadeLocal anneeFacadeLocal;
    protected Annee annee = new Annee();
    protected List<Annee> annees = new ArrayList<>();

    @EJB
    protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;
    protected AnneeMois anneeMois = new AnneeMois();
    protected List<AnneeMois> anneeMoises = new ArrayList<>();

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;

    protected Date date = new Date();

    protected boolean gestionPersonnel;

    protected boolean gestionNote;

    protected boolean parametrage;

    protected boolean showHibernatePanel = false;
    protected String hibernatePassword = "";

    public boolean isGestionPersonnel() {
        return this.gestionPersonnel;
    }

    public void setGestionPersonnel(boolean gestionPersonnel) {
        this.gestionPersonnel = gestionPersonnel;
    }

    public boolean isGestionNote() {
        return this.gestionNote;
    }

    public void setGestionNote(boolean gestionNote) {
        this.gestionNote = gestionNote;
    }

    public boolean isParametrage() {
        return this.parametrage;
    }

    public boolean isShowHibernatePanel() {
        return this.showHibernatePanel;
    }

    public String getHibernatePassword() {
        return this.hibernatePassword;
    }

    public void setHibernatePassword(String hibernatePassword) {
        this.hibernatePassword = hibernatePassword;
    }

    public boolean isShowSessionPanel() {
        return this.showSessionPanel;
    }

    public AnneeMois getAnneeMois() {
        return this.anneeMois;
    }

    public void setAnneeMois(AnneeMois anneeMois) {
        this.anneeMois = anneeMois;
    }

    public List<AnneeMois> getAnneeMoises() {
        return this.anneeMoises;
    }

    public Annee getAnnee() {
        return this.annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public List<Annee> getAnnees() {
        return this.annees;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
