package controllers.rapporthebdomadaire;

import entities.Client;
import entities.FraisCarnet;
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
public class RapportHebdomadaireController extends AbstractRapportHebdomaireController implements Serializable {

    @PostConstruct
    private void init() {
        this.soldes.clear();
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
            this.clients = this.clientFacadeLocal.findAllRange(true);

            this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());

            for (Client c : this.clients) {
                Solde solde = new Solde(c);

                List<Versement> versements = this.versementFacadeLocal.find(c, this.anneeMois);
                if (!versements.isEmpty()) {
                    int montantverse = 0;
                    for (Versement v : versements) {
                        montantverse += v.getMontant();
                    }
                    solde.setMontantVerse(montantverse);
                }

                List<Retrait> retraits = this.retraitFacadeLocal.find(c, this.anneeMois);
                if (!retraits.isEmpty()) {
                    int montantRetire = 0;
                    int commission = 0;
                    for (Retrait r : retraits) {
                        montantRetire += r.getMontant();
                        commission += r.getCommission();
                    }
                    solde.setMontantRetire(montantRetire);
                    solde.setCommission(commission);
                }

                List<FraisCarnet> fraisCarnets = this.fraisCarnetFacadeLocal.find(c, this.anneeMois);
                if (!fraisCarnets.isEmpty()) {
                    int montantF = 0;
                    for (FraisCarnet f : fraisCarnets) {
                        montantF = (int) (montantF + f.getMontant());
                    }
                    solde.setFraisCarnet(montantF);
                }
                if (solde.getMontantVerse() > 0 || solde.getMontantRetire() > 0 || solde.getFraisCarnet() > 0) {
                    this.soldes.add(solde);
                }

            }

            this.showPrintButton = soldes.isEmpty();

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
                p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur(), 17);
                if (p != null) {
                    this.showReportPrintDialog = true;
                } else {
                    this.showReportPrintDialog = false;
                    JsfUtil.addErrorMessage("Vous n'avez pas le privilège d'éditer le rapport périodique d'activité");
                    return;
                }
            }
            this.fileName = PrintUtils.printWeeklyReport(this.soldes, this.anneeMois);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String calculMontantVerse() {
        if (this.soldes.isEmpty()) {
            return "";
        }
        int totalVersement = 0;
        for (Solde s : this.soldes) {
            totalVersement += s.getMontantVerse();
        }
        return JsfUtil.formaterStringMoney(totalVersement);
    }

    public String calculMontantRetire() {
        if (this.soldes.isEmpty()) {
            return "";
        }
        int totalRetrait = 0;
        for (Solde s : this.soldes) {
            totalRetrait += s.getMontantRetire();
        }
        return JsfUtil.formaterStringMoney(totalRetrait);
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
