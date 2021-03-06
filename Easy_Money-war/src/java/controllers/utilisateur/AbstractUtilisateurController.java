/*     */ package controllers.utilisateur;
/*     */ 
/*     */ import entities.Utilisateur;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.ejb.EJB;
/*     */ import sessions.MouchardFacadeLocal;
/*     */ import sessions.PrivilegeFacadeLocal;
/*     */ import sessions.UtilisateurFacadeLocal;
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
/*     */ public class AbstractUtilisateurController
/*     */ {
/*     */   @EJB
/*     */   protected UtilisateurFacadeLocal utilisateurFacadeLocal;
/*     */   protected Utilisateur utilisateur;
/*  25 */   protected List<Utilisateur> utilisateurs = new ArrayList<>();
/*     */   
/*  27 */   protected List<Utilisateur> utilisateurActifs = new ArrayList<>();
/*  28 */   protected List<Utilisateur> utilisateurInactifs = new ArrayList<>();
/*     */   
/*     */   @EJB
/*     */   protected MouchardFacadeLocal mouchardFacadeLocal;
/*     */   
/*     */   @EJB
/*     */   protected PrivilegeFacadeLocal privilegeFacadeLocal;
/*     */   
/*  36 */   protected Boolean detail = Boolean.valueOf(true);
/*  37 */   protected Boolean modifier = Boolean.valueOf(true);
/*  38 */   protected Boolean consulter = Boolean.valueOf(true);
/*  39 */   protected Boolean imprimer = Boolean.valueOf(true);
/*  40 */   protected Boolean supprimer = Boolean.valueOf(true);
/*     */   
/*  42 */   protected Boolean showEditSolde = Boolean.valueOf(true);
/*     */   
/*  44 */   protected Boolean showUserCreateDialog = Boolean.valueOf(false);
/*  45 */   protected Boolean showUserDetailDialog = Boolean.valueOf(false);
/*  46 */   protected Boolean showUserDeleteDialog = Boolean.valueOf(false);
/*  47 */   protected Boolean showUserEditDialog = Boolean.valueOf(false);
/*  48 */   protected Boolean showUserPrintDialog = Boolean.valueOf(false);
/*     */   
/*     */   protected boolean buttonActif = false;
/*     */   
/*     */   protected boolean buttonInactif = true;
/*  53 */   protected String mode = "";
/*     */   
/*     */   public Boolean getDetail() {
/*  56 */     return this.detail;
/*     */   }
/*     */   
/*     */   public void setDetail(Boolean detail) {
/*  60 */     this.detail = detail;
/*     */   }
/*     */   
/*     */   public Boolean getModifier() {
/*  64 */     return this.modifier;
/*     */   }
/*     */   
/*     */   public void setModifier(Boolean modifier) {
/*  68 */     this.modifier = modifier;
/*     */   }
/*     */   
/*     */   public Boolean getConsulter() {
/*  72 */     return this.consulter;
/*     */   }
/*     */   
/*     */   public void setConsulter(Boolean consulter) {
/*  76 */     this.consulter = consulter;
/*     */   }
/*     */   
/*     */   public Boolean getImprimer() {
/*  80 */     this.imprimer = Boolean.valueOf(this.utilisateurFacadeLocal.findAll().isEmpty());
/*  81 */     return this.imprimer;
/*     */   }
/*     */   
/*     */   public void setImprimer(Boolean imprimer) {
/*  85 */     this.imprimer = imprimer;
/*     */   }
/*     */   
/*     */   public Boolean getSupprimer() {
/*  89 */     return this.supprimer;
/*     */   }
/*     */   
/*     */   public void setSupprimer(Boolean supprimer) {
/*  93 */     this.supprimer = supprimer;
/*     */   }
/*     */   
/*     */   public Boolean getShowEditSolde() {
/*  97 */     return this.showEditSolde;
/*     */   }
/*     */   
/*     */   public void setShowEditSolde(Boolean showEditSolde) {
/* 101 */     this.showEditSolde = showEditSolde;
/*     */   }
/*     */   
/*     */   public Utilisateur getUtilisateur() {
/* 105 */     return this.utilisateur;
/*     */   }
/*     */   
/*     */   public void setUtilisateur(Utilisateur utilisateur) {
/* 109 */     this.modifier = this.supprimer = this.detail = Boolean.valueOf((utilisateur == null));
/* 110 */     this.utilisateur = utilisateur;
/*     */   }
/*     */   
/*     */   public List<Utilisateur> getUtilisateurs() {
/* 114 */     this.utilisateurs = this.utilisateurFacadeLocal.findAll();
/* 115 */     return this.utilisateurs;
/*     */   }
/*     */   
/*     */   public void setUtilisateurs(List<Utilisateur> utilisateurs) {
/* 119 */     this.utilisateurs = utilisateurs;
/*     */   }
/*     */   
/*     */   public Boolean getShowUserCreateDialog() {
/* 123 */     return this.showUserCreateDialog;
/*     */   }
/*     */   
/*     */   public void setShowUserCreateDialog(Boolean showUserCreateDialog) {
/* 127 */     this.showUserCreateDialog = showUserCreateDialog;
/*     */   }
/*     */   
/*     */   public Boolean getShowUserDetailDialog() {
/* 131 */     return this.showUserDetailDialog;
/*     */   }
/*     */   
/*     */   public void setShowUserDetailDialog(Boolean showUserDetailDialog) {
/* 135 */     this.showUserDetailDialog = showUserDetailDialog;
/*     */   }
/*     */   
/*     */   public Boolean getShowUserDeleteDialog() {
/* 139 */     return this.showUserDeleteDialog;
/*     */   }
/*     */   
/*     */   public void setShowUserDeleteDialog(Boolean showUserDeleteDialog) {
/* 143 */     this.showUserDeleteDialog = showUserDeleteDialog;
/*     */   }
/*     */   
/*     */   public Boolean getShowUserEditDialog() {
/* 147 */     return this.showUserEditDialog;
/*     */   }
/*     */   
/*     */   public void setShowUserEditDialog(Boolean showUserEditDialog) {
/* 151 */     this.showUserEditDialog = showUserEditDialog;
/*     */   }
/*     */   
/*     */   public Boolean getShowUserPrintDialog() {
/* 155 */     return this.showUserPrintDialog;
/*     */   }
/*     */   
/*     */   public void setShowUserPrintDialog(Boolean showUserPrintDialog) {
/* 159 */     this.showUserPrintDialog = showUserPrintDialog;
/*     */   }
/*     */   
/*     */   public List<Utilisateur> getUtilisateurActifs() {
/* 163 */     this.utilisateurActifs = this.utilisateurFacadeLocal.findByActif(Boolean.valueOf(true));
/* 164 */     return this.utilisateurActifs;
/*     */   }
/*     */   
/*     */   public void setUtilisateurActifs(List<Utilisateur> utilisateurActifs) {
/* 168 */     this.utilisateurActifs = utilisateurActifs;
/*     */   }
/*     */   
/*     */   public List<Utilisateur> getUtilisateurInactifs() {
/* 172 */     this.utilisateurInactifs = this.utilisateurFacadeLocal.findByActif(Boolean.valueOf(false));
/* 173 */     return this.utilisateurInactifs;
/*     */   }
/*     */   
/*     */   public void setUtilisateurInactifs(List<Utilisateur> utilisateurInactifs) {
/* 177 */     this.utilisateurInactifs = utilisateurInactifs;
/*     */   }
/*     */   
/*     */   public boolean isButtonActif() {
/* 181 */     return this.buttonActif;
/*     */   }
/*     */   
/*     */   public void setButtonActif(boolean buttonActif) {
/* 185 */     this.buttonActif = buttonActif;
/*     */   }
/*     */   
/*     */   public boolean isButtonInactif() {
/* 189 */     return this.buttonInactif;
/*     */   }
/*     */   
/*     */   public void setButtonInactif(boolean buttonInactif) {
/* 193 */     this.buttonInactif = buttonInactif;
/*     */   }
/*     */ }


/* Location:              G:\Easy_money\Easy_money-war.war!\WEB-INF\classes\controller\\utilisateur\AbstractUtilisateurController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */