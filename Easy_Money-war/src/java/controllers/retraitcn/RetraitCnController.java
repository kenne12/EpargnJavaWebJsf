package controllers.retraitcn;

import controllers.service.OperationService;
import controllers.service.UtilService;
import entities.Client;
import entities.Operation;
import entities.OperationType;
import entities.Retrait;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
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
public class RetraitCnController extends AbstractRetraitCnController implements Serializable {

    private final Routine routine = new Routine();

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private OperationFacadeLocal operationFacadeLocal;

    @EJB
    private OperationService operationService;

    @EJB
    private UtilService utilService;

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

        if (!Utilitaires.isAccess(13l)) {
            JsfUtil.addErrorMessage("Vous n'avez pas le privilège d'enregistrer un rétrait");
            return;
        }
        this.mode = "Create";
        this.commission = 0;
        this.retrait1 = 0;
        this.client = new Client();
        this.retrait = new Retrait();
        this.retrait.setDateOperation(SessionMBean.getDate());
        this.showClient = false;
        this.anneeMois = SessionMBean.getMois();

        RequestContext.getCurrentInstance().execute("PF('RetraitCreerDialog').show()");
    }

    public void prepareEdit() {

        if (!Utilitaires.isAccess(14l)) {
            JsfUtil.addErrorMessage("Vous n'avez pas le privilège de modifier un rétrait");
            return;
        }

        this.mode = "Edit";
        this.showClient = true;

        if (Objects.nonNull(retrait)) {
            retrait1 = this.retrait.getMontant();
            commission = this.retrait.getCommission();
            anneeMois = this.retrait.getIdmois();
        }

        RequestContext.getCurrentInstance().execute("PF('RetraitCreerDialog').show()");
    }

    public void updateSolde1() {
        if (this.mode.equals("Create")) {
            if (this.retrait.getIdclient() != null) {
                if (this.retrait1 != null) {
                    Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                    if (this.commission != null) {
                        c.setSolde((c.getSolde() - this.retrait1 - this.commission));
                        this.retrait.getIdclient().setSolde(c.getSolde());
                    }
                } else {
                    Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                    this.retrait.getIdclient().setSolde(c.getSolde());
                }

            }
        } else if (this.retrait.getIdclient() != null) {
            if (this.retrait1 != null) {
                if (this.commission != null) {
                    Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                    int solde = c.getSolde() + this.retrait.getMontant() + this.retrait.getCommission() - this.retrait1 - this.commission;
                    this.retrait.getIdclient().setSolde((solde));
                }
            } else {
                Client c = this.clientFacadeLocal.find(this.retrait.getIdclient().getIdclient());
                this.retrait.getIdclient().setSolde(c.getSolde());
            }
        }
    }

    public void updateSolde() {
        this.retrait1 = (0);
    }

    public void updateMode() {

    }

    private void notifyEmpty(List list) {
        if (list.isEmpty()) {
            JsfUtil.addWarningMessage("Aucune donnée retrouvée correspondant aux critères de recherches");
        }
    }

    public void searchData() {
        try {
            retraits.clear();
            switch (searchMode) {
                case "date": {
                    if (Objects.nonNull(searchDate)) {
                        retraits = retraitFacadeLocal.findByDate(searchDate);
                        notifyEmpty(retraits);
                        return;
                    }
                    JsfUtil.addErrorMessage("Veuillez sélectionner une date");
                }

                case "mois": {
                    if (searchMois.getIdAnneeMois() != 0) {
                        searchMois = anneeMoisFacadeLocal.find(searchMois.getIdAnneeMois());
                        retraits = retraitFacadeLocal.findByToDates(searchMois.getDateDebut(), searchMois.getDateFin());
                        notifyEmpty(retraits);
                        return;
                    }
                    JsfUtil.addErrorMessage("Veuillez sélectionner un mois");
                }

                case "annee": {
                    if (anneeSearch.getIdannee() != 0) {
                        anneeSearch = anneeFacadeLocal.find(anneeSearch.getIdannee());
                        retraits = retraitFacadeLocal.findByToDates(anneeSearch.getDateDebut(), anneeSearch.getDateFin());
                        notifyEmpty(retraits);
                        return;
                    }
                    JsfUtil.addErrorMessage("Veuillez sélectionner un mois");
                }

                default:
                    JsfUtil.addErrorMessage("Veuillez sélectionner un critère de recherche");
            }

            if (searchMode.equals("mois")) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            if (mode.equals("Create")) {

                if (commission == null) {
                    commission = 0;
                }

                Operation operation = new Operation();
                operation.setClient(retrait.getIdclient());
                operation.setCommission(commission.doubleValue());
                operation.setMontant(retrait1.doubleValue());
                operation.setOperationType(OperationType.DEBIT);
                operation.setDateOperation(retrait.getDateOperation());
                operation.setHeure(Date.from(Instant.now()));
                operation.setLibelle("Rétrait espèce : " + retrait.getIdclient().getPrenom());

                Operation newOperation = operationService.saveOperation(operation);

                retrait.setIdretrait(retraitFacadeLocal.nextLongVal());
                retrait.setIdOperation(newOperation.getIdOperation());
                retrait.setMontant(retrait1);
                retrait.setCommission(commission);
                retrait.setSolde(newOperation.getSolde());
                retrait.setHeure(operation.getHeure());
                retrait.setIdmois(anneeMois);
                retrait.setCommissionAuto(false);
                retraitFacadeLocal.create(retrait);

                utilService.updateCaisse((newOperation.getCommission() + newOperation.getMontant()), OperationType.DEBIT);

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du retrait -> client : " + this.retrait.getIdclient().getPrenom() + " " + this.retrait.getIdclient().getNom() + " ; Montant : " + this.retrait1, SessionMBean.getUserAccount());

                this.retrait = new Retrait();
                JsfUtil.addSuccessMessage("Transaction réussie");

                RequestContext.getCurrentInstance().execute("PF('RetraitCreerDialog').hide()");
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            } else if (this.retrait != null && this.retrait1 != 0) {

                if (this.commission == null) {
                    this.commission = 0;
                }

                userTransaction.begin();
                Retrait retraitOld = retraitFacadeLocal.find(retrait.getIdretrait());

                Double totalOld = retraitOld.getMontant().doubleValue() + retraitOld.getCommission();
                Double totalNew = retrait1.doubleValue() + commission;
                if (totalNew > totalOld) {
                    utilService.updateCaisse((totalNew - totalOld), OperationType.DEBIT);
                    utilService.updateClient(retrait.getIdclient().getIdclient(), (totalNew - totalOld), OperationType.DEBIT);
                    retrait.setSolde(retraitOld.getSolde() - (totalNew - totalOld));
                } else if (totalNew < totalOld) {
                    utilService.updateCaisse((totalOld - totalNew), OperationType.CREDIT);
                    utilService.updateClient(retrait.getIdclient().getIdclient(), (totalOld - totalNew), OperationType.CREDIT);
                    retrait.setSolde(retraitOld.getSolde() + (totalOld - totalNew));
                }

                anneeMois = anneeMoisFacadeLocal.find(anneeMois.getIdAnneeMois());

                retrait.setMontant(retrait1);
                retrait.setCommission(commission);
                retrait.setIdmois(anneeMois);
                retraitFacadeLocal.edit(retrait);

                final Operation operation = operationFacadeLocal.find(retrait.getIdOperation());
                operation.setMontant(retrait1.doubleValue());
                operation.setCommission((double) commission);
                operation.setSolde(retrait.getSolde());
                operation.setDateOperation(retrait.getDateOperation());
                operationFacadeLocal.edit(operation);

                userTransaction.commit();

                Utilitaires.saveOperation(mouchardFacadeLocal, "Modification du retrait -> client : " + retrait.getIdclient().getPrenom() + " " + retrait.getIdclient().getNom() + " Ancien montant : " + totalOld + " ; Nouveau Montant : " + retrait1, SessionMBean.getUserAccount());

                JsfUtil.addSuccessMessage("Opération réussie");
                RequestContext.getCurrentInstance().execute("PF('RetraitCreerDialog').hide()");
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            } else {
                JsfUtil.addErrorMessage("Aucun retrait selectionné");
                RequestContext.getCurrentInstance().execute("PF('RetraitCreerDialog').hide()");
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Erreur survenue en cours d'éxécution");
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        }
    }

    public void delete() {
        try {
            if (retrait != null) {

                if (!Utilitaires.isAccess(14l)) {
                    JsfUtil.addErrorMessage("Vous n'avez pas le privilège de modifier un rétrait");
                    return;
                }

                userTransaction.begin();
                retraitFacadeLocal.remove(retrait);
                operationService.deleteOperationById(retrait.getIdOperation());
                utilService.updateCaisse((retrait.getCommission() + retrait.getMontant().doubleValue()), OperationType.CREDIT);
                userTransaction.commit();
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression du retrait -> client : " + this.retrait.getIdclient().getPrenom() + " " + this.retrait.getIdclient().getNom() + " ; Montant : " + this.retrait.getMontant(), SessionMBean.getUserAccount());
                JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                JsfUtil.addErrorMessage("Aucun retrait selectionné");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print() {
        if (!Utilitaires.isAccess(14l)) {
            JsfUtil.addErrorMessage("Vous n'avez pas le privilège de modifier un rétrait");
            return;
        }
    }

    public void synchData() {
        try {

            List<Retrait> listRetraits = retraitFacadeLocal.findAll();
            if (!listRetraits.isEmpty()) {

                userTransaction.begin();

                listRetraits.stream().filter((r) -> (r.getIdOperation() == null)).map((r) -> {
                    Operation op = new Operation();
                    op.setIdOperation(operationFacadeLocal.nextVal());
                    op.setClient(r.getIdclient());
                    op.setMontant(r.getMontant().doubleValue());
                    op.setSolde(r.getSolde());
                    op.setCommission(r.getCommission().doubleValue());
                    op.setLibelle("Retrait espèce : " + r.getIdclient().getNom());
                    op.setDateOperation(r.getDateOperation());
                    op.setHeure(r.getHeure());
                    op.setOperationType(OperationType.DEBIT);
                    operationFacadeLocal.create(op);
                    r.setIdOperation(op.getIdOperation());
                    return r;
                }).forEach((r) -> {
                    retraitFacadeLocal.edit(r);
                });

                userTransaction.commit();
            }
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        } catch (Exception e) {
            e.printStackTrace();
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
