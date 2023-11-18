package controllers.rapportperiodique;

import entities.Client;
import entities.FraisCarnet;
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
public class RapportPeriodiqueController extends AbstractRapportPeriodiqueController implements Serializable {

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
            if (this.startDate != null) {
                this.clients = this.clientFacadeLocal.findAllRange(true);
                if (this.endDate != null) {
                    if (this.endDate.after(this.startDate)) {
                        for (Client c : this.clients) {
                            Solde solde = new Solde(c);

                            List<Versement> versements = this.versementFacadeLocal.find(c, this.startDate, this.endDate);
                            if (!versements.isEmpty()) {
                                int totalVersement = 0;
                                for (Versement v : versements) {
                                    totalVersement += v.getMontant();
                                }
                                solde.setMontantVerse(totalVersement);
                            }

                            List<Retrait> retraits = this.retraitFacadeLocal.find(c, this.startDate, this.endDate);
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

                            List<FraisCarnet> fraisCarnets = this.fraisCarnetFacadeLocal.find(c, this.startDate, this.endDate);
                            if (!fraisCarnets.isEmpty()) {
                                int totalCarnet = 0;
                                for (FraisCarnet f : fraisCarnets) {
                                    totalCarnet = (int) (totalCarnet + f.getMontant());
                                }
                                solde.setFraisCarnet(totalCarnet);
                            }

                            if (solde.getMontantVerse() > 0 || solde.getMontantRetire() > 0 || solde.getFraisCarnet() > 0) {
                                this.soldes.add(solde);
                            }
                        }

                        this.showPrintButton = this.soldes.isEmpty();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printReport() {
        try {
            if (this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 1) != null) {
                this.showReportPrintDialog = true;
            } else {
                if (this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 16) != null) {
                    this.showReportPrintDialog = true;
                } else {
                    this.showReportPrintDialog = false;
                    JsfUtil.addErrorMessage("Vous n'avez pas le privilège d'éditer le rapport journalier d'activités");
                    return;
                }
            }
            this.fileName = PrintUtils.printDailylyReport(this.startDate, this.endDate, this.soldes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String calculMontantVerse() {
        if (this.soldes.isEmpty()) {
            return "";
        }
        int resultat = this.soldes.stream()
                .mapToInt(Solde::getMontantVerse)
                .sum();
        return JsfUtil.formaterStringMoney(resultat);
    }

    public String calculMontantRetire() {
        if (this.soldes.isEmpty()) {
            return "";
        }
        int resultat = this.soldes.stream()
                .mapToInt(Solde::getMontantRetire)
                .sum();
        return JsfUtil.formaterStringMoney(resultat);
    }

    public String calculCommission() {
        if (this.soldes.isEmpty()) {
            return "";
        }
        int resultat = this.soldes.stream()
                .mapToInt(line -> line.getCommission())
                .sum();
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
