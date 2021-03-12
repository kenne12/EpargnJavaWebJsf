package controllers.rapportperiodique;

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
            if (!this.date.equals(null)) {
                this.clients = this.clientFacadeLocal.findAllRange(true);
                if (!this.date1.equals(null)) {
                    if (this.date1.after(this.date)) {
                        for (Client c : this.clients) {
                            Solde solde = new Solde();
                            solde.setClient(c);

                            List<Versement> versements = this.versementFacadeLocal.find(c, this.date, this.date1);
                            if (versements.isEmpty()) {
                                solde.setMontantVerse(0);
                            } else {
                                int montantverse = 0;
                                for (Versement v : versements) {
                                    montantverse += v.getMontant();
                                }
                                solde.setMontantVerse(montantverse);
                            }

                            List<Retrait> retraits = this.retraitFacadeLocal.find(c, this.date, this.date1);
                            if (retraits.isEmpty()) {
                                solde.setMontantRetire(0);
                                solde.setCommission(0);
                            } else {
                                int montantRetire = 0;
                                int montantCommission = 0;
                                for (Retrait r : retraits) {
                                    montantRetire += r.getMontant();
                                    montantCommission += r.getCommission();
                                }
                                solde.setMontantRetire((montantRetire));
                                solde.setCommission((montantCommission));
                            }

                            List<FraisCarnet> fraisCarnets = this.fraisCarnetFacadeLocal.find(c, this.date, this.date1);
                            if (fraisCarnets.isEmpty()) {
                                solde.setCarnet((0));
                            } else {
                                int montantF = 0;
                                for (FraisCarnet f : fraisCarnets) {
                                    montantF = (int) (montantF + f.getMontant().doubleValue());
                                }

                                solde.setCarnet((montantF));
                            }

                            this.soldes.add(solde);
                        }

                        if (this.soldes.isEmpty()) {
                            this.showPrintButton = true;
                        } else {
                            this.showPrintButton = false;
                        }
                    }
                }
            }
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
                    JsfUtil.addErrorMessage("Vous n'avez pas le privilège d'éditer le rapport journalier d'activités");
                    return;
                }
            }
            this.fileName = PrintUtils.printDailylyReport(this.date, this.date1, this.soldes);
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
        return JsfUtil.formaterStringMoney((resultat));
    }

    public String calculSolde() {
        if (this.soldes.isEmpty()) {
            return "";
        }
        int resultat = 0;
        for (Solde s : this.soldes) {
            resultat += s.getClient().getSolde();
        }
        return JsfUtil.formaterStringMoney((resultat));
    }
}
