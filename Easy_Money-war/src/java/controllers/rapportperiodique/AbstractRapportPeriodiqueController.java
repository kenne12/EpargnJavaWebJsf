/*     */ package controllers.rapportperiodique;
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
/*     */ public class AbstractRapportPeriodiqueController
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
/*  58 */   protected Date date = new Date();
/*  59 */   protected Date date1 = new Date();
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
/*     */   public Date getDate() {
/*  75 */     return this.date;
/*     */   }
/*     */   
/*     */   public void setDate(Date date) {
/*  79 */     this.date = date;
/*     */   }
/*     */   
/*     */   public boolean isShowPrintButton() {
/*  83 */     return this.showPrintButton;
/*     */   }
/*     */   
/*     */   public void setShowPrintButton(boolean showPrintButton) {
/*  87 */     this.showPrintButton = showPrintButton;
/*     */   }
/*     */   
/*     */   public String getFileName() {
/*  91 */     return this.fileName;
/*     */   }
/*     */   
/*     */   public void setFileName(String fileName) {
/*  95 */     this.fileName = fileName;
/*     */   }
/*     */   
/*     */   public boolean isShowReportPrintDialog() {
/*  99 */     return this.showReportPrintDialog;
/*     */   }
/*     */   
/*     */   public void setShowReportPrintDialog(boolean showReportPrintDialog) {
/* 103 */     this.showReportPrintDialog = showReportPrintDialog;
/*     */   }
/*     */   
/*     */   public List<Annee> getAnnees() {
/*     */     try {
/* 108 */       this.annees = this.anneeFacadeLocal.findByEtat(true);
/* 109 */     } catch (Exception e) {
/* 110 */       e.printStackTrace();
/*     */     } 
/* 112 */     return this.annees;
/*     */   }
/*     */   
/*     */   public void setAnnees(List<Annee> annees) {
/* 116 */     this.annees = annees;
/*     */   }
/*     */   
/*     */   public AnneeMois getAnneeMois() {
/* 120 */     return this.anneeMois;
/*     */   }
/*     */   
/*     */   public void setAnneeMois(AnneeMois anneeMois) {
/* 124 */     this.anneeMois = anneeMois;
/*     */   }
/*     */   
/*     */   public List<AnneeMois> getAnneeMoises() {
/* 128 */     return this.anneeMoises;
/*     */   }
/*     */   
/*     */   public void setAnneeMoises(List<AnneeMois> anneeMoises) {
/* 132 */     this.anneeMoises = anneeMoises;
/*     */   }
/*     */   
/*     */   public Annee getAnnee() {
/* 136 */     return this.annee;
/*     */   }
/*     */   
/*     */   public void setAnnee(Annee annee) {
/* 140 */     this.annee = annee;
/*     */   }
/*     */   
/*     */   public Date getDate1() {
/* 144 */     return this.date1;
/*     */   }
/*     */   
/*     */   public void setDate1(Date date1) {
/* 148 */     this.date1 = date1;
/*     */   }
/*     */ }


/* Location:              G:\Easy_money\Easy_money-war.war!\WEB-INF\classes\controllers\rapportperiodique\AbstractRapportPeriodiqueController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */