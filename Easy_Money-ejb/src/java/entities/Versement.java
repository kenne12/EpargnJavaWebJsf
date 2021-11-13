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
    @NamedQuery(name = "Versement.findAll", query = "SELECT v FROM Versement v"),
    @NamedQuery(name = "Versement.findByIdversement", query = "SELECT v FROM Versement v WHERE v.idversement = :idversement"),
    @NamedQuery(name = "Versement.findByMontant", query = "SELECT v FROM Versement v WHERE v.montant = :montant"),
    @NamedQuery(name = "Versement.findByDate", query = "SELECT v FROM Versement v WHERE v.dateOperation = :date"),
    @NamedQuery(name = "Versement.findByHeure", query = "SELECT v FROM Versement v WHERE v.heure = :heure"),
    @NamedQuery(name = "Versement.findBySolde", query = "SELECT v FROM Versement v WHERE v.solde = :solde")})
public class Versement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idversement;
    private Integer montant;
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date dateOperation;
    @Temporal(TemporalType.TIME)
    private Date heure;
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

    public Versement() {
    }

    public Versement(Long idversement) {
        this.idversement = idversement;
    }

    public Long getIdversement() {
        return idversement;
    }

    public void setIdversement(Long idversement) {
        this.idversement = idversement;
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

    public Date getHeure() {
        return heure;
    }

    public void setHeure(Date heure) {
        this.heure = heure;
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

    public Long getIdOperation() {
        return idOperation;
    }

    public void setIdOperation(Long idOperation) {
        this.idOperation = idOperation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idversement != null ? idversement.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Versement)) {
            return false;
        }
        Versement other = (Versement) object;
        if ((this.idversement == null && other.idversement != null) || (this.idversement != null && !this.idversement.equals(other.idversement))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Versement[ idversement=" + idversement + " ]";
    }

}
