/*    */ package controllers.mouchard;
/*    */ 
/*    */ import entities.Mouchard;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.ejb.EJB;
/*    */ import sessions.MouchardFacadeLocal;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AbstractMouchardController
/*    */ {
/*    */   @EJB
/*    */   protected MouchardFacadeLocal mouchardFacadeLocal;
/*    */   protected Mouchard mouchard;
/* 23 */   protected List<Mouchard> mouchards = new ArrayList<>();
/*    */   
/*    */   public List<Mouchard> getMouchards() {
/* 26 */     this.mouchards = this.mouchardFacadeLocal.findAll();
/* 27 */     return this.mouchards;
/*    */   }
/*    */   
/*    */   public void setMouchards(List<Mouchard> mouchards) {
/* 31 */     this.mouchards = mouchards;
/*    */   }
/*    */ }


/* Location:              G:\Easy_money\Easy_money-war.war!\WEB-INF\classes\controllers\mouchard\AbstractMouchardController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */