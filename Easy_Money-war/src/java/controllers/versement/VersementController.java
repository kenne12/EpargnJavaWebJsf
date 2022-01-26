package controllers.versement;

import controllers.service.OperationService;
import controllers.service.UtilService;
import entities.Client;
import entities.Operation;
import entities.OperationType;
import entities.Versement;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.transaction.UserTransaction;
import org.primefaces.context.RequestContext;
import sessions.OperationFacadeLocal;
import utils.JsfUtil;
import utils.Routine;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class VersementController extends AbstractVersementController implements Serializable {

    private final Routine routine = new Routine();

    @EJB
    private OperationService operationService;

    @EJB
    private OperationFacadeLocal operationFacadeLocal;

    @EJB
    private UtilService utilService;

    @Resource
    private UserTransaction userTransaction;

    @PostConstruct
    private void init() {
        this.searchMode = "date";
        this.searchDate = SessionMBean.getDate();
    }

    public void updateMois() {
        try {
            this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareCreate() {
        if (!Utilitaires.isAccess(10l)) {
            JsfUtil.addErrorMessage("Vous n'avez pas le privilège d'enregistrer un versement");
            return;
        }
        mode = "Create";
        client = new Client();
        versement = new Versement();
        versement.setDateOperation(SessionMBean.getDate());
        versement.setSolde(0d);
        showClient = false;
        anneeMois = SessionMBean.getMois();
        versement1 = 0;
        RequestContext.getCurrentInstance().execute("PF('VersementCreerDialog').show()");
    }

    public void prepareEdit() {

        if (!Utilitaires.isAccess(11L)) {
            JsfUtil.addErrorMessage("Vous n'avez pas le privilège de modifier un versement");
            return;
        }

        this.mode = "Edit";
        this.showClient = true;

        if (this.versement != null) {
            this.versement1 = this.versement.getMontant();
            this.anneeMois = this.versement.getIdmois();
        }

        RequestContext.getCurrentInstance().execute("PF('VersementCreerDialog').show()");
    }

    public void updateSolde1() {
        if (mode.equals("Create")) {
            if (versement.getIdclient() != null) {
                if (versement1 != null) {
                    Client c = clientFacadeLocal.find(versement.getIdclient().getIdclient());
                    int solde = c.getSolde() + versement1;
                    versement.getIdclient().setSolde(solde);
                } else {
                    Client c = clientFacadeLocal.find(versement.getIdclient().getIdclient());
                    versement.getIdclient().setSolde(c.getSolde());
                }
            }
        } else if (versement.getIdclient() != null) {
            if (versement1 != null) {
                Client c = clientFacadeLocal.find(versement.getIdclient().getIdclient());
                int solde = c.getSolde() - versement1;
                versement.getIdclient().setSolde((solde));
            } else {
                Client c = clientFacadeLocal.find(versement.getIdclient().getIdclient());
                this.versement.getIdclient().setSolde(c.getSolde());
            }
        }
    }

    public void updateSolde() {
        this.versement1 = 0;
    }

    public void updateMode() {

    }

    private void notifyEmpty(List<Versement> versements) {
        if (versements.isEmpty()) {
            JsfUtil.addWarningMessage("Aucune donnée retrouvée correspondant aux critères de recherches");
        }
    }

    public void searchData() {
        try {
            versements.clear();
            switch (searchMode) {
                case "date": {
                    if (!Objects.equals(searchDate, null)) {
                        versements.addAll(versementFacadeLocal.findByDate(searchDate));
                        notifyEmpty(versements);
                        return;
                    }
                    JsfUtil.addErrorMessage("Veuillez sélectionner la date");
                }

                case "mois": {
                    if (searchMois.getIdAnneeMois() != 0) {
                        searchMois = anneeMoisFacadeLocal.find(searchMois.getIdAnneeMois());
                        versements = versementFacadeLocal.findByTwoDates(searchMois.getDateDebut(), searchMois.getDateFin());
                        notifyEmpty(versements);
                        return;
                    }
                    JsfUtil.addErrorMessage("Veuillez sélectionner le mois");
                }

                case "annee": {
                    if (!Objects.equals(anneeSearch.getIdannee(), 0)) {
                        anneeSearch = anneeFacadeLocal.find(anneeSearch.getIdannee());
                        versements = versementFacadeLocal.findByTwoDates(anneeSearch.getDateDebut(), anneeSearch.getDateFin());
                        notifyEmpty(versements);
                        return;
                    }
                    JsfUtil.addWarningMessage("Veuillez sélectionner une année");
                }
                default:
                    JsfUtil.addWarningMessage("Veuillez selectionner un critère de recherche");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Erreur survenue en cours d'exécution");
        }
    }

    public void create() {
        try {
            if (mode.equals("Create")) {

                userTransaction.begin();

                Operation operation = new Operation();
                operation.setClient(versement.getIdclient());
                operation.setCommission(null);
                operation.setOperationType(OperationType.CREDIT);
                operation.setLibelle("Versement espèce : " + versement.getIdclient().getPrenom());
                operation.setMontant(versement1.doubleValue());
                operation.setDateOperation(versement.getDateOperation());
                Operation newOperation = operationService.saveOperation(operation);

                versement.setIdversement(versementFacadeLocal.nextVal());
                versement.setSolde(newOperation.getSolde());
                versement.setHeure(newOperation.getHeure());
                versement.setIdmois(anneeMois);
                versement.setIdOperation(newOperation.getIdOperation());
                versement.setMontant(versement1);
                versementFacadeLocal.create(versement);

                utilService.updateCaisse(versement.getMontant().doubleValue(), OperationType.CREDIT);
                userTransaction.commit();

                JsfUtil.addSuccessMessage("Transaction réussie");
                Utilitaires.saveOperation(mouchardFacadeLocal, "Enregistrement du versement -> client : " + versement.getIdclient().getPrenom() + " " + this.versement.getIdclient().getNom() + " ; Montant : " + this.versement1, SessionMBean.getUserAccount());

                versement = new Versement();

                RequestContext.getCurrentInstance().execute("PF('VersementCreerDialog').hide()");
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            } else if (versement != null && versement.getIdversement() != null) {

                Versement versementOld = versementFacadeLocal.find(versement.getIdversement());
                Integer diff = versement1 - versementOld.getMontant();

                versement.setIdmois(anneeMois);
                versement.setMontant(versement1);
                userTransaction.begin();
                if (diff == 0) {
                    versement.setMontant(versement1);
                    versement.setSolde(versement.getSolde());
                    versementFacadeLocal.edit(versement);
                } else {
                    if (diff > 1) {
                        utilService.updateCaisse(diff.doubleValue(), OperationType.CREDIT);

                        versement.setSolde(versementOld.getSolde() + diff);
                        versementFacadeLocal.edit(versement);

                        operationService.editOperation(versement.getIdOperation(), diff.doubleValue(), OperationType.CREDIT);
                        utilService.updateClient(versement.getIdclient().getIdclient(), diff.doubleValue(), OperationType.CREDIT);
                    } else {
                        utilService.updateCaisse(Math.abs(diff.doubleValue()), OperationType.DEBIT);
                        versement.setSolde(versementOld.getSolde() - Math.abs(diff.doubleValue()));
                        versementFacadeLocal.edit(versement);

                        operationService.editOperation(versement.getIdOperation(), (double) Math.abs(diff), OperationType.DEBIT);
                        utilService.updateClient(versement.getIdclient().getIdclient(), Math.abs(diff.doubleValue()), OperationType.DEBIT);
                    }
                }
                userTransaction.commit();

                RequestContext.getCurrentInstance().execute("PF('VersementCreerDialog').hide()");
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                JsfUtil.addSuccessMessage("Opération réussie");
                Utilitaires.saveOperation(mouchardFacadeLocal, "Modification du versement -> client : " + versement.getIdclient().getPrenom() + " " + versement.getIdclient().getNom() + " Ancien montant : " + versementOld.getMontant() + " ; Nouveau Montant : " + this.versement1, SessionMBean.getUserAccount());
            } else {
                JsfUtil.addErrorMessage("Aucun versement selectionné");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit() {
    }

    public void delete() {
        try {
            if (versement != null) {

                if (!Utilitaires.isAccess(12L)) {
                    JsfUtil.addErrorMessage("Vous n 'avez pas le privilège de supprimer un versement");
                    return;
                }

                userTransaction.begin();
                operationService.deleteOperationById(versement.getIdOperation());
                versementFacadeLocal.remove(versement);
                utilService.updateCaisse(versement.getMontant().doubleValue(), OperationType.DEBIT);
                userTransaction.commit();

                Utilitaires.saveOperation(mouchardFacadeLocal, "Suppression du versement -> client : " + versement.getIdclient().getPrenom() + " " + this.versement.getIdclient().getNom() + " ; Montant : " + this.versement.getMontant(), SessionMBean.getUserAccount());

                JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                JsfUtil.addErrorMessage("Aucun versement selectionnée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void synchData() {
        try {

            List<Versement> listVersements = versementFacadeLocal.findAll();
            if (!listVersements.isEmpty()) {

                userTransaction.begin();

                listVersements.stream().filter((v) -> (v.getIdOperation() == null)).map((v) -> {
                    Operation op = new Operation();
                    op.setIdOperation(operationFacadeLocal.nextVal());
                    op.setClient(v.getIdclient());
                    op.setMontant(v.getMontant().doubleValue());
                    op.setSolde(v.getSolde());
                    op.setCommission(null);
                    op.setLibelle("Versement espèce : " + v.getIdclient().getNom());
                    op.setDateOperation(v.getDateOperation());
                    op.setHeure(v.getHeure());
                    op.setOperationType(OperationType.CREDIT);
                    operationFacadeLocal.create(op);
                    v.setIdOperation(op.getIdOperation());
                    return v;
                }).forEach((v) -> {
                    versementFacadeLocal.edit(v);
                });

                userTransaction.commit();
            }
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print() {

        if (!Utilitaires.isAccess(12L)) {
            JsfUtil.addErrorMessage("Vous n 'avez pas le privilège de supprimer un versement");
            return;
        }
    }

    public void signalError(String chaine) {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        this.routine.feedBack("information", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(chaine));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void signalSuccess() {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void signalException(Exception e) {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        this.routine.catchException(e, this.routine.localizeMessage("erreur_execution"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public Routine getRoutine() {
        return routine;
    }

}
