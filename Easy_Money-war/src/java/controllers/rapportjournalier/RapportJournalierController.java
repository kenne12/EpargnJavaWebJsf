package controllers.rapportjournalier;

import entities.Client;
import entities.Privilege;
import entities.Retrait;
import entities.Versement;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utils.JsfUtil;
import utils.PrintUtils;
import utils.SessionMBean;
import utils.Solde;

@ManagedBean
@ViewScoped
public class RapportJournalierController extends AbstractRapportJournalierController implements Serializable {

    @PostConstruct
    private void init() {
        this.soldes.clear();
    }

    public void updateDate() {
        try {
            if (this.anneeMois.getIdAnneeMois() != null) {
                this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filterMois() {
        try {
            if (this.annee.getIdannee() != null) {
                this.anneeMoises = this.anneeMoisFacadeLocal.findByAnneeEtat(this.annee.getIdannee(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void find() {
        this.soldes.clear();
        try {
            if (this.date != null) {

                this.clients = this.clientFacadeLocal.findAllRange(true);

                for (Client c : this.clients) {
                    Solde solde = new Solde();
                    solde.setClient(c);

                    List<Versement> versements = this.versementFacadeLocal.find(c, this.date);
                    if (!versements.isEmpty()) {
                        int totalVersement = 0;
                        for (Versement v : versements) {
                            totalVersement += v.getMontant();
                        }
                        solde.setMontantVerse(totalVersement);
                    }

                    List<Retrait> retraits = this.retraitFacadeLocal.find(c, this.date);
                    if (!retraits.isEmpty()) {
                        int totalRetrait = 0;
                        int totalCommission = 0;
                        for (Retrait r : retraits) {
                            totalRetrait += r.getMontant();
                            totalCommission += r.getCommission();
                        }
                        solde.setMontantRetire(totalRetrait);
                        solde.setCommission(totalCommission);
                    }

                    solde.setFraisCarnet(0);

                    if (solde.getMontantVerse() > 0 || solde.getMontantRetire() > 0 || solde.getFraisCarnet() > 0) {
                        this.soldes.add(solde);
                    }
                }

                this.showPrintButton = this.soldes.isEmpty();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printReport() {
        try {
            Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 1);
            if (p != null) {
                this.showReportPrintDialog = true;
            } else {
                p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 16);
                if (p != null) {
                    this.showReportPrintDialog = true;
                } else {
                    this.showReportPrintDialog = false;
                    JsfUtil.addErrorMessage("Vous n 'avez pas le privilege d'éditer le rapport journalier d'activités");
                    return;
                }
            }
            this.fileName = PrintUtils.printDailylyReport(this.date, this.soldes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String calculMontantVerse() {
        if (this.soldes.isEmpty()) {
            return "";
        }
        int resultat = 0;
        for (Solde s : this.soldes) {
            resultat += s.getMontantVerse();
        }
        return JsfUtil.formaterStringMoney(resultat);
    }

    public String calculMontantRetire() {
        if (this.soldes.isEmpty()) {
            return "";
        }
        int resultat = 0;
        for (Solde s : this.soldes) {
            resultat += s.getMontantRetire();
        }
        return JsfUtil.formaterStringMoney(resultat);
    }

    
    public String calculCommission() {
        if (this.soldes.isEmpty()) {
            return "";
        }
        int resultat = this.soldes.stream().mapToInt(line -> line.getCommission()).sum();
        return JsfUtil.formaterStringMoney(resultat);
    }
    
    
    public String calculFraisCarnet() {
        if (this.soldes.isEmpty()) {
            return "";
        }
        int resultat = this.soldes.stream().mapToInt(line -> line.getFraisCarnet()).sum();
        return JsfUtil.formaterStringMoney(resultat);
    }
}
