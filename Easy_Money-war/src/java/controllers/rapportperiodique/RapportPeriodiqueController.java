/*     */ package controllers.rapportperiodique;
/*     */ 
/*     */ import controllers.rapportperiodique.AbstractRapportPeriodiqueController;
/*     */ import entities.Client;
/*     */ import entities.FraisCarnet;
/*     */ import entities.Privilege;
/*     */ import entities.Retrait;
/*     */ import entities.Versement;
/*     */ import java.util.List;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.faces.bean.ManagedBean;
/*     */ import javax.faces.bean.ViewScoped;
/*     */ import utils.JsfUtil;
/*     */ import utils.PrintUtils;
/*     */ import utils.SessionMBean;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ManagedBean
/*     */ @ViewScoped
/*     */ public class RapportPeriodiqueController
/*     */   extends AbstractRapportPeriodiqueController
/*     */ {
/*     */   @PostConstruct
/*     */   private void init() {
/*  38 */     this.soldes.clear();
/*     */   }
/*     */   
/*     */   public void updateDate() {
/*     */     try {
/*  43 */       if (this.anneeMois.getIdAnneeMois() != null) {
/*  44 */         this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());
/*     */       }
/*  46 */     } catch (Exception e) {
/*  47 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void filterMois() {
/*     */     try {
/*  53 */       if (this.annee.getIdannee() != null) {
/*  54 */         this.anneeMoises = this.anneeMoisFacadeLocal.findByAnneeEtat(this.annee, true);
/*     */       }
/*  56 */     } catch (Exception e) {
/*  57 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void find() {
/*  62 */     this.soldes.clear();
/*     */     try {
/*  64 */       if (!this.date.equals(null)) {
/*  65 */         this.clients = this.clientFacadeLocal.findAllRange(true);
/*  66 */         if (!this.date1.equals(null))
/*     */         {
/*  68 */           if (this.date1.after(this.date))
/*     */           {
/*  70 */             for (Client c : this.clients) {
/*  71 */               Solde solde = new Solde();
/*  72 */               solde.setClient(c);
/*     */               
/*  74 */               List<Versement> versements = this.versementFacadeLocal.find(c, this.date, this.date1);
/*  75 */               if (versements.isEmpty()) {
/*  76 */                 solde.setMontantVerse(Integer.valueOf(0));
/*     */               } else {
/*  78 */                 int montantverse = 0;
/*  79 */                 for (Versement v : versements) {
/*  80 */                   montantverse += v.getMontant().intValue();
/*     */                 }
/*  82 */                 solde.setMontantVerse(Integer.valueOf(montantverse));
/*     */               } 
/*     */               
/*  85 */               List<Retrait> retraits = this.retraitFacadeLocal.find(c, this.date, this.date1);
/*  86 */               if (retraits.isEmpty()) {
/*  87 */                 solde.setMontantRetire(Integer.valueOf(0));
/*  88 */                 solde.setCommission(Integer.valueOf(0));
/*     */               } else {
/*  90 */                 int montantRetire = 0;
/*  91 */                 int montantCommission = 0;
/*  92 */                 for (Retrait r : retraits) {
/*  93 */                   montantRetire += r.getMontant().intValue();
/*  94 */                   montantCommission += r.getCommission().intValue();
/*     */                 } 
/*  96 */                 solde.setMontantRetire(Integer.valueOf(montantRetire));
/*  97 */                 solde.setCommission(Integer.valueOf(montantCommission));
/*     */               } 
/*     */               
/* 100 */               List<FraisCarnet> fraisCarnets = this.fraisCarnetFacadeLocal.find(c, this.date, this.date1);
/* 101 */               if (fraisCarnets.isEmpty()) {
/* 102 */                 solde.setCarnet(Integer.valueOf(0));
/*     */               } else {
/* 104 */                 int montantF = 0;
/* 105 */                 for (FraisCarnet f : fraisCarnets) {
/* 106 */                   montantF = (int)(montantF + f.getMontant().doubleValue());
/*     */                 }
/*     */                 
/* 109 */                 solde.setCarnet(Integer.valueOf(montantF));
/*     */               } 
/*     */               
/* 112 */               this.soldes.add(solde);
/*     */             } 
/*     */             
/* 115 */             if (this.soldes.isEmpty()) {
/* 116 */               this.showPrintButton = true;
/*     */             } else {
/* 118 */               this.showPrintButton = false;
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */       } 
/* 125 */     } catch (Exception e) {
/* 126 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void printReport() {
/*     */     try {
/* 132 */       Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
/* 133 */       if (p != null) {
/* 134 */         this.showReportPrintDialog = true;
/*     */       } else {
/* 136 */         p = new Privilege();
/* 137 */         p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 16);
/* 138 */         if (p != null) {
/* 139 */           this.showReportPrintDialog = true;
/*     */         } else {
/* 141 */           this.showReportPrintDialog = false;
/* 142 */           JsfUtil.addErrorMessage("Vous n'avez pas le privilège d'éditer le rapport journalier d'activités");
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 147 */       this.fileName = PrintUtils.printDailylyReport(this.date, this.date1, this.soldes);
/* 148 */     } catch (Exception e) {
/* 149 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public String calculMontantVerse() {
/* 154 */     if (this.soldes.isEmpty()) {
/* 155 */       return "";
/*     */     }
/* 157 */     int resultat = 0;
/* 158 */     for (Solde s : this.soldes) {
/* 159 */       resultat += s.getMontantVerse().intValue();
/*     */     }
/* 161 */     return JsfUtil.formaterStringMoney(Integer.valueOf(resultat));
/*     */   }
/*     */   
/*     */   public String calculMontantRetire() {
/* 165 */     if (this.soldes.isEmpty()) {
/* 166 */       return "";
/*     */     }
/* 168 */     int resultat = 0;
/* 169 */     for (Solde s : this.soldes) {
/* 170 */       resultat += s.getMontantRetire().intValue();
/*     */     }
/* 172 */     return JsfUtil.formaterStringMoney(Integer.valueOf(resultat));
/*     */   }
/*     */   
/*     */   public String calculSolde() {
/* 176 */     if (this.soldes.isEmpty()) {
/* 177 */       return "";
/*     */     }
/* 179 */     int resultat = 0;
/* 180 */     for (Solde s : this.soldes) {
/* 181 */       resultat += s.getClient().getSolde().intValue();
/*     */     }
/* 183 */     return JsfUtil.formaterStringMoney(Integer.valueOf(resultat));
/*     */   }
/*     */ }


/* Location:              G:\Easy_money\Easy_money-war.war!\WEB-INF\classes\controllers\rapportperiodique\RapportPeriodiqueController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */