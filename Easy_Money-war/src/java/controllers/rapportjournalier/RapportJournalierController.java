package controllers.rapportjournalier;

import entities.Client;
import entities.FraisCarnet;
import entities.Privilege;
import entities.Retrait;
import entities.Versement;
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
public class RapportJournalierController extends AbstractRapportJournalierController {

    @PostConstruct
    private void init() {
        /*  38 */ this.soldes.clear();
    }

    public void updateDate() {
        try {
            /*  43 */ if (this.anneeMois.getIdAnneeMois() != null) {
                /*  44 */ this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());
            }
            /*  46 */        } catch (Exception e) {
            /*  47 */ e.printStackTrace();
        }
    }

    public void filterMois() {
        try {
            /*  53 */ if (this.annee.getIdannee() != null) {
                /*  54 */ this.anneeMoises = this.anneeMoisFacadeLocal.findByAnneeEtat(this.annee.getIdannee(), true);
            }
            /*  56 */        } catch (Exception e) {
            /*  57 */ e.printStackTrace();
        }
    }

    public void find() {
        /*  62 */ this.soldes.clear();
        try {
            /*  64 */ if (!this.date.equals(null)) {

                /*  66 */ this.clients = this.clientFacadeLocal.findAllRange(true);

                /*  68 */ for (Client c : this.clients) {
                    /*  69 */ Solde solde = new Solde();
                    /*  70 */ solde.setClient(c);

                    /*  72 */ List<Versement> versements = this.versementFacadeLocal.find(c, this.date);
                    /*  73 */ if (versements.isEmpty()) {
                        /*  74 */ solde.setMontantVerse(Integer.valueOf(0));
                    } else {
                        /*  76 */ int montantverse = 0;
                        /*  77 */ for (Versement v : versements) {
                            /*  78 */ montantverse += v.getMontant().intValue();
                        }
                        /*  80 */ solde.setMontantVerse(Integer.valueOf(montantverse));
                    }

                    /*  83 */ List<Retrait> retraits = this.retraitFacadeLocal.find(c, this.date);
                    /*  84 */ if (retraits.isEmpty()) {
                        /*  85 */ solde.setMontantRetire(Integer.valueOf(0));
                        /*  86 */ solde.setCommission(Integer.valueOf(0));
                    } else {
                        /*  88 */ int montantRetire = 0;
                        /*  89 */ int montantCommission = 0;
                        /*  90 */ for (Retrait r : retraits) {
                            /*  91 */ montantRetire += r.getMontant().intValue();
                            /*  92 */ montantCommission += r.getCommission().intValue();
                        }
                        /*  94 */ solde.setMontantRetire(Integer.valueOf(montantRetire));
                        /*  95 */ solde.setCommission(Integer.valueOf(montantCommission));
                    }

                    /*  98 */ List<FraisCarnet> fraisCarnets = this.fraisCarnetFacadeLocal.find(c, this.date);
                    /*  99 */ if (fraisCarnets.isEmpty()) {
                        /* 100 */ solde.setCarnet(Integer.valueOf(0));
                    } else {
                        /* 102 */ solde.setCarnet(Integer.valueOf(500));
                    }
                    /* 104 */ this.soldes.add(solde);
                }

                /* 107 */ if (this.soldes.isEmpty()) {
                    /* 108 */ this.showPrintButton = true;
                } else {
                    /* 110 */ this.showPrintButton = false;
                }

            }
            /* 114 */        } catch (Exception e) {
            /* 115 */ e.printStackTrace();
        }
    }

    public void printReport() {
        try {
            /* 121 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            /* 122 */ if (p != null) {
                /* 123 */ this.showReportPrintDialog = true;
            } else {
                /* 125 */ p = new Privilege();
                /* 126 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 16);
                /* 127 */ if (p != null) {
                    /* 128 */ this.showReportPrintDialog = true;
                } else {
                    /* 130 */ this.showReportPrintDialog = false;
                    /* 131 */ JsfUtil.addErrorMessage("Vous n 'avez pas le privilege d'éditer le rapport journalier d'activités");

                    return;
                }
            }
            /* 136 */ this.fileName = PrintUtils.printDailylyReport(this.date, this.soldes);
            /* 137 */        } catch (Exception e) {
            /* 138 */ e.printStackTrace();
        }
    }

    public String calculMontantVerse() {
        /* 143 */ if (this.soldes.isEmpty()) {
            /* 144 */ return "";
        }
        /* 146 */ int resultat = 0;
        /* 147 */ for (Solde s : this.soldes) {
            /* 148 */ resultat += s.getMontantVerse().intValue();
        }
        /* 150 */ return JsfUtil.formaterStringMoney(Integer.valueOf(resultat));
    }

    public String calculMontantRetire() {
        /* 154 */ if (this.soldes.isEmpty()) {
            /* 155 */ return "";
        }
        /* 157 */ int resultat = 0;
        /* 158 */ for (Solde s : this.soldes) {
            /* 159 */ resultat += s.getMontantRetire().intValue();
        }
        /* 161 */ return JsfUtil.formaterStringMoney(Integer.valueOf(resultat));
    }

    public String calculSolde() {
        /* 165 */ if (this.soldes.isEmpty()) {
            /* 166 */ return "";
        }
        /* 168 */ int resultat = 0;
        /* 169 */ for (Solde s : this.soldes) {
            /* 170 */ resultat += s.getClient().getSolde().intValue();
        }
        /* 172 */ return JsfUtil.formaterStringMoney(Integer.valueOf(resultat));
    }
}
