/*     */ package controllers.retraitcn;
/*     */ 
/*     */ import entities.AnneeMois;
/*     */ import entities.Client;
/*     */ import entities.Retrait;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.ejb.EJB;
/*     */ import sessions.AnneeMoisFacadeLocal;
/*     */ import sessions.CaisseFacadeLocal;
/*     */ import sessions.ClientFacadeLocal;
/*     */ import sessions.MouchardFacadeLocal;
/*     */ import sessions.PrivilegeFacadeLocal;
/*     */ import sessions.RetraitFacadeLocal;
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
/*     */ public class AbstractRetraitCnController
/*     */ {
/*     */   @EJB
/*     */   protected RetraitFacadeLocal retraitFacadeLocal;
/*     */   protected Retrait retrait;
/*  30 */   protected List<Retrait> retraits = new ArrayList<>();
/*     */   
/*     */   @EJB
/*     */   protected ClientFacadeLocal clientFacadeLocal;
/*     */   protected Client client;
/*  35 */   protected List<Client> clients = new ArrayList<>();
/*     */   
/*     */   @EJB
/*     */   protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;
/*  39 */   protected AnneeMois anneeMois = new AnneeMois();
/*  40 */   protected List<AnneeMois> anneeMoises = new ArrayList<>();
/*     */   
/*     */   @EJB
/*     */   protected MouchardFacadeLocal mouchardFacadeLocal;
/*     */   
/*     */   @EJB
/*     */   protected CaisseFacadeLocal caisseFacadeLocal;
/*     */   
/*     */   @EJB
/*     */   protected PrivilegeFacadeLocal privilegeFacadeLocal;
/*     */   
/*  51 */   protected Integer retrait1 = Integer.valueOf(0);
/*  52 */   protected Integer commission = Integer.valueOf(0);
/*     */   
/*  54 */   protected Boolean detail = Boolean.valueOf(true);
/*  55 */   protected Boolean modifier = Boolean.valueOf(true);
/*  56 */   protected Boolean consulter = Boolean.valueOf(true);
/*  57 */   protected Boolean imprimer = Boolean.valueOf(true);
/*  58 */   protected Boolean supprimer = Boolean.valueOf(true);
/*     */   
/*  60 */   protected Boolean showRetraitCreateDialog = Boolean.valueOf(false);
/*  61 */   protected Boolean showRetraitDetailDialog = Boolean.valueOf(false);
/*  62 */   protected Boolean showRetraitDeleteDialog = Boolean.valueOf(false);
/*  63 */   protected Boolean showRetraitEditDialog = Boolean.valueOf(false);
/*  64 */   protected Boolean showRetraitPrintDialog = Boolean.valueOf(false);
/*     */   
/*  66 */   protected Boolean showClient = Boolean.valueOf(true);
/*     */   
/*  68 */   protected String mode = "";
/*     */   
/*     */   public Client getClient() {
/*  71 */     return this.client;
/*     */   }
/*     */   
/*     */   public void setClient(Client client) {
/*  75 */     this.client = client;
/*     */   }
/*     */   
/*     */   public List<Client> getClients() {
/*  79 */     this.clients = this.clientFacadeLocal.findAllRange(true);
/*  80 */     return this.clients;
/*     */   }
/*     */   
/*     */   public void setClients(List<Client> clients) {
/*  84 */     this.clients = clients;
/*     */   }
/*     */   
/*     */   public Boolean getDetail() {
/*  88 */     return this.detail;
/*     */   }
/*     */   
/*     */   public void setDetail(Boolean detail) {
/*  92 */     this.detail = detail;
/*     */   }
/*     */   
/*     */   public Boolean getModifier() {
/*  96 */     return this.modifier;
/*     */   }
/*     */   
/*     */   public void setModifier(Boolean modifier) {
/* 100 */     this.modifier = modifier;
/*     */   }
/*     */   
/*     */   public Boolean getImprimer() {
/* 104 */     this.imprimer = Boolean.valueOf(this.retraitFacadeLocal.findAll().isEmpty());
/* 105 */     return this.imprimer;
/*     */   }
/*     */   
/*     */   public void setImprimer(Boolean imprimer) {
/* 109 */     this.imprimer = imprimer;
/*     */   }
/*     */   
/*     */   public Boolean getSupprimer() {
/* 113 */     return this.supprimer;
/*     */   }
/*     */   
/*     */   public void setSupprimer(Boolean supprimer) {
/* 117 */     this.supprimer = supprimer;
/*     */   }
/*     */   
/*     */   public Integer getRetrait1() {
/* 121 */     return this.retrait1;
/*     */   }
/*     */   
/*     */   public void setRetrait1(Integer retrait1) {
/* 125 */     this.retrait1 = retrait1;
/*     */   }
/*     */   
/*     */   public Boolean getShowClient() {
/* 129 */     return this.showClient;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setShowClient(Boolean showClient) {
/* 134 */     this.showClient = showClient;
/*     */   }
/*     */   
/*     */   public Retrait getRetrait() {
/* 138 */     return this.retrait;
/*     */   }
/*     */   
/*     */   public void setRetrait(Retrait retrait) {
/* 142 */     this.modifier = this.supprimer = this.detail = Boolean.valueOf((retrait == null));
/* 143 */     this.retrait = retrait;
/*     */   }
/*     */   
/*     */   public List<Retrait> getRetraits() {
/*     */     try {
/* 148 */       this.retraits = this.retraitFacadeLocal.findAllRange(Boolean.valueOf(false));
/* 149 */     } catch (Exception e) {
/* 150 */       e.printStackTrace();
/*     */     } 
/* 152 */     return this.retraits;
/*     */   }
/*     */   
/*     */   public void setRetraits(List<Retrait> retraits) {
/* 156 */     this.retraits = retraits;
/*     */   }
/*     */   
/*     */   public Boolean getShowRetraitCreateDialog() {
/* 160 */     return this.showRetraitCreateDialog;
/*     */   }
/*     */   
/*     */   public void setShowRetraitCreateDialog(Boolean showRetraitCreateDialog) {
/* 164 */     this.showRetraitCreateDialog = showRetraitCreateDialog;
/*     */   }
/*     */   
/*     */   public Boolean getShowRetraitDetailDialog() {
/* 168 */     return this.showRetraitDetailDialog;
/*     */   }
/*     */   
/*     */   public void setShowRetraitDetailDialog(Boolean showRetraitDetailDialog) {
/* 172 */     this.showRetraitDetailDialog = showRetraitDetailDialog;
/*     */   }
/*     */   
/*     */   public Boolean getShowRetraitDeleteDialog() {
/* 176 */     return this.showRetraitDeleteDialog;
/*     */   }
/*     */   
/*     */   public void setShowRetraitDeleteDialog(Boolean showRetraitDeleteDialog) {
/* 180 */     this.showRetraitDeleteDialog = showRetraitDeleteDialog;
/*     */   }
/*     */   
/*     */   public Boolean getShowRetraitEditDialog() {
/* 184 */     return this.showRetraitEditDialog;
/*     */   }
/*     */   
/*     */   public void setShowRetraitEditDialog(Boolean showRetraitEditDialog) {
/* 188 */     this.showRetraitEditDialog = showRetraitEditDialog;
/*     */   }
/*     */   
/*     */   public Boolean getShowRetraitPrintDialog() {
/* 192 */     return this.showRetraitPrintDialog;
/*     */   }
/*     */   
/*     */   public void setShowRetraitPrintDialog(Boolean showRetraitPrintDialog) {
/* 196 */     this.showRetraitPrintDialog = showRetraitPrintDialog;
/*     */   }
/*     */   
/*     */   public Integer getCommission() {
/* 200 */     return this.commission;
/*     */   }
/*     */   
/*     */   public void setCommission(Integer commission) {
/* 204 */     this.commission = commission;
/*     */   }
/*     */   
/*     */   public List<AnneeMois> getAnneeMoises() {
/*     */     try {
/* 209 */       this.anneeMoises = this.anneeMoisFacadeLocal.findByEtat(Boolean.valueOf(true));
/* 210 */     } catch (Exception e) {
/* 211 */       e.printStackTrace();
/*     */     } 
/* 213 */     return this.anneeMoises;
/*     */   }
/*     */   
/*     */   public void setAnneeMoises(List<AnneeMois> anneeMoises) {
/* 217 */     this.anneeMoises = anneeMoises;
/*     */   }
/*     */   
/*     */   public AnneeMois getAnneeMois() {
/* 221 */     return this.anneeMois;
/*     */   }
/*     */   
/*     */   public void setAnneeMois(AnneeMois anneeMois) {
/* 225 */     this.anneeMois = anneeMois;
/*     */   }
/*     */ }


/* Location:              G:\Easy_money\Easy_money-war.war!\WEB-INF\classes\controllers\retraitcn\AbstractRetraitCnController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */