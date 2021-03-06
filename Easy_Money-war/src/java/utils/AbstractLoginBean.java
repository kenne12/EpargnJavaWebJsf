/*     */ package utils;
/*     */ 
/*     */ import entities.Annee;
/*     */ import entities.AnneeMois;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.ejb.EJB;
/*     */ import sessions.AnneeFacadeLocal;
/*     */ import sessions.AnneeMoisFacadeLocal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AbstractLoginBean
/*     */ {
/*     */   protected boolean showSessionPanel;
/*     */   @EJB
/*     */   protected AnneeFacadeLocal anneeFacadeLocal;
/*  27 */   protected Annee annee = new Annee();
/*  28 */   protected List<Annee> annees = new ArrayList<>();
/*     */   
/*     */   @EJB
/*     */   protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;
/*  32 */   protected AnneeMois anneeMois = new AnneeMois();
/*  33 */   protected List<AnneeMois> anneeMoises = new ArrayList<>();
/*     */   
/*  35 */   protected Date date = new Date();
/*     */   
/*     */   protected boolean gestionPersonnel;
/*     */   
/*     */   protected boolean gestionNote;
/*     */   
/*     */   protected boolean gestionPrivilege;
/*     */   protected boolean gestionDiscipline;
/*     */   protected boolean gestionInscription;
/*     */   protected boolean gestionEtat;
/*     */   protected boolean parametrage;
/*     */   protected boolean bibliotheque;
/*  47 */   protected String gestionPersonnelVisible = "hidden";
/*  48 */   protected String gestionNoteVisible = "hidden";
/*  49 */   protected String gestionPrivilegeVisible = "hidden";
/*  50 */   protected String gestionDisciplineVisible = "hidden";
/*  51 */   protected String gestionInscriptionVisible = "hidden";
/*  52 */   protected String gestionEtatVisible = "hidden";
/*  53 */   protected String parametrageVisible = "hidden";
/*  54 */   protected String bibliothequeVisible = "hidden";
/*     */   
/*     */   protected boolean showHibernatePanel = false;
/*  57 */   protected String hibernatePassword = "";
/*     */   
/*     */   public boolean isGestionPersonnel() {
/*  60 */     return this.gestionPersonnel;
/*     */   }
/*     */   
/*     */   public void setGestionPersonnel(boolean gestionPersonnel) {
/*  64 */     this.gestionPersonnel = gestionPersonnel;
/*     */   }
/*     */   
/*     */   public boolean isGestionNote() {
/*  68 */     return this.gestionNote;
/*     */   }
/*     */   
/*     */   public void setGestionNote(boolean gestionNote) {
/*  72 */     this.gestionNote = gestionNote;
/*     */   }
/*     */   
/*     */   public boolean isGestionPrivilege() {
/*  76 */     return this.gestionPrivilege;
/*     */   }
/*     */   
/*     */   public void setGestionPrivilege(boolean gestionPrivilege) {
/*  80 */     this.gestionPrivilege = gestionPrivilege;
/*     */   }
/*     */   
/*     */   public boolean isGestionDiscipline() {
/*  84 */     return this.gestionDiscipline;
/*     */   }
/*     */   
/*     */   public void setGestionDiscipline(boolean gestionDiscipline) {
/*  88 */     this.gestionDiscipline = gestionDiscipline;
/*     */   }
/*     */   
/*     */   public boolean isGestionInscription() {
/*  92 */     return this.gestionInscription;
/*     */   }
/*     */   
/*     */   public void setGestionInscription(boolean gestionInscription) {
/*  96 */     this.gestionInscription = gestionInscription;
/*     */   }
/*     */   
/*     */   public boolean isGestionEtat() {
/* 100 */     return this.gestionEtat;
/*     */   }
/*     */   
/*     */   public void setGestionEtat(boolean gestionEtat) {
/* 104 */     this.gestionEtat = gestionEtat;
/*     */   }
/*     */   
/*     */   public boolean isParametrage() {
/* 108 */     return this.parametrage;
/*     */   }
/*     */   
/*     */   public void setParametrage(boolean parametrage) {
/* 112 */     this.parametrage = parametrage;
/*     */   }
/*     */   
/*     */   public boolean isBibliotheque() {
/* 116 */     return this.bibliotheque;
/*     */   }
/*     */   
/*     */   public void setBibliotheque(boolean bibliotheque) {
/* 120 */     this.bibliotheque = bibliotheque;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGestionPersonnelVisible() {
/* 125 */     return this.gestionPersonnelVisible;
/*     */   }
/*     */   
/*     */   public void setGestionPersonnelVisible(String gestionPersonnelVisible) {
/* 129 */     this.gestionPersonnelVisible = gestionPersonnelVisible;
/*     */   }
/*     */   
/*     */   public String getGestionNoteVisible() {
/* 133 */     return this.gestionNoteVisible;
/*     */   }
/*     */   
/*     */   public void setGestionNoteVisible(String gestionNoteVisible) {
/* 137 */     this.gestionNoteVisible = gestionNoteVisible;
/*     */   }
/*     */   
/*     */   public String getGestionPrivilegeVisible() {
/* 141 */     return this.gestionPrivilegeVisible;
/*     */   }
/*     */   
/*     */   public void setGestionPrivilegeVisible(String gestionPrivilegeVisible) {
/* 145 */     this.gestionPrivilegeVisible = gestionPrivilegeVisible;
/*     */   }
/*     */   
/*     */   public String getGestionDisciplineVisible() {
/* 149 */     return this.gestionDisciplineVisible;
/*     */   }
/*     */   
/*     */   public void setGestionDisciplineVisible(String gestionDisciplineVisible) {
/* 153 */     this.gestionDisciplineVisible = gestionDisciplineVisible;
/*     */   }
/*     */   
/*     */   public String getGestionInscriptionVisible() {
/* 157 */     return this.gestionInscriptionVisible;
/*     */   }
/*     */   
/*     */   public void setGestionInscriptionVisible(String gestionInscriptionVisible) {
/* 161 */     this.gestionInscriptionVisible = gestionInscriptionVisible;
/*     */   }
/*     */   
/*     */   public String getGestionEtatVisible() {
/* 165 */     return this.gestionEtatVisible;
/*     */   }
/*     */   
/*     */   public void setGestionEtatVisible(String gestionEtatVisible) {
/* 169 */     this.gestionEtatVisible = gestionEtatVisible;
/*     */   }
/*     */   
/*     */   public String getParametrageVisible() {
/* 173 */     return this.parametrageVisible;
/*     */   }
/*     */   
/*     */   public void setParametrageVisible(String parametrageVisible) {
/* 177 */     this.parametrageVisible = parametrageVisible;
/*     */   }
/*     */   
/*     */   public String getBibliothequeVisible() {
/* 181 */     return this.bibliothequeVisible;
/*     */   }
/*     */   
/*     */   public void setBibliothequeVisible(String bibliothequeVisible) {
/* 185 */     this.bibliothequeVisible = bibliothequeVisible;
/*     */   }
/*     */   
/*     */   public boolean isShowHibernatePanel() {
/* 189 */     return this.showHibernatePanel;
/*     */   }
/*     */   
/*     */   public void setShowHibernatePanel(boolean showHibernatePanel) {
/* 193 */     this.showHibernatePanel = showHibernatePanel;
/*     */   }
/*     */   
/*     */   public String getHibernatePassword() {
/* 197 */     return this.hibernatePassword;
/*     */   }
/*     */   
/*     */   public void setHibernatePassword(String hibernatePassword) {
/* 201 */     this.hibernatePassword = hibernatePassword;
/*     */   }
/*     */   
/*     */   public boolean isShowSessionPanel() {
/* 205 */     return this.showSessionPanel;
/*     */   }
/*     */   
/*     */   public AnneeMois getAnneeMois() {
/* 209 */     return this.anneeMois;
/*     */   }
/*     */   
/*     */   public void setAnneeMois(AnneeMois anneeMois) {
/* 213 */     this.anneeMois = anneeMois;
/*     */   }
/*     */   
/*     */   public List<AnneeMois> getAnneeMoises() {
/*     */     try {
/* 218 */       this.anneeMoises = this.anneeMoisFacadeLocal.findByEtat(Boolean.valueOf(true));
/* 219 */     } catch (Exception e) {
/* 220 */       e.printStackTrace();
/*     */     } 
/* 222 */     return this.anneeMoises;
/*     */   }
/*     */   
/*     */   public void setAnneeMoises(List<AnneeMois> anneeMoises) {
/* 226 */     this.anneeMoises = anneeMoises;
/*     */   }
/*     */   
/*     */   public Annee getAnnee() {
/* 230 */     return this.annee;
/*     */   }
/*     */   
/*     */   public void setAnnee(Annee annee) {
/* 234 */     this.annee = annee;
/*     */   }
/*     */   
/*     */   public List<Annee> getAnnees() {
/* 238 */     return this.annees;
/*     */   }
/*     */   
/*     */   public void setAnnees(List<Annee> annees) {
/* 242 */     this.annees = annees;
/*     */   }
/*     */   
/*     */   public Date getDate() {
/* 246 */     return this.date;
/*     */   }
/*     */   
/*     */   public void setDate(Date date) {
/* 250 */     this.date = date;
/*     */   }
/*     */ }


/* Location:              G:\Easy_money\Easy_money-war.war!\WEB-INF\classe\\utils\AbstractLoginBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */