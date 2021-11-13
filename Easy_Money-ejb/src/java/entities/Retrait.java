/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Retrait.findAll", query = "SELECT r FROM Retrait r"),
    @NamedQuery(name = "Retrait.findByIdretrait", query = "SELECT r FROM Retrait r WHERE r.idretrait = :idretrait"),
    @NamedQuery(name = "Retrait.findByMontant", query = "SELECT r FROM Retrait r WHERE r.montant = :montant"),
    @NamedQuery(name = "Retrait.findByDate", query = "SELECT r FROM Retrait r WHERE r.dateOperation = :date"),
    @NamedQuery(name = "Retrait.findByHeure", query = "SELECT r FROM Retrait r WHERE r.heure = :heure"),
    @NamedQuery(name = "Retrait.findByCommission", query = "SELECT r FROM Retrait r WHERE r.commission = :commission"),
    @NamedQuery(name = "Retrait.findByCommissionAuto", query = "SELECT r FROM Retrait r WHERE r.commissionAuto = :commissionAuto"),
    @NamedQuery(name = "Retrait.findBySolde", query = "SELECT r FROM Retrait r WHERE r.solde = :solde")})
public class Retrait implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idretrait;
    private Integer montant;
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date dateOperation;
    @Temporal(TemporalType.TIME)
    private Date heure;
    private Integer commission;
    @Column(name = "commission_auto")
    private Boolean commissionAuto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double solde;
    @JoinColumn(name = "idmois", referencedColumnName = "id_annee_mois")
    @ManyToOne(fetch = FetchType.LAZY)
    private AnneeMois idmois;
    @JoinColumn(name = "idclient", referencedColumnName = "idclient")
    @ManyToOne(fetch = FetchType.LAZY)
    private Client idclient;

    @Column(name = "idoperation")
    private Long idOperation;

    public Retrait() {
    }

    public Retrait(Long idretrait) {
        this.idretrait = idretrait;
    }

    public Long getIdretrait() {
        return idretrait;
    }

    public void setIdretrait(Long idretrait) {
        this.idretrait = idretrait;
    }

    public Integer getMontant() {
        return montant;
    }

    public void setMontant(Integer montant) {
        this.montant = montant;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public Long getIdOperation() {
        return idOperation;
    }

    public void setIdOperation(Long idOperation) {
        this.idOperation = idOperation;
    }

    public Date getHeure() {
        return heure;
    }

    public void setHeure(Date heure) {
        this.heure = heure;
    }

    public Integer getCommission() {
        return commission;
    }

    public void setCommission(Integer commission) {
        this.commission = commission;
    }

    public Boolean getCommissionAuto() {
        return commissionAuto;
    }

    public void setCommissionAuto(Boolean commissionAuto) {
        this.commissionAuto = commissionAuto;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public AnneeMois getIdmois() {
        return idmois;
    }

    public void setIdmois(AnneeMois idmois) {
        this.idmois = idmois;
    }

    public Client getIdclient() {
        return idclient;
    }

    public void setIdclient(Client idclient) {
        this.idclient = idclient;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idretrait != null ? idretrait.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Retrait)) {
            return false;
        }
        Retrait other = (Retrait) object;
        return !((this.idretrait == null && other.idretrait != null) || (this.idretrait != null && !this.idretrait.equals(other.idretrait)));
    }

    @Override
    public String toString() {
        return "entities.Retrait[ idretrait=" + idretrait + " ]";
    }

}
