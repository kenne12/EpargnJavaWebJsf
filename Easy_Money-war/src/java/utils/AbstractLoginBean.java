package utils;

import entities.Annee;
import entities.AnneeMois;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import sessions.AnneeFacadeLocal;
import sessions.AnneeMoisFacadeLocal;

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

    protected Date date = new Date();

    protected boolean gestionPersonnel;

    protected boolean gestionNote;

    protected boolean gestionPrivilege;
    protected boolean gestionDiscipline;
    protected boolean gestionInscription;
    protected boolean gestionEtat;
    protected boolean parametrage;
    protected boolean bibliotheque;

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

    public boolean isGestionPrivilege() {
        return this.gestionPrivilege;
    }

    public boolean isGestionDiscipline() {
        return this.gestionDiscipline;
    }

    public boolean isGestionInscription() {
        return this.gestionInscription;
    }

    public boolean isGestionEtat() {
        return this.gestionEtat;
    }

    public boolean isParametrage() {
        /* 108 */ return this.parametrage;
    }

    public boolean isBibliotheque() {
        /* 116 */ return this.bibliotheque;
    }

    public boolean isShowHibernatePanel() {
        /* 189 */ return this.showHibernatePanel;
    }

    public String getHibernatePassword() {
        /* 197 */ return this.hibernatePassword;
    }

    public void setHibernatePassword(String hibernatePassword) {
        /* 201 */ this.hibernatePassword = hibernatePassword;
    }

    public boolean isShowSessionPanel() {
        /* 205 */ return this.showSessionPanel;
    }

    public AnneeMois getAnneeMois() {
        /* 209 */ return this.anneeMois;
    }

    public void setAnneeMois(AnneeMois anneeMois) {
        /* 213 */ this.anneeMois = anneeMois;
    }

    public List<AnneeMois> getAnneeMoises() {
        try {
            this.anneeMoises = this.anneeMoisFacadeLocal.findByEtat(Boolean.valueOf(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.anneeMoises;
    }

    public void setAnneeMoises(List<AnneeMois> anneeMoises) {
        /* 226 */ this.anneeMoises = anneeMoises;
    }

    public Annee getAnnee() {
        /* 230 */ return this.annee;
    }

    public void setAnnee(Annee annee) {
        /* 234 */ this.annee = annee;
    }

    public List<Annee> getAnnees() {
        /* 238 */ return this.annees;
    }

    public void setAnnees(List<Annee> annees) {
        /* 242 */ this.annees = annees;
    }

    public Date getDate() {
        /* 246 */ return this.date;
    }

    public void setDate(Date date) {
        /* 250 */ this.date = date;
    }
}
