/*     */ package controllers.rapporthebdomadaire;
/*     */ 
/*     */ import entities.Annee;
/*     */ import entities.AnneeMois;
/*     */ import entities.Client;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.ejb.EJB;
/*     */ import sessions.AnneeFacadeLocal;
/*     */ import sessions.AnneeMoisFacadeLocal;
/*     */ import sessions.ClientFacadeLocal;
/*     */ import sessions.FraisCarnetFacadeLocal;
/*     */ import sessions.PrivilegeFacadeLocal;
/*     */ import sessions.RetraitFacadeLocal;
/*     */ import sessions.VersementFacadeLocal;
/*     */ import utils.Solde;
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
/*     */ public class AbstractRapportHebdomaireController
/*     */ {
/*     */   @EJB
/*     */   protected AnneeFacadeLocal anneeFacadeLocal;
/*  32 */   protected Annee annee = new Annee();
/*  33 */   protected List<Annee> annees = new ArrayList<>();
/*     */   
/*     */   @EJB
/*     */   protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;
/*  37 */   protected AnneeMois anneeMois = new AnneeMois();
/*  38 */   protected List<AnneeMois> anneeMoises = new ArrayList<>();
/*     */   
/*     */   @EJB
/*     */   protected VersementFacadeLocal versementFacadeLocal;
/*     */   
/*     */   @EJB
/*     */   protected RetraitFacadeLocal retraitFacadeLocal;
/*     */   
/*     */   @EJB
/*     */   protected FraisCarnetFacadeLocal fraisCarnetFacadeLocal;
/*     */   
/*     */   @EJB
/*     */   protected PrivilegeFacadeLocal privilegeFacadeLocal;
/*     */   
/*     */   @EJB
/*     */   protected ClientFacadeLocal clientFacadeLocal;
/*  54 */   List<Client> clients = new ArrayList<>();
/*     */   
/*  56 */   protected List<Solde> soldes = new ArrayList<>();
/*     */   
/*  58 */   Date dateDebut = new Date();
/*  59 */   Date dateFin = new Date();
/*     */   
/*  61 */   protected String fileName = "";
/*     */   
/*     */   protected boolean showPrintButton = true;
/*     */   protected boolean showReportPrintDialog = false;
/*     */   
/*     */   public List<Solde> getSoldes() {
/*  67 */     return this.soldes;
/*     */   }
/*     */   
/*     */   public void setSoldes(List<Solde> soldes) {
/*  71 */     this.soldes = soldes;
/*     */   }
/*     */   
/*     */   public Date getDateDebut() {
/*  75 */     return this.dateDebut;
/*     */   }
/*     */   
/*     */   public void setDateDebut(Date dateDebut) {
/*  79 */     this.dateDebut = dateDebut;
/*     */   }
/*     */   
/*     */   public Date getDateFin() {
/*  83 */     return this.dateFin;
/*     */   }
/*     */   
/*     */   public void setDateFin(Date dateFin) {
/*  87 */     this.dateFin = dateFin;
/*     */   }
/*     */   
/*     */   public boolean isShowPrintButton() {
/*  91 */     return this.showPrintButton;
/*     */   }
/*     */   
/*     */   public void setShowPrintButton(boolean showPrintButton) {
/*  95 */     this.showPrintButton = showPrintButton;
/*     */   }
/*     */   
/*     */   public String getFileName() {
/*  99 */     return this.fileName;
/*     */   }
/*     */   
/*     */   public void setFileName(String fileName) {
/* 103 */     this.fileName = fileName;
/*     */   }
/*     */   
/*     */   public boolean isShowReportPrintDialog() {
/* 107 */     return this.showReportPrintDialog;
/*     */   }
/*     */   
/*     */   public void setShowReportPrintDialog(boolean showReportPrintDialog) {
/* 111 */     this.showReportPrintDialog = showReportPrintDialog;
/*     */   }
/*     */   
/*     */   public Annee getAnnee() {
/* 115 */     return this.annee;
/*     */   }
/*     */   
/*     */   public void setAnnee(Annee annee) {
/* 119 */     this.annee = annee;
/*     */   }
/*     */   
/*     */   public List<Annee> getAnnees() {
/*     */     try {
/* 124 */       this.annees = this.anneeFacadeLocal.findByEtat(true);
/* 125 */     } catch (Exception e) {
/* 126 */       e.printStackTrace();
/*     */     } 
/* 128 */     return this.annees;
/*     */   }
/*     */   
/*     */   public void setAnnees(List<Annee> annees) {
/* 132 */     this.annees = annees;
/*     */   }
/*     */   
/*     */   public AnneeMois getAnneeMois() {
/* 136 */     return this.anneeMois;
/*     */   }
/*     */   
/*     */   public void setAnneeMois(AnneeMois anneeMois) {
/* 140 */     this.anneeMois = anneeMois;
/*     */   }
/*     */   
/*     */   public List<AnneeMois> getAnneeMoises() {
/* 144 */     return this.anneeMoises;
/*     */   }
/*     */   
/*     */   public void setAnneeMoises(List<AnneeMois> anneeMoises) {
/* 148 */     this.anneeMoises = anneeMoises;
/*     */   }
/*     */ }


/* Location:              G:\Easy_money\Easy_money-war.war!\WEB-INF\classes\controllers\rapporthebdomadaire\AbstractRapportHebdomaireController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */