package controllers.rapporthebdomadaire;

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
public class RapportHebdomadaireController extends AbstractRapportHebdomaireController {

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
            /*  55 */ this.clients = this.clientFacadeLocal.findAllRange(true);

            /*  57 */ this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());

            /*  59 */ for (Client c : this.clients) {
                /*  60 */ Solde solde = new Solde();
                /*  61 */ solde.setClient(c);

                /*  63 */ List<Versement> versements = this.versementFacadeLocal.find(c, this.anneeMois);
                /*  64 */ if (versements.isEmpty()) {

                    /*  66 */ solde.setMontantVerse(Integer.valueOf(0));
                } else {
                    /*  68 */ int montantverse = 0;
                    /*  69 */ for (Versement v : versements) {
                        /*  70 */ montantverse += v.getMontant().intValue();
                    }
                    /*  72 */ solde.setMontantVerse(Integer.valueOf(montantverse));
                }

                /*  75 */ List<Retrait> retraits = this.retraitFacadeLocal.find(c, this.anneeMois);
                /*  76 */ if (retraits.isEmpty()) {
                    /*  77 */ solde.setMontantRetire(Integer.valueOf(0));
                    /*  78 */ solde.setCommission(Integer.valueOf(0));
                } else {
                    /*  80 */ int montantRetire = 0;
                    /*  81 */ int commission = 0;
                    /*  82 */ for (Retrait r : retraits) {
                        /*  83 */ montantRetire += r.getMontant().intValue();
                        /*  84 */ commission += r.getCommission().intValue();
                    }
                    /*  86 */ solde.setMontantRetire(Integer.valueOf(montantRetire));
                    /*  87 */ solde.setCommission(Integer.valueOf(commission));
                }

                /*  90 */ List<FraisCarnet> fraisCarnets = this.fraisCarnetFacadeLocal.find(c, this.anneeMois);
                /*  91 */ if (fraisCarnets.isEmpty()) {
                    /*  92 */ solde.setCarnet(Integer.valueOf(0));
                } else {
                    /*  94 */ int montantF = 0;
                    /*  95 */ for (FraisCarnet f : fraisCarnets) {
                        /*  96 */ montantF = (int) (montantF + f.getMontant().doubleValue());
                    }

                    /*  99 */ solde.setCarnet(Integer.valueOf(montantF));
                }

                /* 102 */ this.soldes.add(solde);
            }

            /* 105 */ if (this.soldes.isEmpty()) {
                /* 106 */ this.showPrintButton = true;
            } else {
                /* 108 */ this.showPrintButton = false;
            }

            /* 111 */        } catch (Exception e) {
            /* 112 */ e.printStackTrace();
        }
    }

    public void printReport() {
        try {
            /* 118 */ Privilege p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 1);
            /* 119 */ if (p != null) {
                /* 120 */ this.showReportPrintDialog = true;
            } else {
                /* 122 */ p = new Privilege();
                /* 123 */ p = this.privilegeFacadeLocal.findByUser(SessionMBean.getUserAccount().getIdutilisateur().intValue(), 17);
                /* 124 */ if (p != null) {
                    /* 125 */ this.showReportPrintDialog = true;
                } else {
                    /* 127 */ this.showReportPrintDialog = false;
                    /* 128 */ JsfUtil.addErrorMessage("Vous n'avez pas le privilège d'éditer le rapport périodique d'activité");

                    return;
                }
            }
            /* 133 */ this.fileName = PrintUtils.printWeeklyReport(this.soldes, this.anneeMois);
            /* 134 */        } catch (Exception e) {
            /* 135 */ e.printStackTrace();
        }
    }

    public String calculMontantVerse() {
        /* 140 */ if (this.soldes.isEmpty()) {
            /* 141 */ return "";
        }
        /* 143 */ int resultat = 0;
        /* 144 */ for (Solde s : this.soldes) {
            /* 145 */ resultat += s.getMontantVerse().intValue();
        }
        /* 147 */ return JsfUtil.formaterStringMoney(Integer.valueOf(resultat));
    }

    public String calculMontantRetire() {
        /* 151 */ if (this.soldes.isEmpty()) {
            /* 152 */ return "";
        }
        /* 154 */ int resultat = 0;
        /* 155 */ for (Solde s : this.soldes) {
            /* 156 */ resultat += s.getMontantRetire().intValue();
        }
        /* 158 */ return JsfUtil.formaterStringMoney(Integer.valueOf(resultat));
    }

    public String calculSolde() {
        /* 162 */ if (this.soldes.isEmpty()) {
            /* 163 */ return "";
        }
        /* 165 */ int resultat = 0;
        /* 166 */ for (Solde s : this.soldes) {
            /* 167 */ resultat += s.getClient().getSolde().intValue();
        }
        /* 169 */ return JsfUtil.formaterStringMoney(Integer.valueOf(resultat));
    }
}
