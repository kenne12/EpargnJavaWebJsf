/*    */ package controllers.mouchard;
/*    */ 
/*    */ import controllers.mouchard.AbstractMouchardController;
/*    */ import entities.Mouchard;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.faces.bean.ManagedBean;
/*    */ import javax.faces.bean.ViewScoped;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ManagedBean
/*    */ @ViewScoped
/*    */ public class MouchardController
/*    */   extends AbstractMouchardController
/*    */ {
/*    */   @PostConstruct
/*    */   private void init() {
/* 39 */     this.mouchard = new Mouchard();
/*    */   }
/*    */ }


/* Location:              G:\Easy_money\Easy_money-war.war!\WEB-INF\classes\controllers\mouchard\MouchardController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */