/*     */ package controllers.fraiscarnet;
/*     */ 
/*     */ import entities.AnneeMois;
/*     */ import entities.Client;
/*     */ import entities.FraisCarnet;
/*     */ import entities.Profession;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.ejb.EJB;
/*     */ import sessions.AnneeMoisFacadeLocal;
/*     */ import sessions.CaisseFacadeLocal;
/*     */ import sessions.ClientFacadeLocal;
/*     */ import sessions.FraisCarnetFacadeLocal;
/*     */ import sessions.MouchardFacadeLocal;
/*     */ import sessions.PrivilegeFacadeLocal;
/*     */ import sessions.ProfessionFacadeLocal;
/*     */ import sessions.RetraitFacadeLocal;
/*     */ import sessions.VersementFacadeLocal;
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
/*     */ public class AbstractCarnetClientController
/*     */ {
/*     */   @EJB
/*     */   protected FraisCarnetFacadeLocal fraisCarnetFacadeLocal;
/*  33 */   protected FraisCarnet fraisCarnet = new FraisCarnet();
/*  34 */   protected List<FraisCarnet> fraisCarnets = new ArrayList<>();
/*     */   
/*     */   @EJB
/*     */   protected ClientFacadeLocal clientFacadeLocal;
/*  38 */   protected Client client = new Client();
/*  39 */   protected List<Client> clients = new ArrayList<>();
/*  40 */   protected List<Client> clients1 = new ArrayList<>();
/*     */   
/*     */   @EJB
/*     */   protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;
/*  44 */   protected AnneeMois anneeMois = new AnneeMois();
/*  45 */   protected List<AnneeMois> anneeMoises = new ArrayList<>();
/*     */   
/*     */   @EJB
/*     */   protected MouchardFacadeLocal mouchardFacadeLocal;
/*     */   
/*     */   @EJB
/*     */   protected ProfessionFacadeLocal professionFacadeLocal;
/*  52 */   protected List<Profession> professions = new ArrayList<>();
/*     */   
/*     */   @EJB
/*     */   protected VersementFacadeLocal versementFacadeLocal;
/*     */   
/*     */   @EJB
/*     */   protected CaisseFacadeLocal caisseFacadeLocal;
/*     */   
/*     */   @EJB
/*     */   protected RetraitFacadeLocal retraitFacadeLocal;
/*     */   
/*     */   @EJB
/*     */   protected PrivilegeFacadeLocal privilegeFacadeLocal;
/*     */   
/*  66 */   protected String fileName = "";
/*     */   
/*  68 */   protected Boolean detail = Boolean.valueOf(true);
/*  69 */   protected Boolean modifier = Boolean.valueOf(true);
/*  70 */   protected Boolean consulter = Boolean.valueOf(true);
/*  71 */   protected Boolean imprimer = Boolean.valueOf(true);
/*  72 */   protected Boolean supprimer = Boolean.valueOf(true);
/*     */   
/*  74 */   protected Boolean showEditSolde = Boolean.valueOf(true);
/*     */   
/*     */   protected boolean showClientCreateDialog = false;
/*     */   protected boolean showClientDeleteDialog = false;
/*  78 */   protected Boolean showClientPrintDialog = Boolean.valueOf(false);
/*     */   
/*     */   protected boolean showMontantCarnet = true;
/*     */   
/*     */   protected boolean showMontantCarnetCompnent = true;
/*  83 */   protected Integer carnet = Integer.valueOf(500);
/*     */   
/*  85 */   protected String mode = "";
/*     */   
/*     */   public Client getClient() {
/*  88 */     return this.client;
/*     */   }
/*     */   
/*     */   public void setClient(Client client) {
/*  92 */     this.modifier = this.supprimer = this.detail = Boolean.valueOf((client == null));
/*  93 */     this.client = client;
/*     */   }
/*     */   
/*     */   public List<Client> getClients() {
/*  97 */     return this.clients;
/*     */   }
/*     */   
/*     */   public void setClients(List<Client> clients) {
/* 101 */     this.clients = clients;
/*     */   }
/*     */   
/*     */   public Boolean getDetail() {
/* 105 */     return this.detail;
/*     */   }
/*     */   
/*     */   public void setDetail(Boolean detail) {
/* 109 */     this.detail = detail;
/*     */   }
/*     */   
/*     */   public Boolean getModifier() {
/* 113 */     return this.modifier;
/*     */   }
/*     */   
/*     */   public void setModifier(Boolean modifier) {
/* 117 */     this.modifier = modifier;
/*     */   }
/*     */   
/*     */   public Boolean getConsulter() {
/* 121 */     return this.consulter;
/*     */   }
/*     */   
/*     */   public void setConsulter(Boolean consulter) {
/* 125 */     this.consulter = consulter;
/*     */   }
/*     */   
/*     */   public Boolean getImprimer() {
/* 129 */     this.imprimer = Boolean.valueOf(this.clientFacadeLocal.findAllRange().isEmpty());
/* 130 */     return this.imprimer;
/*     */   }
/*     */   
/*     */   public void setImprimer(Boolean imprimer) {
/* 134 */     this.imprimer = imprimer;
/*     */   }
/*     */   
/*     */   public Boolean getSupprimer() {
/* 138 */     return this.supprimer;
/*     */   }
/*     */   
/*     */   public void setSupprimer(Boolean supprimer) {
/* 142 */     this.supprimer = supprimer;
/*     */   }
/*     */   
/*     */   public Boolean getShowEditSolde() {
/* 146 */     return this.showEditSolde;
/*     */   }
/*     */   
/*     */   public void setShowEditSolde(Boolean showEditSolde) {
/* 150 */     this.showEditSolde = showEditSolde;
/*     */   }
/*     */   
/*     */   public boolean isShowClientCreateDialog() {
/* 154 */     return this.showClientCreateDialog;
/*     */   }
/*     */   
/*     */   public void setShowClientCreateDialog(boolean showClientCreateDialog) {
/* 158 */     this.showClientCreateDialog = showClientCreateDialog;
/*     */   }
/*     */   
/*     */   public boolean isShowClientDeleteDialog() {
/* 162 */     return this.showClientDeleteDialog;
/*     */   }
/*     */   
/*     */   public void setShowClientDeleteDialog(boolean showClientDeleteDialog) {
/* 166 */     this.showClientDeleteDialog = showClientDeleteDialog;
/*     */   }
/*     */   
/*     */   public Boolean getShowClientPrintDialog() {
/* 170 */     return this.showClientPrintDialog;
/*     */   }
/*     */   
/*     */   public void setShowClientPrintDialog(Boolean showClientPrintDialog) {
/* 174 */     this.showClientPrintDialog = showClientPrintDialog;
/*     */   }
/*     */   
/*     */   public String getFileName() {
/* 178 */     return this.fileName;
/*     */   }
/*     */   
/*     */   public void setFileName(String fileName) {
/* 182 */     this.fileName = fileName;
/*     */   }
/*     */   
/*     */   public int getCarnet() {
/* 186 */     return this.carnet.intValue();
/*     */   }
/*     */   
/*     */   public void setCarnet(int carnet) {
/* 190 */     this.carnet = Integer.valueOf(carnet);
/*     */   }
/*     */   
/*     */   public boolean isShowMontantCarnet() {
/* 194 */     return this.showMontantCarnet;
/*     */   }
/*     */   
/*     */   public void setShowMontantCarnet(boolean showMontantCarnet) {
/* 198 */     this.showMontantCarnet = showMontantCarnet;
/*     */   }
/*     */   
/*     */   public boolean isShowMontantCarnetCompnent() {
/* 202 */     return this.showMontantCarnetCompnent;
/*     */   }
/*     */   
/*     */   public void setShowMontantCarnetCompnent(boolean showMontantCarnetCompnent) {
/* 206 */     this.showMontantCarnetCompnent = showMontantCarnetCompnent;
/*     */   }
/*     */   
/*     */   public List<Client> getClients1() {
/* 210 */     this.clients1 = this.clientFacadeLocal.findAllRange(true);
/* 211 */     return this.clients1;
/*     */   }
/*     */   
/*     */   public void setClients1(List<Client> clients1) {
/* 215 */     this.clients1 = clients1;
/*     */   }
/*     */   
/*     */   public AnneeMois getAnneeMois() {
/* 219 */     return this.anneeMois;
/*     */   }
/*     */   
/*     */   public void setAnneeMois(AnneeMois anneeMois) {
/* 223 */     this.anneeMois = anneeMois;
/*     */   }
/*     */   
/*     */   public List<AnneeMois> getAnneeMoises() {
/*     */     try {
/* 228 */       this.anneeMoises = this.anneeMoisFacadeLocal.findByEtat(Boolean.valueOf(true));
/* 229 */     } catch (Exception e) {
/* 230 */       e.printStackTrace();
/*     */     } 
/* 232 */     return this.anneeMoises;
/*     */   }
/*     */   
/*     */   public void setAnneeMoises(List<AnneeMois> anneeMoises) {
/* 236 */     this.anneeMoises = anneeMoises;
/*     */   }
/*     */   
/*     */   public FraisCarnet getFraisCarnet() {
/* 240 */     return this.fraisCarnet;
/*     */   }
/*     */   
/*     */   public void setFraisCarnet(FraisCarnet fraisCarnet) {
/* 244 */     this.modifier = this.supprimer = this.detail = Boolean.valueOf((this.client == null));
/* 245 */     this.fraisCarnet = fraisCarnet;
/*     */   }
/*     */   
/*     */   public List<FraisCarnet> getFraisCarnets() {
/* 249 */     this.fraisCarnets = this.fraisCarnetFacadeLocal.findAllRange();
/* 250 */     return this.fraisCarnets;
/*     */   }
/*     */   
/*     */   public void setFraisCarnets(List<FraisCarnet> fraisCarnets) {
/* 254 */     this.fraisCarnets = fraisCarnets;
/*     */   }
/*     */ }


/* Location:              G:\Easy_money\Easy_money-war.war!\WEB-INF\classes\controllers\fraiscarnet\AbstractCarnetClientController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */