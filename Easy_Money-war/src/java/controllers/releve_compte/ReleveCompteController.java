package controllers.releve_compte;

import entities.Privilege;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import utils.JsfUtil;
import utils.PrintUtils;
import utils.SessionMBean;

@ManagedBean
@SessionScoped
public class ReleveCompteController extends AbstractReleveCompteController implements Serializable {
    
    @PostConstruct
    private void init() {
        
    }
    
    public void find() {
        versements.clear();
        retraits.clear();
        showPrintButton = true;
        sommeRetrait = 0;
        sommeCommission = 0;
        sommeVersement = 0;
        try {
            if (client.getIdclient().equals(0)) {
                JsfUtil.addWarningMessage("Veuillez sélectionner un client");
                return;
            }
            
            if (dateDebut != null && dateFin != null && (dateFin.equals(dateFin) || dateFin.after(dateDebut))) {
                
                client = clientFacadeLocal.find(client.getIdclient());
                
                versements = versementFacadeLocal.find(client, dateDebut, dateFin);
                retraits = retraitFacadeLocal.find(client, dateDebut, dateFin);
                
                sommeVersement = versements.stream().mapToInt(v -> v.getMontant()).sum();
                sommeRetrait = 0;
                sommeCommission = 0;
                
                retraits.forEach(r -> {
                    sommeRetrait += r.getMontant();
                    sommeCommission += r.getCommission();
                });
                
                if (!versements.isEmpty() || !retraits.isEmpty()) {
                    this.showPrintButton = false;
                } else {
                    JsfUtil.addWarningMessage("Aucune opération trouvée sur la période");
                }
                return;
            }
            JsfUtil.addWarningMessage("Veuillez controller les dates");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void printReport() {
        try {
            Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            if (p != null) {
                this.showReportPrintDialog = true;
            } else {
                p = new Privilege();
                p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 16);
                if (p != null) {
                    this.showReportPrintDialog = true;
                } else {
                    this.showReportPrintDialog = false;
                    JsfUtil.addErrorMessage("Vous n 'avez pas le privilege d'éditer le rapport journalier d'activités");
                    return;
                }
            }
            this.fileName = PrintUtils.printAccountMvt(client, dateDebut, dateFin, versements, retraits);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String calculMontantVerse() {
        return JsfUtil.formaterStringMoney(sommeVersement);
    }
    
    public String calculMontantRetire() {
        return JsfUtil.formaterStringMoney(sommeRetrait);
    }
    
    public String calculMontantCommission() {
        return JsfUtil.formaterStringMoney(sommeCommission);
    }
    
    public String calculSolde() {  
        return JsfUtil.formaterStringMoney(0);
    }
}
