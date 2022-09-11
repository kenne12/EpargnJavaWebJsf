/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.depense.rapport_periodique;

import entities.AnneeMois;
import entities.Recette;
import enumeration.OperationModeType;
import java.io.Serializable;
import java.util.Optional;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.PrintUtils;

/**
 *
 * @author kenne
 */
@Named(value = "rapportDepensePeriodeController")
@SessionScoped
public class RapportDepensePeriodeController extends AbstractRapportDepenseController implements Serializable {

    /**
     * Creates a new instance of RapportDepensePeriodeController
     */
    public RapportDepensePeriodeController() {
    }

    public void updateDate() {
        startDate = null;
        endDate = null;
        if (this.anneeMois.getIdAnneeMois() != null) {

            Optional<AnneeMois> value = this.anneeMoises.stream()
                    .filter(item -> item.getIdAnneeMois().equals(anneeMois.getIdAnneeMois())).findAny();

            if (value.isPresent()) {
                anneeMois = value.get();
                startDate = anneeMois.getDateDebut();
                endDate = anneeMois.getDateFin();
            }
        }
    }

    private boolean checkPrint() {
        return this.recettes.isEmpty();
    }

    public void onSearchRecettes() {
        this.recettes.clear();
        if (startDate != null && endDate != null) {
            this.setRecettes(depenseService.findOperationBetweenTwoDates(startDate, endDate));
        }
        this.showPrintButton = this.checkPrint();
    }

    public void onPrintReport() {
        this.fileName = PrintUtils.printPeriodicRecetteReport(startDate, endDate, this.getRecettes() , OperationModeType.DEPENSE);
        RequestContext.getCurrentInstance().execute("PF('RapportDepenseImprimerDialog').show()");
    }

    private Integer sumTotalDepense() {
        if (this.getRecettes() == null || this.getRecettes().isEmpty()) {
            return 0;
        }
        return this.getRecettes().stream().mapToInt(Recette::getMontant).sum();
    }

    public String getformatTotalRecette() {
        return JsfUtil.formaterStringMoney(this.sumTotalDepense());
    }

}
