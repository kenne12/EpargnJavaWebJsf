/*     */ package controllers.fraiscarnet;
/*     */ 
/*     */ import controllers.fraiscarnet.AbstractCarnetClientController;
/*     */ import entities.AnneeMois;
/*     */ import entities.Caisse;
/*     */ import entities.Client;
/*     */ import entities.Privilege;
/*     */ import entities.Retrait;
/*     */ import entities.Versement;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.faces.bean.ManagedBean;
/*     */ import javax.faces.bean.ViewScoped;
/*     */ import utils.JsfUtil;
/*     */ import utils.PrintUtils;
/*     */ import utils.SessionMBean;
/*     */ import utils.Utilitaires;
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
/*     */ public class CarnetClientController
/*     */   extends AbstractCarnetClientController
/*     */ {
/*     */   @PostConstruct
/*     */   private void init() {
/*  40 */     this.client = new Client();
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareCreate() {
/*  45 */     this.client = new Client();
/*  46 */     this.mode = "Create";
/*  47 */     this.showMontantCarnetCompnent = true;
/*  48 */     this.carnet = Integer.valueOf(500);
/*     */     try {
/*  50 */       filterDate(new Date());
/*  51 */       Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
/*  52 */       if (p != null) {
/*  53 */         this.showClientCreateDialog = true;
/*     */       } else {
/*  55 */         p = new Privilege();
/*  56 */         p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 21);
/*  57 */         if (p != null) {
/*  58 */           this.showClientCreateDialog = true;
/*     */         } else {
/*  60 */           JsfUtil.addErrorMessage("Vous n'avez pas le privilege d'enregistrer un client");
/*     */         } 
/*     */       } 
/*  63 */     } catch (Exception e) {
/*  64 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void prepareEdit() {
/*  69 */     this.mode = "Edit";
/*  70 */     this.showMontantCarnetCompnent = false;
/*  71 */     if (this.client != null) {
/*  72 */       List<Versement> versementsTemp = this.versementFacadeLocal.find(this.client);
/*  73 */       if (versementsTemp.isEmpty()) {
/*  74 */         List<Retrait> retraitsTemp = this.retraitFacadeLocal.find(this.client);
/*  75 */         if (retraitsTemp.isEmpty()) {
/*  76 */           this.showEditSolde = Boolean.valueOf(true);
/*     */         } else {
/*  78 */           this.showEditSolde = Boolean.valueOf(false);
/*     */         } 
/*     */       } else {
/*  81 */         this.showEditSolde = Boolean.valueOf(false);
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/*  86 */       Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
/*  87 */       if (p != null) {
/*  88 */         this.showClientCreateDialog = true;
/*     */         return;
/*     */       } 
/*  91 */       p = new Privilege();
/*  92 */       p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 8);
/*  93 */       if (p != null) {
/*  94 */         this.showClientCreateDialog = true;
/*     */         return;
/*     */       } 
/*  97 */       JsfUtil.addErrorMessage("Vous n'avez pas le privilege de modifier ce client");
/*     */     
/*     */     }
/* 100 */     catch (Exception e) {
/* 101 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void create() {
/*     */     try {
/* 108 */       if (this.mode.equals("Create"))
/*     */       {
/* 110 */         if (this.client.getIdclient() != null) {
/*     */ 
/*     */           
/* 113 */           Caisse c = this.caisseFacadeLocal.findAll().get(0);
/* 114 */           c.setMontant(Integer.valueOf(c.getMontant().intValue() + this.carnet.intValue()));
/* 115 */           this.caisseFacadeLocal.edit(c);
/*     */           
/* 117 */           this.fraisCarnet.setId(this.fraisCarnetFacadeLocal.nextVal());
/* 118 */           this.fraisCarnet.setIdmois(this.anneeMois);
/* 119 */           this.fraisCarnet.setMontant(Double.valueOf(this.carnet.doubleValue()));
/* 120 */           this.fraisCarnet.setIdclient(this.client);
/* 121 */           this.fraisCarnetFacadeLocal.create(this.fraisCarnet);
/*     */           
/* 123 */           Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement des frais de carnet du client : " + this.client.getNom() + " " + this.client.getPrenom(), SessionMBean.getUserAccount());
/* 124 */           this.client = new Client();
/* 125 */           JsfUtil.addSuccessMessage("Opération réussie");
/*     */         } else {
/* 127 */           JsfUtil.addErrorMessage("Aucun client sélectionné");
/*     */         } 
/*     */       }
/* 130 */     } catch (Exception e) {
/* 131 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void edit() {}
/*     */ 
/*     */   
/*     */   public void delete() {
/*     */     try {
/* 141 */       if (this.fraisCarnet != null) {
/*     */         
/* 143 */         Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
/* 144 */         if (p != null) {
/* 145 */           this.showClientDeleteDialog = true;
/*     */         } else {
/* 147 */           p = new Privilege();
/* 148 */           p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 22);
/* 149 */           if (p != null) {
/* 150 */             this.showClientDeleteDialog = true;
/*     */           } else {
/* 152 */             this.showClientDeleteDialog = false;
/* 153 */             JsfUtil.addErrorMessage("Vous n'avez pas le privilège de supprimer les frais de carnet");
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/* 158 */         this.client = this.fraisCarnet.getIdclient();
/*     */         
/* 160 */         this.fraisCarnetFacadeLocal.remove(this.fraisCarnet);
/*     */         
/* 162 */         if (this.client.getFraiscarnet().intValue() != 0) {
/* 163 */           Caisse caisse = this.caisseFacadeLocal.findAll().get(0);
/* 164 */           caisse.setMontant(Integer.valueOf(caisse.getMontant().intValue() - this.client.getFraiscarnet().intValue()));
/* 165 */           this.caisseFacadeLocal.edit(caisse);
/*     */         } 
/*     */         
/* 168 */         Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression des frais de carnet du client : " + this.client.getNom() + " " + this.client.getPrenom(), SessionMBean.getUserAccount());
/* 169 */         JsfUtil.addSuccessMessage("Operation réussie");
/*     */       } else {
/* 171 */         JsfUtil.addErrorMessage("Aucune ligne selectionnée");
/*     */       } 
/* 173 */     } catch (Exception e) {
/* 174 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void print() {
/*     */     try {
/* 180 */       Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
/* 181 */       if (p != null) {
/* 182 */         this.showClientPrintDialog = Boolean.valueOf(true);
/*     */       } else {
/* 184 */         p = new Privilege();
/* 185 */         p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 20);
/* 186 */         if (p != null) {
/* 187 */           this.showClientPrintDialog = Boolean.valueOf(true);
/*     */         } else {
/* 189 */           this.showClientPrintDialog = Boolean.valueOf(false);
/* 190 */           JsfUtil.addErrorMessage("Vous n 'avez pas le privilège d'éditer la liste des clients");
/*     */           return;
/*     */         } 
/*     */       } 
/* 194 */       this.fileName = PrintUtils.printCustomerList(this.clients);
/* 195 */       System.err.println("" + this.fileName);
/* 196 */     } catch (Exception e) {
/* 197 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateMois() {
/*     */     try {
/* 203 */       this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());
/* 204 */     } catch (Exception e) {
/* 205 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void filterDate(Date date) {
/* 210 */     for (AnneeMois a : this.anneeMoises) {
/*     */       try {
/* 212 */         if ((a.getDateDebut().equals(date) || a.getDateDebut().before(date)) && (a.getDateFin().equals(date) || a.getDateFin().after(date))) {
/* 213 */           this.anneeMois = a;
/* 214 */           System.err.println("retouvé");
/*     */           break;
/*     */         } 
/* 217 */       } catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              G:\Easy_money\Easy_money-war.war!\WEB-INF\classes\controllers\fraiscarnet\CarnetClientController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */